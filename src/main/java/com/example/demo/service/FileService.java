package com.example.demo.service;

import com.example.demo.VO.Token;
import com.example.demo.exceptions.RequestParamException;
import com.example.demo.util.HWPFUtils;
import com.example.demo.util.XWPFUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileService {

    private static final String FILE_FORMAT_NOT_SUPPORTED = "不支持的文件格式";
    private static final String FILE_PREFIX = "parser_legal_Instrument_";

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
        String token = Long.toHexString(System.currentTimeMillis()) ;
        storeFile(token,file,name);
        String suffix = name.substring(name.lastIndexOf('.') + 1);
        try {
            switch (suffix) {
                case "doc":
                case "wps":
                    HWPFUtils.parse(file.getInputStream(),token); break;
                case "docx":
                    XWPFUtils.parse(file.getInputStream(),token); break;
                case "pdf":
                    break;
                default:
                    throw new RequestParamException(FILE_FORMAT_NOT_SUPPORTED);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Token(token);
    }


    private void storeFile(String token,MultipartFile filecontent, String name) {
        String localUrl = FILE_PREFIX + token;

        OutputStream os = null;
        InputStream inputStream = null;
        try {
            inputStream = filecontent.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = "WPWPOI/files/"+localUrl+"/";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + name);
            // 开始读取
            assert inputStream != null;
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                assert os != null;
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File fileRes=new File("WPWPOI/results/"+localUrl);
        if(!fileRes.exists()){//如果文件夹不存在
            fileRes.mkdir();//创建文件夹
        }
        File parFile = new File("WPWPOI/results/"+localUrl+"/paragraphs");
        if(!parFile.exists()){//如果文件夹不存在
            parFile.mkdir();//创建文件夹
        }
        File parParFile = new File("WPWPOI/results/"+localUrl+"/paragraphs/paragraph");
        if(!parParFile.exists()){//如果文件夹不存在
            parParFile.mkdir();//创建文件夹
        }
        File parFonFile = new File("WPWPOI/results/"+localUrl+"/paragraphs/font_stype");
        if(!parFonFile.exists()){//如果文件夹不存在
            parFonFile.mkdir();//创建文件夹
        }
        File parStyFile = new File("WPWPOI/results/"+localUrl+"/paragraphs/paragraph_stype");
        if(!parStyFile.exists()){//如果文件夹不存在
            parStyFile.mkdir();//创建文件夹
        }
        File picFile = new File("WPWPOI/results/"+localUrl+"/pics");
        if(!picFile.exists()){//如果文件夹不存在
            picFile.mkdir();//创建文件夹
        }
        File tabFile = new File("WPWPOI/results/"+localUrl+"/tables");
        if(!tabFile.exists()){//如果文件夹不存在
            tabFile.mkdir();//创建文件夹
        }
        File tilFile = new File("WPWPOI/results/"+localUrl+"/titles");
        if(!tilFile.exists()){//如果文件夹不存在
            tilFile.mkdir();//创建文件夹
        }
    }


}
