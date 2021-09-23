package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("图片")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Picture {
    @ApiModelProperty("在图片之前的文本")
    private String textBefore;

    @ApiModelProperty("在图片之后的文本")
    private String textAfter;

    @ApiModelProperty("图片高度")
    private Double height;

    @ApiModelProperty("图片宽度")
    private Double width;

    @ApiModelProperty("建议使用的文件扩展名")
    private String suggestFileExtension;

    @ApiModelProperty("使用base64编码后的数据内容")
    private String base64Content;

    @ApiModelProperty("文件名")
    private String filename;

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getSuggestFileExtension() {
        return suggestFileExtension;
    }

    public void setSuggestFileExtension(String suggestFileExtension) {
        this.suggestFileExtension = suggestFileExtension;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
