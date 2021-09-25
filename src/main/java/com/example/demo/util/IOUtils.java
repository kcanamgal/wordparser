package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.VO.*;

import java.io.*;
import java.util.List;

public class IOUtils {

    private static final String FILE_PREFIX = "parser_legal_Instrument_";
    private static final String RES_PREFIX = "WPWPOI/results/";

    public static void saveDataToFile(String fileName, Object rawData) {
        String data = JSONObject.toJSONString(rawData);
        BufferedWriter writer = null;
        File file = new File(RES_PREFIX + fileName + ".json");
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
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
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
        String paragraph_filename = FILE_PREFIX + token + "/paragraphs/paragraph/";
        String font_sytpe_filename = FILE_PREFIX + token + "/paragraphs/font_stype/";
        String paragraph_stype_filename = FILE_PREFIX + token + "/paragraphs/paragraph_stype/";
        String pics_filename = FILE_PREFIX + token + "/pics/";
        String tables_filename = FILE_PREFIX + token + "/tables/";
        String titles_filename = FILE_PREFIX + token + "/titles/";

        for(int i=0;i<paragraphs.size();i++){
            String tmpName = paragraph_filename + i;
            saveDataToFile(tmpName,paragraphs.get(i));
        }
        for(int i=0;i<fonts.size();i++){
            String tmpName = font_sytpe_filename + i;
            saveDataToFile(tmpName,fonts.get(i));
        }
        for(int i=0;i<paragraph_stypes.size();i++){
            String tmpName = paragraph_stype_filename + i;
            saveDataToFile(tmpName,paragraph_stypes.get(i));
        }
        for(int i=0;i<pictures.size();i++){
            String tmpName = pics_filename + pictures.get(i).getParagraphId();
            saveDataToFile(tmpName,pictures.get(i));
        }
        for(int i=0;i<tables.size();i++){
            String tmpName = tables_filename + tables.get(i).getParagraphBefore();
            saveDataToFile(tmpName,tables.get(i));
        }
        for(int i=0;i<titles.size();i++){
            String tmpName = titles_filename + titles.get(i).getParagraphId();
            saveDataToFile(tmpName,titles.get(i));
        }
    }

}
