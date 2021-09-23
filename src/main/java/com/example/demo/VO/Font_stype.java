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
    private Integer color;

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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Boolean isBold() {
        return isBold;
    }

    public void setBold(Boolean bold) {
        isBold = bold;
    }

    public Boolean isItalic() {
        return isItalic;
    }

    public void setItalic(Boolean italic) {
        isItalic = italic;
    }

    public Integer getFontAlignment() {
        return fontAlignment;
    }

    public void setFontAlignment(Integer fontAlignment) {
        this.fontAlignment = fontAlignment;
    }
}
