package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Service
public class JsonParserService {
    public Object jsonToObject(String localUrl) throws IOException {
        File jsonFile = ResourceUtils.getFile(localUrl);
        String json = FileUtils.readFileToString(jsonFile);
        return JSON.parse(json);
    }
}