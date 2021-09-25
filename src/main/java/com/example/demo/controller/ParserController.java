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
        String localUrl = "WPWPOI/results/parser_legal_Instrument_" + token + "/paragraphs/paragraph";
        jsonContent = new ArrayList<>();
        File file = new File(localUrl);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        try{
            for(int x=0;x<fs.length;x++){
                jsonContent.add(jsonParserService.jsonToObject(localUrl + "/" + x + ".json"));
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
            List<Integer> nameList = new ArrayList<>();
            jsonContent = new ArrayList<>();
            File file = new File(localUrl);		//获取其file对象
            File[] fs = file.listFiles();	    //遍历path下的文件和目录，放在File数组中
            File file2 = new File(localUrl2);		//获取其file对象
            File[] fs2 = file2.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(int x=0;x<fs.length;x++){
                nameList.add(jsonParserService.transFileNameToInt(fs[x].getName()));
            }
            Collections.sort(nameList);
            int begin = nameList.get(nameList.indexOf(Integer.parseInt(paragraph_id)));;
            int end = fs2.length;
            if (nameList.indexOf(begin)!=nameList.size()-1){
                end = nameList.get(nameList.indexOf(begin)+1);
            }
            System.out.println(begin);
            System.out.println(end);
            for(int x=begin+1;x<end;x++){
                System.out.println(x);
                jsonContent.add(jsonParserService.jsonToObject(localUrl2 + "/" + x + ".json"));
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
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部表格信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}/all_tables")
    public RestResult getTitleTables (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token释放服务端资源，" +
            "也就是用户对解析内容进行主动删除，防止由于解析文件逐渐增多，出现内存不足")
    @DeleteMapping("/{token}")
    public Boolean delete (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }
}
