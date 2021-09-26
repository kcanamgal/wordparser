package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.VO.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IOUtils {

    private static final String FILE_PREFIX = "parser_legal_Instrument_";
    private static final String RES_PREFIX = "WPWPOI" + File.separator + "results";

    public static void saveDataToFile(String fileName, Object rawData) {
        String data = JSONObject.toJSONString(rawData);
        BufferedWriter writer = null;
        File file = new File(RES_PREFIX + File.separator +fileName + ".json");
        //如果文件不存在，则新建一个
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), StandardCharsets.UTF_8));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("文件写入成功！");
    }

    public static void saveParserResult(List<Paragraph> paragraphs,List<Table> tables,List<Title> titles,List<List<Font_stype>> fonts,List<Picture> pictures,List<Paragraph_stype> paragraph_stypes,String token){
        String paragraph_filename = FILE_PREFIX + token + File.separator + "paragraphs" + File.separator + "paragraph";
        String font_sytpe_filename = FILE_PREFIX + token + File.separator + "paragraphs" + File.separator + "font_stype";
        String paragraph_stype_filename = FILE_PREFIX + token + File.separator + "paragraphs" + File.separator + "paragraph_stype";
        String pics_filename = FILE_PREFIX + token + File.separator + "pics";
        String tables_filename = FILE_PREFIX + token + File.separator + "tables";
        String titles_filename = FILE_PREFIX + token + File.separator + "titles";

        for(int i=0;i<paragraphs.size();i++){
            String tmpName = paragraph_filename + File.separator + paragraphs.get(i).getParagraphId();
            saveDataToFile(tmpName,paragraphs.get(i));
        }
        for(int i=0;i<fonts.size();i++){
            String tmpName = font_sytpe_filename + File.separator + fonts.get(i).get(0).getParagraphId();
            saveDataToFile(tmpName,fonts.get(i));
        }
        for(int i=0;i<paragraph_stypes.size();i++){
            String tmpName = paragraph_stype_filename + File.separator + paragraph_stypes.get(i).getParagraphId();
            saveDataToFile(tmpName,paragraph_stypes.get(i));
        }
        for(int i=0;i<pictures.size();i++){
            String tmpName = pics_filename + File.separator + pictures.get(i).getParagraphId();
            saveDataToFile(tmpName,pictures.get(i));
        }
        for(int i=0;i<tables.size();i++){
            String tmpName = tables_filename + File.separator + tables.get(i).getParagraphBefore();
            saveDataToFile(tmpName,tables.get(i));
        }
        for(int i=0;i<titles.size();i++){
            String tmpName = titles_filename + File.separator + titles.get(i).getParagraphId();
            saveDataToFile(tmpName,titles.get(i));
        }
    }

}
