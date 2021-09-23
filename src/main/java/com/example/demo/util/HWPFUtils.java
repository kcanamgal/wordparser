package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public final class HWPFUtils {

    private HWPFUtils() { }

    public static void parse(InputStream is) throws IOException {
        HWPFDocument hwpfDocument = new HWPFDocument(is);
        Range r = hwpfDocument.getRange();
        for (int i = 0; i < r.numParagraphs(); i++) {
            Paragraph p = r.getParagraph(i);
            if (p.text().indexOf("序号") != -1) {

            }
            else {
                com.example.demo.VO.Paragraph paragraph = new com.example.demo.VO.Paragraph();
                paragraph.setParagraphId(new BigInteger(String.valueOf(i)));
                fill(p, paragraph);
            }

        }
    }

    private static void fill(Paragraph paragraphModel, com.example.demo.VO.Paragraph paragraph) {
        //paragraph.setFontName(paragraphModel.getCharacterRun(0).getFontName());
        //paragraph.setFontSize(paragraphModel.getCharacterRun(0).getFontSize());
        paragraph.setFontAlignment(paragraphModel.getFontAlignment());
        //paragraph.setBold(paragraphModel.getCharacterRun(0).isBold());
        //paragraph.setItalic(paragraphModel.getCharacterRun(0).isItalic());
        paragraph.setLvl(paragraphModel.getLvl());
        paragraph.setLineSpacing(paragraphModel.getLineSpacing().toInt());
        paragraph.setFirstLineIndent(paragraphModel.getFirstLineIndent());
        paragraph.setIndentFromLeft(paragraphModel.getIndentFromLeft());
        paragraph.setIndentFromRight(paragraphModel.getIndentFromRight());
        paragraph.setParagraphText(paragraphModel.text());
        paragraph.setInTable(paragraphModel.isInTable());
    }


}
