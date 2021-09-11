package com.example.demo.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XWPFUtils {

    private XWPFUtils() { }

    public static void parse(InputStream is) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument(is);

    }
}
