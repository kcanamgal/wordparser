package com.example.demo.controller;

import com.example.demo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文档载入接口")
@RestController
@RequestMapping("/load_file")
public class FileController {

    @Autowired
    FileService fileService;

    @Operation(summary = "文档载入")
    @PostMapping(value = "/")
    public String loadFile(
            @Parameter(description = "文档二进制文件流") @RequestPart("file") MultipartFile file,
            @Parameter(description = "文档名称") @RequestParam(required = false) String fileName)
    {
        return fileService.loadFile(file, fileName);
    }
}
