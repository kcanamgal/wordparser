package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("段落")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paragraph {
    @ApiModelProperty("段落文本")
    private String paragraphText;

    @ApiModelProperty("段落编号")
    private Integer paragraphId;

    @ApiModelProperty("字体大小")
    private Integer fontSize;

    @ApiModelProperty("字体名")
    private String fontName;

    @ApiModelProperty("是否为粗体")
    private Boolean isBold;

    @ApiModelProperty("是否为斜体")
    private Boolean isItalic;

    @ApiModelProperty("是否在表格中")
    private Boolean isInTable;

    @ApiModelProperty("大纲级别")
    private Integer lvl;  //类型存疑

    @ApiModelProperty("行距")
    private Integer lineSpacing;

    @ApiModelProperty("字间距")
    private Integer fontAlignment;

    @ApiModelProperty("是否到表尾")
    private Boolean isTableRowEnd;

    @ApiModelProperty("左方缩排")
    private Integer indentFromLeft;  //类型存疑

    @ApiModelProperty("右方缩排")
    private Integer indentFromRight;  //类型存疑

    @ApiModelProperty("第一行缩排")
    private Integer firstLineIndent;  //类型存疑
}
