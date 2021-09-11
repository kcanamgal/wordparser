package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("段落格式")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paragraph_stype {
    @ApiModelProperty("行距")
    private Integer lineSpacing;

    @ApiModelProperty("左方缩排")
    private Integer indentFromLeft; //类型存疑

    @ApiModelProperty("右方缩排")
    private Integer indentFromRight; //类型存疑

    @ApiModelProperty("第一行缩排")
    private Integer firstLineIndent; //类型存疑

    @ApiModelProperty("大纲级别")
    private Integer lvl; //类型存疑
}
