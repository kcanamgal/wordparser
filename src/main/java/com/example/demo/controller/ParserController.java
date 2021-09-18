package com.example.demo.controller;

import com.example.demo.VO.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Api("文档解析接口")
@RestController
@RequestMapping("/word_parser")
public class ParserController {

    @Operation(summary = "获取文档内全部段落信息")
    @GetMapping("/{token}/all_paragraphs")
    public List<Paragraph> getAllParagraphs(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }

    @Operation(summary = "获取文档内全部表格信息")
    @GetMapping("/{token}/all_tables")
    public List<Table> getAllTables(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }

    @Operation(summary = "获取文档内全部图片信息")
    @GetMapping("/{token}/all_pics")
    public List<Paragraph> getAllPictures(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }

    @Operation(summary = "获取文档内全部标题信息")
    @GetMapping("/{token}/all_titles")
    public List<Paragraph> getAllTitles(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }

    @Operation(summary = "根据token、段落id，获取段落详细信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}")
    public Paragraph getParagraph(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token、段落id，获取段落的详细段落格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/paragraph_stype")
    public Paragraph_stype getParagraph_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token、段落id，获取段落的详细字体格式")
    @GetMapping("/{token}/paragraph/{paragraph_id}/font_stype")
    public List<Font_stype> getParagraph_Font_stype(
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部段落信息")
    @GetMapping("/{token}/title/{paragraph_id}/all_paragraphs")
    public List<Paragraph> getTitleParagraphs (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部图片信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}/all_pics")
    public List<Picture> getTitlePictures (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token、标题对应的段落id，获取标题下全部表格信息")
    @GetMapping("/{token}/paragraph/{paragraph_id}/all_tables")
    public List<Table> getTitleTables (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token,
            @Parameter(description = "段落id") @PathVariable String paragraph_id) {
        return null;
    }

    @Operation(summary = "根据token释放服务端资源，" +
            "也就是用户对解析内容进行主动删除，防止由于解析文件逐渐增多，出现内存不足")
    @DeleteMapping("/{token}")
    public Boolean delete (
            @Parameter(description = "文档的唯一标识token") @PathVariable String token) {
        return null;
    }
}
