package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.VO.*;
import com.example.demo.service.JsonParserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Api("文档解析接口")
@RestController
@RequestMapping("/word_parser")
public class ParserController {
    @Autowired
    JsonParserService jsonParserService;

    @Operation(summary = "获取文档内全部段落信息")
    @GetMapping("/{token}/all_paragraphs")
    public RestResult getAllParagraphs(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        System.out.println(Long.toHexString(System.currentTimeMillis()));
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "获取文档内全部表格信息")
    @GetMapping("/{token}/all_tables")
    public RestResult getAllTables(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "获取文档内全部图片信息")
    @GetMapping("/{token}/all_pics")
    public RestResult getAllPictures(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "获取文档内全部标题信息")
    @GetMapping("/{token}/all_titles")
    public RestResult getAllTitles(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token、段落id，获取段落详细信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}")
    public RestResult getParagraph(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token、段落id，获取段落的详细段落格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/paragraph_stype")
    public RestResult getParagraph_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token、段落id，获取段落的详细字体格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/font_stype")
    public RestResult getParagraph_Font_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部段落信息")
    @GetMapping("/{token}/title/{paragraph_id}/all_paragraphs")
    public RestResult getTitleParagraphs (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) throws IOException {
        String localUrl = "WPWPOI/results/test1.json";
        return RestResult.success(jsonParserService.jsonToObject(localUrl));
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
