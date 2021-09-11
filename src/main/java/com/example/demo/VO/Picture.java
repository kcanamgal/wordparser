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
    private Integer height;

    @ApiModelProperty("图片宽度")
    private Integer width;

    @ApiModelProperty("建议使用的文件扩展名")
    private String suggestFileExtension;

    @ApiModelProperty("使用base64编码后的数据内容")
    private String base64Content;

    @ApiModelProperty("文件名")
    private String filename;
}
