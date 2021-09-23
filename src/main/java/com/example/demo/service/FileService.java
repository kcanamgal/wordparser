package com.example.demo.service;

import com.example.demo.VO.Token;
import com.example.demo.util.HWPFUtils;
import com.example.demo.util.XWPFUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class FileService {

    private static final String FILE_FORMAT_NOT_SUPPORTED = "不支持的文件格式";

    /**
     * 根据输入的文件流解析
     * 需要支持200M以上的文件上传
     * 目前测得文件的content-type：docx是"application/octet-stream"，
     *                            docx是"application/octet-stream"，
     *                            wps是”“
     *                            pdf是"application/pdf"；
     * @param file
     * @param fileName
     * @return
     */
    public Token loadFile(MultipartFile file, String fileName) {
        String name = file.getOriginalFilename();
        assert name != null;
        String suffix = name.substring(name.lastIndexOf('.') + 1);
        try {
            switch (suffix) {
                case "doc":
                case "wps": HWPFUtils.parse(file.getInputStream()); break;
                case "docx": XWPFUtils.parse(file.getInputStream()); break;
                case "pdf": break;
                default:
                    throw new RuntimeException(FILE_FORMAT_NOT_SUPPORTED);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new Token(name);
    }
}
