package com.example.demo.util;

import com.example.demo.VO.Paragraph;
import com.example.demo.VO.Table;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class XWPFUtils {

    private XWPFUtils() { }

    public static void parse(InputStream is) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(is);

    }

    public static List<Paragraph> allParagraphs(XWPFDocument document) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<Paragraph> list = new ArrayList<>(paragraphs.size());
        for (XWPFParagraph paragraph: paragraphs) {
            Paragraph p = new Paragraph();
            fill(paragraph, p);
            list.add(p);
        }
        return list;
    }

    public static List<Table> allTables(XWPFDocument document) {
        List<XWPFTable> tables = document.getTables();
        return null;
    }

    private static void fill(XWPFParagraph xwpfParagraph, Paragraph paragraph) {
        paragraph.setFirstLineIndent(xwpfParagraph.getIndentationFirstLine());
        paragraph.setIndentFromLeft(xwpfParagraph.getIndentationLeft());
        paragraph.setIndentFromRight(xwpfParagraph.getIndentationRight());
        paragraph.setParagraphId(xwpfParagraph.getNumID());
        xwpfParagraph.getText();    // 有页脚文本
        xwpfParagraph.getParagraphText();   // 无页脚文本
    }
}
