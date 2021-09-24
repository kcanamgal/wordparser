package com.example.demo.util;

import com.example.demo.VO.Paragraph;
import com.example.demo.VO.Picture;
import com.example.demo.VO.Table;
import com.example.demo.service.FileService;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.*;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        lvlValue.put(PSTYLE_TITLE, 1);
        lvlValue.put(PSTYLE_SUBTITLE, 2);
        lvlValue.put(PSTYLE_TITLE_1, 1);
        lvlValue.put(PSTYLE_TITLE_2, 2);
        lvlValue.put(PSTYLE_TITLE_3, 3);
        lvlValue.put(PSTYLE_TITLE_4, 4);
        lvlValue.put(PSTYLE_TITLE_5, 5);
    }

    private XWPFUtils() { }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void parse(InputStream is,String token) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(is);
        List<IBodyElement> elements = xwpfDocument.getBodyElements();
        List result = new ArrayList();
        int i = 0, n = elements.size(); boolean isTable = false; int j=0;


        while (i < n) {
            IBodyElement bodyElement = elements.get(i);
            switch (bodyElement.getElementType()) {
                case PARAGRAPH: {
                    XWPFParagraph xwpfParagraph = (XWPFParagraph) bodyElement;
                    String text = xwpfParagraph.getParagraphText();
                    StringBuilder sb = new StringBuilder();
                    for (XWPFRun run: xwpfParagraph.getRuns()) {
                        List<XWPFPicture> pictures = run.getEmbeddedPictures();
                        if (pictures.size() == 0) {
                            sb.append(run);
                        }
                        else {
                            for (XWPFPicture picture: pictures) {
                                Picture pic = new Picture();
                                pic.setFilename(picture.getPictureData().getFileName());
                                pic.setHeight(picture.getDepth());
                                pic.setWidth(picture.getWidth());
                                pic.setSuggestFileExtension(picture.getPictureData().suggestFileExtension());
                                pic.setBase64Content(new BASE64Encoder().encode(picture.getPictureData().getData()));
                            }
                        }
                    }
                    Paragraph paragraph = new Paragraph();
                    fill(xwpfParagraph, paragraph, false);
                    String fileName = IOUtils.getFilePrefix() + token + "/paragraphs/" + j;
                    IOUtils.saveDataToFile(fileName,paragraph);
                    j++;
                    if (isTable) {
                        int k = result.size() - 1;
                        Table table = (Table) result.get(k);
                        table.setTextAfter(text);
                        table.setParagraphAfter(paragraph.getParagraphId());
                    }

                    result.add(paragraph);
                    isTable = false;
                    break;
                }
                case TABLE: {
                    XWPFTable xwpfTable = (XWPFTable) bodyElement;
                    Table tb = new Table();
                    tb.setTableContent(xwpfTable.getText());
                    /*
                     * 对在表格中的段落进行提取
                     */
                    List<XWPFTableRow> tableRows = xwpfTable.getRows();
                    for (XWPFTableRow row: tableRows) {
                        List<XWPFTableCell> tableCells = row.getTableCells();
                        for (XWPFTableCell cell: tableCells) {
                            List<XWPFParagraph> cellParagraphs = cell.getParagraphs();
                            for (XWPFParagraph cParagraph: cellParagraphs) {
                                Paragraph p = new Paragraph();
                                fill(cParagraph, p, true);
                                String fileName = IOUtils.getFilePrefix() + token + "/paragraphs/" + j;
                                IOUtils.saveDataToFile(fileName,p);
                                j++;
                            }
                        }
                    }

                    result.add(tb);
                    isTable = true;
                    break;
                }
                default:
                    break;
            }
            i++;
        }
    }

    private static void fill(final XWPFParagraph xwpfParagraph , final Paragraph paragraph, boolean inTable) {
        paragraph.setParagraphId(xwpfParagraph.getNumID());
        paragraph.setIndentFromRight(xwpfParagraph.getIndentationRight());
        paragraph.setIndentFromLeft(xwpfParagraph.getIndentationLeft());
        paragraph.setFirstLineIndent(xwpfParagraph.getIndentationFirstLine());
        paragraph.setInTable(inTable);
        ;
        // deal with fonts
        List<XWPFRun> runs = xwpfParagraph.getRuns();
        List<Double> fontSizes = new ArrayList<>();
        List<String> fontNames = new ArrayList<>();
        List<Boolean> fontBolds = new ArrayList<>(), fontItalics = new ArrayList<>();
        for (XWPFRun run: runs) {
            fontSizes.add(run.getFontSizeAsDouble());
            fontNames.add(run.getFontName());
            fontBolds.add(run.isBold());
            fontBolds.add(run.isItalic());
        }
        paragraph.setFontName(fontNames);
        paragraph.setFontSize(fontSizes);
        paragraph.setItalic(fontItalics);
        paragraph.setBold(fontBolds);
//        paragraph.setInTable();
        paragraph.setLvl(lvlValue.get(xwpfParagraph.getStyle()));
    }
//
    private static void fill(final XWPFTable xwpfTable, final Table table) {

    }
}
