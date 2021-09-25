package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.VO.*;
import com.example.demo.exceptions.RequestParamException;
import com.example.demo.service.JsonParserService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

@Api("文档解析接口")
@RestController
@RequestMapping("/word_parser")
public class ParserController {
    @Autowired
    JsonParserService jsonParserService;
    List<Object> jsonContent;

    @Operation(summary = "获取文档内全部段落信息")
    @GetMapping("/{token}/all_paragraphs")
    public RestResult getAllParagraphs(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        try{
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/paragraph";
        jsonContent = new ArrayList<>();
        List<Integer> nameList = new ArrayList<>();
        File file = new File(localUrl);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            Collections.sort(nameList);
            for(int x=0;x<fs.length;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl + "/" + nameList.get(x) + ".json"));
            }
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token not found");
        }
        return RestResult.success(jsonContent);
    }

    @Operation(summary = "获取文档内全部表格信息")
    @GetMapping("/{token}/all_tables")
    public RestResult getAllTables(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/tables";
        jsonContent = new ArrayList<>();
        List<Integer> nameList = new ArrayList<>();
        File file = new File(localUrl);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        try{
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            Collections.sort(nameList);
            for(int x=0;x<fs.length;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl + "/" + nameList.get(x) + ".json"));
            }
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token not found");
        }
        return RestResult.success(jsonContent);
    }

    @Operation(summary = "获取文档内全部图片信息")
    @GetMapping("/{token}/all_pics")
    public RestResult getAllPictures(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/pics";
        jsonContent = new ArrayList<>();
        List<Integer> nameList = new ArrayList<>();
        File file = new File(localUrl);		//获取其file对象
        File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
        try{
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            Collections.sort(nameList);
            for(int x=0;x<fs.length;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl + "/" + nameList.get(x) + ".json"));
            }
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token not found");
        }
        return RestResult.success(jsonContent);
    }

    @Operation(summary = "获取文档内全部标题信息")
    @GetMapping("/{token}/all_titles")
    public RestResult getAllTitles(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/titles";
        jsonContent = new ArrayList<>();
        List<Integer> nameList = new ArrayList<>();
        File file = new File(localUrl);		//获取其file对象
        File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
        try{
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            Collections.sort(nameList);
            for(int x=0;x<fs.length;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl + "/" + nameList.get(x) + ".json"));
            }
        }catch (NullPointerException e){
            throw new RequestParamException("Token not found");
        }catch (IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
        return RestResult.success(jsonContent);
    }

    @Operation(summary = "根据token、段落id，获取段落详细信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}")
    public RestResult getParagraph(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/paragraph" + "/" + paragraph_id + ".json";
        try{
            return RestResult.success(jsonParserService.jsonToObject(localUrl));
        }catch (NullPointerException e){
            throw new RequestParamException("Token not found");
        }catch (IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token、段落id，获取段落的详细段落格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/paragraph_stype")
    public RestResult getParagraph_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/paragraph_stype" + "/" + paragraph_id + ".json";
        try{
            return RestResult.success(jsonParserService.jsonToObject(localUrl));
        }catch (NullPointerException e){
            throw new RequestParamException("Token not found");
        }catch (IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token、段落id，获取段落的详细字体格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/font_stype")
    public RestResult getParagraph_Font_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/font_stype" + "/" + paragraph_id + ".json";
        try{
            return RestResult.success(jsonParserService.jsonToObject(localUrl));
        }catch (NullPointerException e){
            throw new RequestParamException("Token not found");
        }catch (IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部段落信息")
    @GetMapping("/{token}/title/{paragraph_id}/all_paragraphs")
    public RestResult getTitleParagraphs (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        try{
            String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/titles";
            String localUrl2 = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/paragraph";
            jsonContent = new ArrayList<>();
            List<Integer> nameList = new ArrayList<>();
            List<Integer> nameList2 = new ArrayList<>();
            File file = new File(localUrl);		//获取其file对象
            File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
            File file2 = new File(localUrl2);		//获取其file对象
            File[] fs2 = file2.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            for(int x=0;x<fs2.length;x++){
                nameList2.add(jsonParserService.transFileNameToInt(fs2[x].getName()));
            }
            Collections.sort(nameList);
            Collections.sort(nameList2);
            int begin;
            try{
                begin = nameList.get(nameList.indexOf(Integer.parseInt(paragraph_id)));
            }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                throw new RequestParamException("paragraph_id not found");
            }
            int end = nameList2.get(nameList2.size()-1);
            if (nameList.indexOf(begin)!=nameList.size()-1){
                end = nameList.get(nameList.indexOf(begin)+1);
            }
            System.out.println(begin);
            System.out.println(end);
            int i=0,j=0;
            while(nameList2.get(i)<=begin){
                i++;
            }
            while(nameList2.get(j)<end){
                j++;
                if(nameList2.get(j)==end){
                    j++;
                    break;
                }
            }
            for(int x=i;x<j;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl2 + "/" + nameList2.get(x) + ".json"));
            }
            return RestResult.success(jsonContent);
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部图片信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}/all_pics")
    public RestResult getTitlePictures (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        try{
            String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/titles";
            String localUrl2 = "WPWPOI/results/parser_legal_Instrument_" + token + "/pics";
            jsonContent = new ArrayList<>();
            List<Integer> nameList = new ArrayList<>();
            List<Integer> nameList2 = new ArrayList<>();
            File file = new File(localUrl);		//获取其file对象
            File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
            File file2 = new File(localUrl2);		//获取其file对象
            File[] fs2 = file2.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            for(int x=0;x<fs2.length;x++){
                nameList2.add(jsonParserService.transFileNameToInt(fs2[x].getName()));
            }
            Collections.sort(nameList);
            Collections.sort(nameList2);
            int begin;
            try{
                begin = nameList.get(nameList.indexOf(Integer.parseInt(paragraph_id)));
            }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                throw new RequestParamException("paragraph_id not found");
            }
            int end = nameList2.get(nameList2.size()-1);
            if (nameList.indexOf(begin)!=nameList.size()-1){
                end = nameList.get(nameList.indexOf(begin)+1);
            }
            System.out.println(begin);
            System.out.println(end);
            int i=0,j=0;
            while(nameList2.get(i)<=begin){
                i++;
            }
            while(nameList2.get(j)<end){
                j++;
                if(nameList2.get(j)==end){
                    j++;
                    break;
                }
            }
            for(int x=i;x<j;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl2 + "/" + nameList2.get(x) + ".json"));
            }
            return RestResult.success(jsonContent);
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部表格信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}/all_tables")
    public RestResult getTitleTables (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        try{
            String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/titles";
            String localUrl2 = "WPWPOI/results/parser_legal_Instrument_" + token + "/tables";
            jsonContent = new ArrayList<>();
            List<Integer> nameList = new ArrayList<>();
            List<Integer> nameList2 = new ArrayList<>();
            File file = new File(localUrl);		//获取其file对象
            File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
            File file2 = new File(localUrl2);		//获取其file对象
            File[] fs2 = file2.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            for(int x=0;x<fs2.length;x++){
                nameList2.add(jsonParserService.transFileNameToInt(fs2[x].getName()));
            }
            Collections.sort(nameList);
            Collections.sort(nameList2);
            int begin;
            try{
                begin = nameList.get(nameList.indexOf(Integer.parseInt(paragraph_id)));
            }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                throw new RequestParamException("paragraph_id not found");
            }
            int end = nameList2.get(nameList2.size()-1);
            if (nameList.indexOf(begin)!=nameList.size()-1){
                end = nameList.get(nameList.indexOf(begin)+1);
            }
            System.out.println(begin);
            System.out.println(end);
            int i=0,j=0;
            while(nameList2.get(i)<=begin){
                i++;
            }
            while(nameList2.get(j)<end){
                j++;
                if(nameList2.get(j)==end){
                    j++;
                    break;
                }
            }
            for(int x=i;x<j;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl2 + "/" + nameList2.get(x) + ".json"));
            }
            return RestResult.success(jsonContent);
        }catch (NullPointerException | IOException e){
            throw new RequestParamException("Token or paragraph_id not found");
        }
    }

    @Operation(summary = "根据token释放服务端资源，" +
            "也就是用户对解析内容进行主动删除，防止由于解析文件逐渐增多，出现内存不足")
    @DeleteMapping("/{token}")
    public RestResult delete (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        String localUrl = "WPWPOI/files/parser_legal_Instrument_" + token;
        String localUrl2 = "WPWPOI/results/parser_legal_Instrument_" + token;
        File file = new File(localUrl);		//获取其file对象
        File file2 = new File(localUrl2);
        if(!file.exists()&&!file2.exists()){
            throw new RequestParamException("Token not found");
        }
        try{
            delFolder(localUrl);
            delFolder(localUrl2);
            return RestResult.success("Delete Success");
        }catch (NullPointerException e){
            throw new RequestParamException("Token not found");
        }
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
