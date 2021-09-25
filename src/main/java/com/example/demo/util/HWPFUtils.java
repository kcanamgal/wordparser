package com.example.demo.util;

import com.example.demo.VO.Font_stype;
import com.example.demo.VO.Paragraph_stype;
import com.example.demo.VO.Table;
import com.example.demo.VO.Title;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public final class HWPFUtils {
    private static byte JC_LEFT = 0;
    private static byte JC_CENTER = 1;
    private static byte JC_RIGHT = 2;

    private HWPFUtils() { }

    public static void parse(InputStream is,String token) throws IOException {
        new HWPFHelper(is).parseAndWrite(token);
    }

    private static class HWPFHelper {
        HWPFDocument hwpfDocument;
        PicturesTable picturesTable;
        List<com.example.demo.VO.Paragraph> paragraphs = new ArrayList<>();
        Map<Integer, Picture> pictures = new HashMap<>();
        List<List<Font_stype>> fonts = new ArrayList<>();
        List<com.example.demo.VO.Picture> picModels = new ArrayList<>();
        List<Title> titles = new ArrayList<>();
        List<Paragraph_stype> paragraph_stypes = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        HWPFHelper(InputStream is) throws IOException {
            hwpfDocument = new HWPFDocument(is);
            picturesTable = hwpfDocument.getPicturesTable();
            getPicOffsets(pictures, picturesTable);
        }

        static void getPicOffsets(Map<Integer, Picture> pictures, PicturesTable picturesTable) {
            List<Picture> allPics = picturesTable.getAllPictures();
            for (Picture pic: allPics) {
                pictures.put(pic.getStartOffset(), pic);
            }
        }

        static boolean isTitle(Paragraph paragraph) {
            return paragraph.getProps().getJc() == JC_CENTER;
        }

        static Title extractAsTitle(Paragraph paragraph, int defaultId) {
            Title title = new Title();
            title.setFirstLineIndent(paragraph.getFirstLineIndent());
            title.setIndentFromLeft(paragraph.getIndentFromLeft());
            title.setIndentFromRight(paragraph.getIndentFromRight());
            title.setLineSpacing((double) paragraph.getLineSpacing().toInt());
            title.setLvl(paragraph.getLvl());
            title.setParagraphText(paragraph.text());
            title.setParagraphId(BigInteger.valueOf(defaultId));
            return title;
        }

        void parse() {
            Range r = hwpfDocument.getRange();
            com.example.demo.VO.Paragraph paragraph; Paragraph p, previousParagraph = null;
            String textBefore = null;

            for (int i = 0, n = r.numParagraphs(); i < n; i++) {
                p = r.getParagraph(i);
                /*
                 * 首先要确认这个段落是不是一个title
                 * 确认的方法（暂时是简单粗暴的）以是否为一行居中的文字作为判断标题的依据
                 * 如果不是title，那么就是一个普通的段落或者是在表格内的段落，这时候来判断表格
                 */
                if (isTitle(p)) {
                    Title title = extractAsTitle(p, i);
                    titles.add(title);
                }
                else {
                    paragraph = new com.example.demo.VO.Paragraph();
                    paragraph.setParagraphId(BigInteger.valueOf(i));
                    textBefore = parseToModel(p, paragraph, textBefore);

                    if (!paragraph.isInTable()) {
                        paragraphs.add(paragraph);
                    }
                    else {
                        /*
                         * 建表格
                         * 获取Document中的表格需要知道表格的起始段落，因此通过
                         * 每次都判断段落是否在表格中来找到表格中第一个段落。
                         * 然后进入循环，直到处理完第一个不在表格中的段落为止
                         * 此时的p跟paragraph都对应到table的paragraphAfter
                         */
                        Table table = new Table();
                        Paragraph firstInTable = p;

                        assert previousParagraph != null;
                        table.setTextBefore(previousParagraph.text());
                        table.setParagraphBefore(BigInteger.valueOf(i - 1));
                        paragraphs.add(paragraph);
                        do {
                            p = r.getParagraph(++i);
                            paragraph = new com.example.demo.VO.Paragraph();
                            paragraph.setParagraphId(BigInteger.valueOf(i));
                            textBefore = parseToModel(p, paragraph, textBefore);
                            paragraphs.add(paragraph);
                        } while (p.isInTable());
                        table.setTableContent(r.getTable(firstInTable).text());
                        table.setParagraphAfter(paragraph.getParagraphId());
                        table.setTextAfter(paragraph.getParagraphText());
                        tables.add(table);
                    }
                }

                previousParagraph = p;
            }
        }

        void parseAndWrite(String token) {
            parse();
            write(token);
        }

        void write(String token) {
            IOUtils.saveParserResult(paragraphs,tables,titles,fonts,picModels,paragraph_stypes,token);
        }

        private void fill(org.apache.poi.hwpf.usermodel.Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel) {
            Paragraph_stype stype = new Paragraph_stype();
            stype.setFirstLineIndent(paragraph.getFirstLineIndent());
            stype.setIndentFromLeft(paragraph.getIndentFromLeft());
            stype.setIndentFromRight(paragraph.getIndentFromRight());
            stype.setLineSpacing((double) paragraph.getLineSpacing().toInt());
            stype.setLvl(paragraph.getLvl());
            stype.setParagraphId(paragraphModel.getParagraphId());
            this.paragraph_stypes.add(stype);
            paragraphModel.setLvl(stype.getLvl());
            paragraphModel.setLineSpacing(stype.getLineSpacing());
            paragraphModel.setFirstLineIndent(stype.getFirstLineIndent());
            paragraphModel.setIndentFromLeft(stype.getIndentFromLeft());
            paragraphModel.setIndentFromRight(stype.getIndentFromRight());
            paragraphModel.setParagraphText(paragraph.text());
            paragraphModel.setInTable(paragraph.isInTable());

        }

        private static Font_stype extractFont(CharacterRun run) {
            Font_stype font = new Font_stype();
            font.setBold(run.isBold());
            font.setColor(run.getColor());
            font.setItalic(run.isItalic());
            font.setFontName(run.getFontName());
            font.setFontSize((double) run.getFontSize());
            return font;
        }

        private static com.example.demo.VO.Picture extractPicture(Picture picture) {
            com.example.demo.VO.Picture pictureModel = new com.example.demo.VO.Picture();
            pictureModel.setBase64Content(new BASE64Encoder().encode(picture.getContent()));
            pictureModel.setSuggestFileExtension(picture.suggestFileExtension());
            pictureModel.setWidth((double) picture.getWidth());
            pictureModel.setHeight((double) picture.getHeight());
            pictureModel.setFilename(picture.suggestFullFileName());
            return pictureModel;
        }

        private String parseToModel(Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel, String textBefore) {
            int numRuns = paragraph.numCharacterRuns();
            CharacterRun run;
            Set<Font_stype> font_stypes = new HashSet<>();
            int traceBack = 0, n = this.picModels.size();
            while (traceBack < n && this.picModels.get(n - traceBack - 1).getTextAfter() == null) traceBack++;
            for (int j = 0; j < numRuns; j++) {
                run = paragraph.getCharacterRun(j);
                if (picturesTable.hasPicture(run)) {
                    Picture picture = pictures.get(run.getPicOffset());
                    com.example.demo.VO.Picture pictureModel = extractPicture(picture);
                    pictureModel.setTextBefore(textBefore);
                    picModels.add(pictureModel);
                    traceBack++;
                }
                else {
                    Font_stype font = extractFont(run);
                    font.setFontAlignment(paragraph.getFontAlignment());
                    font.setParagraphId(paragraphModel.getParagraphId());
                    font_stypes.add(font);
                    textBefore = run.text();
                    // 回填picture
                    while (traceBack > 0) {
                        com.example.demo.VO.Picture picture = picModels.get(picModels.size() - (traceBack--));
                        picture.setTextAfter(textBefore);
                    }
                }
            }
            paragraphModel.setBold(font_stypes.stream().map(Font_stype::isBold).collect(Collectors.toList()));
            paragraphModel.setItalic(font_stypes.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
            paragraphModel.setFontSize(font_stypes.stream().map(Font_stype::getFontSize).collect(Collectors.toList()));
            paragraphModel.setFontName(font_stypes.stream().map(Font_stype::getFontName).collect(Collectors.toList()));
            if(font_stypes.size()>0){
                fonts.add(new ArrayList<>(font_stypes));
            }
            fill(paragraph, paragraphModel);
            return textBefore;
        }
    }
}
