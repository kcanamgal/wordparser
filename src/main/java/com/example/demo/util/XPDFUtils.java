package com.example.demo.util;

import java.io.*;

import com.example.demo.exceptions.RequestParamException;
import com.spire.doc.Document;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.widget.PdfPageCollection;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class XPDFUtils {
    private static final String splitPath = "./split/";

    private static final String docPath = "./doc/";

    public static void parse(MultipartFile file,String token) throws IOException {
        InputStream is = null;
        try{
            XPDFUtils.pdftoword("WPWPOI/files/" + "parser_legal_Instrument_" + token +"/"+ file.getOriginalFilename());
        }catch (Exception e){
            throw new RequestParamException("Cannot parse this pdf.");
        }
        File file1 = new File("WPWPOI/files/" + "parser_legal_Instrument_" + token +"/"+ file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-4) + ".docx");
        is = new FileInputStream(file1);
        XWPFUtils.parse(is,token);
        deleteFile(file1);
    }

    public static void pdftoword(String  srcPath) {
        String desPath = srcPath.substring(0, srcPath.length()-4)+".docx";
        boolean result = false;
        try {
            boolean flag = isPDFFile(srcPath);
            boolean flag1 = create();

            if (flag && flag1) {
                PdfDocument pdf = new PdfDocument();
                pdf.loadFromFile(srcPath);
                PdfPageCollection num = pdf.getPages();

                if (num.getCount() <= 10) {
                    pdf.saveToFile(desPath, FileFormat.DOCX);
                }
                else {
                    pdf.split(splitPath+"test{0}.pdf",0);

                    File[] fs = getSplitFiles(splitPath);
                    for(int i=0;i<fs.length;i++) {
                        PdfDocument sonpdf = new PdfDocument();
                        sonpdf.loadFromFile(fs[i].getAbsolutePath());
                        sonpdf.saveToFile(docPath+fs[i].getName().substring(0, fs[i].getName().length()-4)+".docx",FileFormat.DOCX);
                    }
                    try {
                        result = merge(docPath, desPath);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(result==true) {
                clearFiles(splitPath);
                clearFiles(docPath);
            }
        }
    }


    private static boolean create() {
        File f = new File(splitPath);
        File f1 = new File(docPath);
        if(!f.exists() )  f.mkdirs();
        if(!f.exists() )  f1.mkdirs();
        return true;
    }

    private static boolean isPDFFile(String srcPath2) {
        File file = new File(srcPath2);
        String filename = file.getName();
        if (filename.endsWith(".pdf")) {
            return true;
        }
        return false;
    }

    private static File[] getSplitFiles(String path) {
        File f = new File(path);
        File[] fs = f.listFiles();
        if (fs == null) {
            return null;
        }
        return fs;
    }

    public static boolean merge(String docPath,String desPath){

        File[] fs = getSplitFiles(docPath);
        System.out.println(docPath);
        Document document = new Document(docPath+"test0.docx");

        for(int i=1;i<fs.length;i++) {
            document.insertTextFromFile(docPath+"test"+i+".docx", com.spire.doc.FileFormat.Docx_2013);
        }
        document.saveToFile(desPath);
        return true;
    }

    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        if(file.exists()){
            deleteFile(file);
        }
    }
    public static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                deleteFile(files[i]);
            }
        }
        file.delete();
    }
}
