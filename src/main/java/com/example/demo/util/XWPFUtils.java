package com.example.demo.util;

import com.example.demo.VO.Paragraph;
import com.example.demo.VO.Table;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XWPFUtils {

    private XWPFUtils() { }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void parse(InputStream is) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(is);
        List<IBodyElement> elements = xwpfDocument.getBodyElements();
        List result = new ArrayList();
        int i = 0, n = elements.size(); boolean isTable = false;

        while (i < n) {
            IBodyElement bodyElement = elements.get((int) i);
            switch (bodyElement.getElementType()) {
                case PARAGRAPH: {
                    XWPFParagraph xwpfParagraph = (XWPFParagraph) bodyElement;
                    String text = xwpfParagraph.getParagraphText();
                    Paragraph paragraph = new Paragraph();
                    fill(xwpfParagraph, paragraph);
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
                    String text = xwpfTable.getText();
                    if (isTable) {

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
//
//    public static List<Paragraph> allParagraphs(XWPFDocument document) {
//        List<XWPFParagraph> paragraphs = document.getParagraphs();
//        List<Paragraph> list = new ArrayList<>(paragraphs.size());
//        for (XWPFParagraph paragraph: paragraphs) {
//            Paragraph p = new Paragraph();
//            fill(paragraph, p);
//            list.add(p);
//        }
//        return list;
//    }
//
//    public static List<Table> allTables(XWPFDocument document) {
//        List<XWPFTable> tables = document.getTables();
//        List<Table> list = new ArrayList<>(tables.size());
//        for (XWPFTable tb: tables) {
//            Table table = new Table();
//            fill();
//        }
//        return null;
//    }
//
    private static void fill(final XWPFParagraph xwpfParagraph , final Paragraph paragraph) {
        paragraph.setParagraphId(xwpfParagraph.getNumID());
        paragraph.setIndentFromRight(xwpfParagraph.getIndentationRight());
        paragraph.setIndentFromLeft(xwpfParagraph.getIndentationLeft());
        paragraph.setFirstLineIndent(xwpfParagraph.getIndentationFirstLine());
//        paragraph.setBold();
    }
//
//    private static void fill(final XWPFTable xwpfTable, final Table table) {
//        table.setParagraphAfter(xwpfTable);
//    }
}
