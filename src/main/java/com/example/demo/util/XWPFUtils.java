package com.example.demo.util;

import com.example.demo.VO.*;
import org.apache.poi.xwpf.usermodel.*;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class XWPFUtils {

    private static final String PSTYLE_TITLE = "a3";
    private static final String PSTYLE_SUBTITLE = "a6";
    private static final String PSTYLE_TITLE_1 = "1";
    private static final String PSTYLE_TITLE_2 = "2";
    private static final String PSTYLE_TITLE_3 = "3";
    private static final String PSTYLE_TITLE_4 = "4";
    private static final String PSTYLE_TITLE_5 = "5";
    private static final ParagraphAlignment POSSIBLE_TITLE = ParagraphAlignment.CENTER;

    private static final Map<String, Integer> lvlValue;

    static {
        lvlValue = new HashMap<>();
        lvlValue.put(PSTYLE_TITLE, 0);
        lvlValue.put(PSTYLE_SUBTITLE, 1);
        lvlValue.put(PSTYLE_TITLE_1, 0);
        lvlValue.put(PSTYLE_TITLE_2, 1);
        lvlValue.put(PSTYLE_TITLE_3, 2);
        lvlValue.put(PSTYLE_TITLE_4, 3);
        lvlValue.put(PSTYLE_TITLE_5, 4);
        lvlValue.put(null, 9);
    }

    private XWPFUtils() { }

    public static void parse(InputStream is,String token) throws IOException {
        new XWPFHelper(is).parseAndWrite(token);
    }

    private static class XWPFHelper {
        XWPFDocument xwpfDocument;
        List<Paragraph> paragraphs = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        List<Title> titles = new ArrayList<>();
        List<List<Font_stype>> fonts = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        List<Paragraph_stype> paragraph_stypes = new ArrayList<>();

        XWPFHelper(InputStream is) throws IOException {
            xwpfDocument = new XWPFDocument(is);
        }

        void parse() {
            List<IBodyElement> elements = xwpfDocument.getBodyElements();
            int i = 0, count = 0, n = elements.size(); boolean isTable = false;
            String textBefore = null;
            while (i < n) {
                IBodyElement bodyElement = elements.get(i);
                switch (bodyElement.getElementType()) {
                    case PARAGRAPH: {
                        XWPFParagraph xwpfParagraph = (XWPFParagraph) bodyElement;
                        String text = xwpfParagraph.getParagraphText();
//                        StringBuilder sb = new StringBuilder();
                        int lastPics = 0;
                        /*
                         * 图片处理
                         * 遍历段落中所有run，获取其中的picture
                         * 如果run中有picture就处理picture，否则就处理文字
                         */
                        for (XWPFRun run: xwpfParagraph.getRuns()) {
                            List<XWPFPicture> pictures = run.getEmbeddedPictures();
                            if (pictures.size() == 0) {
//                                sb.append(run);
                                textBefore = run.text();
                                // 回填textAfter
                                if (lastPics > 0) {
                                    for (int pos = this.pictures.size() - lastPics; lastPics > 0; lastPics--, pos++) {
                                        Picture picture = this.pictures.get(pos);
                                        picture.setTextAfter(textBefore);
                                    }
                                }
                            }
                            else {
                                for (XWPFPicture picture: pictures) {
                                    lastPics++;
                                    Picture pic = new Picture();
                                    pic.setFilename(picture.getPictureData().getFileName());
                                    pic.setHeight(picture.getDepth());
                                    pic.setWidth(picture.getWidth());
                                    pic.setSuggestFileExtension(picture.getPictureData().suggestFileExtension());
                                    pic.setBase64Content(new BASE64Encoder().encode(picture.getPictureData().getData()));
                                    pic.setTextBefore(textBefore);
                                    pic.setParagraphId(BigInteger.valueOf(count));
                                    this.pictures.add(pic);
                                }
                            }
                        }

                        if (xwpfParagraph.getAlignment() == POSSIBLE_TITLE || xwpfParagraph.getStyle()!=null) {
                            Title title = new Title();
                            title.setParagraphId(BigInteger.valueOf(count++));
                            fill(xwpfParagraph, title);
                            titles.add(title);
                        }
                        else {
                            Paragraph paragraph = new Paragraph();
                            fill(xwpfParagraph, paragraph, false);
                            paragraph.setParagraphId(BigInteger.valueOf(count++));
                            paragraphs.add(paragraph);
                        }
                        if (isTable) {
                            int k = tables.size() - 1;
                            Table table = tables.get(k);
                            table.setTextAfter(text);
                            table.setParagraphAfter(BigInteger.valueOf(count - 1));
                        }
                        // 二度回填textAfter
                        if (lastPics > 0) {
                            for (int pos = this.pictures.size() - lastPics; lastPics > 0; lastPics--, pos++) {
                                Picture picture = this.pictures.get(pos);
                                picture.setTextAfter(textBefore);
                            }
                        }
                        break;
                    }
                    case TABLE: {
                        XWPFTable xwpfTable = (XWPFTable) bodyElement;
                        Table tb = new Table();
                        tb.setTableContent(xwpfTable.getText());
                        tb.setTextBefore(textBefore);
                        tb.setParagraphBefore(BigInteger.valueOf(count - 1));
                        /*
                         * 对在表格中的段落进行提取
                         */
                        List<XWPFTableRow> tableRows = xwpfTable.getRows();
                        String finalText = null;
                        for (XWPFTableRow row: tableRows) {
                            List<XWPFTableCell> tableCells = row.getTableCells();
                            for (XWPFTableCell cell: tableCells) {
                                List<XWPFParagraph> cellParagraphs = cell.getParagraphs();
                                for (XWPFParagraph cParagraph: cellParagraphs) {
                                    Paragraph p = new Paragraph();
                                    fill(cParagraph, p, true);
                                    p.setParagraphId(BigInteger.valueOf(count++));
                                    paragraphs.add(p);
                                    finalText = p.getParagraphText();
                                }
                            }
                        }

                        tables.add(tb);
                        isTable = true;
                        textBefore = finalText;
                        break;
                    }
                    default:
                        break;
                }
                i++;
            }

        }

        void parseAndWrite(String token) {
            parse();
            write(token);
        }

        void write(String token) {
            IOUtils.saveParserResult(paragraphs,tables,titles,fonts,pictures,paragraph_stypes,token);
        }

        private void fill(XWPFParagraph titleParagraph, Title title) {
            Paragraph_stype stype = extractParagraphStype(titleParagraph);
            title.setParagraphText(titleParagraph.getParagraphText());
            title.setLvl(stype.getLvl());
            title.setLineSpacing(stype.getLineSpacing());
            title.setIndentFromLeft(stype.getIndentFromLeft());
            title.setIndentFromRight(stype.getIndentFromRight());
            title.setFirstLineIndent(stype.getFirstLineIndent());
        }

        // 提取出stype的部分并添加进列表
        private Paragraph_stype extractParagraphStype(XWPFParagraph xwpfParagraph) {
            Paragraph_stype stype = new Paragraph_stype();
            stype.setFirstLineIndent(xwpfParagraph.getIndentationFirstLine());
            stype.setIndentFromLeft(xwpfParagraph.getIndentationLeft());
            stype.setIndentFromRight(xwpfParagraph.getIndentationRight());
            stype.setLineSpacing(xwpfParagraph.getSpacingBetween());
            stype.setLvl(lvlValue.get(xwpfParagraph.getStyle()));
            this.paragraph_stypes.add(stype);
            return stype;
        }

        private void fill(final XWPFParagraph xwpfParagraph , final Paragraph paragraph, boolean inTable) {
            Paragraph_stype stype = extractParagraphStype(xwpfParagraph);
            stype.setParagraphId(paragraph.getParagraphId());
            paragraph.setIndentFromRight(stype.getIndentFromRight());
            paragraph.setIndentFromLeft(stype.getIndentFromLeft());
            paragraph.setFirstLineIndent(stype.getFirstLineIndent());
            paragraph.setLineSpacing(stype.getLineSpacing());
            paragraph.setLvl(stype.getLvl());
            paragraph.setInTable(inTable);

            // deal with fonts
            List<XWPFRun> runs = xwpfParagraph.getRuns();
            Set<Font_stype> font_stypes = new HashSet<>();
            for (XWPFRun run: runs) {
               Font_stype font = new Font_stype();
               font.setFontName(run.getFontName());
               font.setFontAlignment(xwpfParagraph.getFontAlignment());
               font.setFontSize(run.getFontSizeAsDouble());
               font.setItalic(run.isItalic());
               String color = run.getColor();
               font.setColor(color == null? 0: Integer.parseUnsignedInt(color, 16));
               font.setBold(run.isBold());
               font.setParagraphId(paragraph.getParagraphId());
               font_stypes.add(font);
            }
            this.fonts.add(new ArrayList<>(font_stypes));
            System.out.println(font_stypes.size());
            paragraph.setFontName(font_stypes.stream().map(Font_stype::getFontName).collect(Collectors.toList()));
            paragraph.setFontSize(font_stypes.stream().map(Font_stype::getFontSize).collect(Collectors.toList()));
            paragraph.setItalic(font_stypes.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
            paragraph.setBold(font_stypes.stream().map(Font_stype::isBold).collect(Collectors.toList()));
        }

    }
}
