package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class IOUtils {

    private static final String FILE_PREFIX = "parser_legal_Instrument_";

    public static void saveDataToFile(String fileName, Object rawData) {
        String data = JSONObject.toJSONString(rawData);
        BufferedWriter writer = null;
        File file = new File("WPWPOI/results/"+ fileName + ".json");
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

    public static String getFilePrefix() {
        return FILE_PREFIX;
    }
}
