package com.example.demo.util;

import com.example.demo.VO.Font_stype;
import com.example.demo.VO.Table;
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

public final class HWPFUtils {

    private HWPFUtils() { }

    public static void parse(InputStream is) throws IOException {
        HWPFDocument hwpfDocument = new HWPFDocument(is);
        Range r = hwpfDocument.getRange();
        List<com.example.demo.VO.Paragraph> paragraphs = new ArrayList<>();
        com.example.demo.VO.Paragraph paragraph, previousParagraph = null; Paragraph p;
        PicturesTable picturesTable = hwpfDocument.getPicturesTable();
        Map<Integer, Picture> pictures = getPicOffsets(picturesTable);

        for (int i = 0, n = r.numParagraphs(); i < n; i++) {
            p = r.getParagraph(i);
            if (p == null) {
                // do nothing
            }
            else {
                /*
                 *
                 */
                int numRuns = p.numCharacterRuns();
                List<Font_stype> fonts = new ArrayList<>(numRuns);
                CharacterRun run, previousRun = null;
                for (int j = 0; j < numRuns; j++) {
                    run = p.getCharacterRun(j);
                    if (picturesTable.hasPicture(run)) {
                        Picture pictureModel = pictures.get(run.getPicOffset());
                        com.example.demo.VO.Picture picture = new com.example.demo.VO.Picture();
                        picture.setBase64Content(new BASE64Encoder().encode(pictureModel.getContent()));
                        picture.setSuggestFileExtension(pictureModel.suggestFileExtension());
                        picture.setWidth((double) pictureModel.getWidth());
                        picture.setHeight((double) pictureModel.getHeight());
                        picture.setFilename(pictureModel.suggestFullFileName());
                        picture.setTextBefore(previousRun.text());
                    }
                    else {
                        Font_stype font = extractFont(run);
                        font.setFontAlignment(p.getFontAlignment());
                        fonts.add(font);
                    }
                    previousRun = run;
                }
//                paragraph.setBold(fonts.stream().map(Font_stype::isBold).collect(Collectors.toList()));
//                paragraph.setItalic(fonts.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
//                paragraph.setFontSize(fonts.stream().map(font_stype -> (double) font_stype.getFontSize()).collect(Collectors.toList()));
//                paragraph.setFontName(fonts.stream().map(Font_stype::getFontName).collect(Collectors.toList()));

                paragraph = new com.example.demo.VO.Paragraph();
                paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
                fill(p, paragraph);

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
                    table.setTextBefore(previousParagraph.getParagraphText());
                    table.setParagraphBefore(previousParagraph.getParagraphId());
                    paragraphs.add(paragraph);
                    do {
                        p = r.getParagraph(++i);
                        paragraph = new com.example.demo.VO.Paragraph();
                        paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
                        fill(p, paragraph);
                    } while (p.isInTable());
                    table.setTableContent(r.getTable(firstInTable).text());
                    table.setParagraphAfter(paragraph.getParagraphId());
                    table.setTextAfter(paragraph.getParagraphText());
                }
            }
        }
    }

    private static Map<Integer, Picture> getPicOffsets(PicturesTable picturesTable) {
        Map<Integer, Picture> pictures =  new HashMap<>();
        List<Picture> allPics = picturesTable.getAllPictures();
        for (Picture pic: allPics) {
            pictures.put(pic.getStartOffset(), pic);
        }
        return pictures;
    }

    private static void fill(org.apache.poi.hwpf.usermodel.Paragraph paragraphModel, com.example.demo.VO.Paragraph paragraph) {
        paragraph.setLvl(paragraphModel.getLvl());
        paragraph.setLineSpacing(paragraphModel.getLineSpacing().toInt());
        paragraph.setFirstLineIndent(paragraphModel.getFirstLineIndent());
        paragraph.setIndentFromLeft(paragraphModel.getIndentFromLeft());
        paragraph.setIndentFromRight(paragraphModel.getIndentFromRight());
        paragraph.setParagraphText(paragraphModel.text());
        paragraph.setInTable(paragraphModel.isInTable());
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
}
