package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("字体格式")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Font_stype {
    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("字体大小")
    private Integer fontSize;

    @ApiModelProperty("字体名")
    private String fontName;

    @ApiModelProperty("是否为粗体")
    private Boolean isBold;

    @ApiModelProperty("是否为斜体")
    private Boolean isItalic;

    @ApiModelProperty("字间距")
    private Integer fontAlignment;
}
