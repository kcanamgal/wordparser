package com.example.demo.util;

import com.example.demo.VO.Font_stype;
import com.example.demo.VO.Table;
import com.example.demo.VO.Title;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class HWPFUtils {

    private HWPFUtils() { }

    public static void parse(InputStream is) throws IOException {
        new HWPFHelper(is).parse();

//        HWPFDocument hwpfDocument = new HWPFDocument(is);
//        Range r = hwpfDocument.getRange();
//        List<com.example.demo.VO.Paragraph> paragraphs = new ArrayList<>();
//        com.example.demo.VO.Paragraph paragraph, previousParagraph = null; Paragraph p;
//        PicturesTable picturesTable = hwpfDocument.getPicturesTable();
//        Map<Integer, Picture> pictures = getPicOffsets(picturesTable);
//
//        for (int i = 0, n = r.numParagraphs(); i < n; i++) {
//            p = r.getParagraph(i);
//            if (p == null) {
//                // do nothing
//            }
//            else {
//                /*
//                 *
//                 */
//                int numRuns = p.numCharacterRuns();
//                List<Font_stype> fonts = new ArrayList<>(numRuns);
//                CharacterRun run, previousRun = null;
//                for (int j = 0; j < numRuns; j++) {
//                    run = p.getCharacterRun(j);
//                    if (picturesTable.hasPicture(run)) {
//                        Picture pictureModel = pictures.get(run.getPicOffset());
//                        com.example.demo.VO.Picture picture = new com.example.demo.VO.Picture();
//                        picture.setBase64Content(new BASE64Encoder().encode(pictureModel.getContent()));
//                        picture.setSuggestFileExtension(pictureModel.suggestFileExtension());
//                        picture.setWidth((double) pictureModel.getWidth());
//                        picture.setHeight((double) pictureModel.getHeight());
//                        picture.setFilename(pictureModel.suggestFullFileName());
//                        picture.setTextBefore(previousRun.text());
//                    }
//                    else {
//                        Font_stype font = extractFont(run);
//                        font.setFontAlignment(p.getFontAlignment());
//                        fonts.add(font);
//                    }
//                    previousRun = run;
//                }
////                paragraph.setBold(fonts.stream().map(Font_stype::isBold).collect(Collectors.toList()));
////                paragraph.setItalic(fonts.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
////                paragraph.setFontSize(fonts.stream().map(font_stype -> (double) font_stype.getFontSize()).collect(Collectors.toList()));
////                paragraph.setFontName(fonts.stream().map(Font_stype::getFontName).collect(Collectors.toList()));
//
//                paragraph = new com.example.demo.VO.Paragraph();
//                paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
//                fill(p, paragraph);
//
//                if (!paragraph.isInTable()) {
//                    paragraphs.add(paragraph);
//                }
//                else {
//                    /*
//                     * 建表格
//                     * 获取Document中的表格需要知道表格的起始段落，因此通过
//                     * 每次都判断段落是否在表格中来找到表格中第一个段落。
//                     * 然后进入循环，直到处理完第一个不在表格中的段落为止
//                     * 此时的p跟paragraph都对应到table的paragraphAfter
//                     */
//                    Table table = new Table();
//                    Paragraph firstInTable = p;
//                    table.setTextBefore(previousParagraph.getParagraphText());
//                    table.setParagraphBefore(previousParagraph.getParagraphId());
//                    paragraphs.add(paragraph);
//                    do {
//                        p = r.getParagraph(++i);
//                        paragraph = new com.example.demo.VO.Paragraph();
//                        paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
//                        fill(p, paragraph);
//                        paragraphs.add(paragraph);
//                    } while (p.isInTable());
//                    table.setTableContent(r.getTable(firstInTable).text());
//                    table.setParagraphAfter(paragraph.getParagraphId());
//                    table.setTextAfter(paragraph.getParagraphText());
//                }
//            }
//        }
    }

    private static class HWPFHelper {
        HWPFDocument hwpfDocument;
        PicturesTable picturesTable;
        List<com.example.demo.VO.Paragraph> paragraphs = new ArrayList<>();
        Map<Integer, Picture> pictures = new HashMap<>();
        List<List<Font_stype>> fonts = new ArrayList<>();
        List<com.example.demo.VO.Picture> picModels = new ArrayList<>();
        List<Title> titles = new ArrayList<>();

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
            return false;
        }

        static Title extractAsTitle(Paragraph paragraph, int defaultId) {
            Title title = new Title();
            title.setFirstLineIndent(paragraph.getFirstLineIndent());
            title.setIndentFromLeft(paragraph.getIndentFromLeft());
            title.setIndentFromRight(paragraph.getIndentFromRight());
            title.setLineSpacing(paragraph.getLineSpacing().toInt());
            title.setLvl(paragraph.getLvl());
            title.setParagraphText(paragraph.text());
            title.setParagraphId(new BigInteger(String.valueOf(defaultId)));
            return title;
        }

        void parse() {
            Range r = hwpfDocument.getRange();
            com.example.demo.VO.Paragraph paragraph; Paragraph p, previousParagraph = null;

            for (int i = 0, n = r.numParagraphs(); i < n; i++) {
                p = r.getParagraph(i);
                /*
                 * 首先要确认这个段落是不是一个title
                 * 确认的方法（暂时是简单粗暴的）以是否为一行居中的文字作为判断标题的依据
                 */
                if (isTitle(p)) {
                    Title title = extractAsTitle(p, i);

                }
                else {
                    paragraph = new com.example.demo.VO.Paragraph();
                    paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
                    parseToModel(p, paragraph);

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
                        table.setParagraphBefore(new BigInteger(String.valueOf(i - 1)));
                        paragraphs.add(paragraph);
                        do {
                            p = r.getParagraph(++i);
                            paragraph = new com.example.demo.VO.Paragraph();
                            paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
                            parseToModel(p, paragraph);
                            paragraphs.add(paragraph);
                        } while (p.isInTable());
                        table.setTableContent(r.getTable(firstInTable).text());
                        table.setParagraphAfter(paragraph.getParagraphId());
                        table.setTextAfter(paragraph.getParagraphText());

                    }
                }

                previousParagraph = p;
            }
        }

        private static void fill(org.apache.poi.hwpf.usermodel.Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel) {
            paragraphModel.setLvl(paragraph.getLvl());
            paragraphModel.setLineSpacing(paragraph.getLineSpacing().toInt());
            paragraphModel.setFirstLineIndent(paragraph.getFirstLineIndent());
            paragraphModel.setIndentFromLeft(paragraph.getIndentFromLeft());
            paragraphModel.setIndentFromRight(paragraph.getIndentFromRight());
            paragraphModel.setParagraphText(paragraph.text());
            paragraphModel.setInTable(paragraph.isInTable());
        }

        private static Font_stype extractFont(CharacterRun run) {
            Font_stype font = new Font_stype();
            font.setBold(run.isBold());
            font.setColor(run.getColor());
            font.setItalic(run.isItalic());
            font.setFontName(run.getFontName());
            font.setFontSize(run.getFontSize());
            return font;
        }

        private void parseToModel(Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel) {
            int numRuns = paragraph.numCharacterRuns();
            CharacterRun run, previousRun = null;
            List<Font_stype> font_stypes = new ArrayList<>();
            for (int j = 0; j < numRuns; j++) {
                run = paragraph.getCharacterRun(j);
                if (picturesTable.hasPicture(run)) {
                    Picture picture = pictures.get(run.getPicOffset());
                    com.example.demo.VO.Picture pictureModel = new com.example.demo.VO.Picture();
                    pictureModel.setBase64Content(new BASE64Encoder().encode(picture.getContent()));
                    pictureModel.setSuggestFileExtension(picture.suggestFileExtension());
                    pictureModel.setWidth((double) picture.getWidth());
                    pictureModel.setHeight((double) picture.getHeight());
                    pictureModel.setFilename(picture.suggestFullFileName());
                    pictureModel.setTextBefore(previousRun.text());
                    picModels.add(pictureModel);
                }
                else {
                    Font_stype font = extractFont(run);
                    font.setFontAlignment(paragraph.getFontAlignment());
                    font_stypes.add(font);
                }
                previousRun = run;
            }
            paragraphModel.setBold(font_stypes.stream().map(Font_stype::isBold).collect(Collectors.toList()));
            paragraphModel.setItalic(font_stypes.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
            paragraphModel.setFontSize(font_stypes.stream().map(font_stype -> (double) font_stype.getFontSize()).collect(Collectors.toList()));
            paragraphModel.setFontName(font_stypes.stream().map(Font_stype::getFontName).collect(Collectors.toList()));
            fonts.add(font_stypes);
            fill(paragraph, paragraphModel);
        }
    }
}
