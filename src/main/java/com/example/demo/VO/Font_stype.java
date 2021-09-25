package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Objects;

@ApiModel("字体格式")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Font_stype {
    @ApiModelProperty("段落编号")
    private BigInteger paragraphId;

    @ApiModelProperty("颜色")
    private Integer color;

    @ApiModelProperty("字体大小")
    private Double fontSize;

    @ApiModelProperty("字体名")
    private String fontName;

    @ApiModelProperty("是否为粗体")
    private Boolean isBold;

    @ApiModelProperty("是否为斜体")
    private Boolean isItalic;

    @ApiModelProperty("字间距")
    private Integer fontAlignment;

    public BigInteger getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(BigInteger paragraphId) {
        this.paragraphId = paragraphId;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Font_stype that = (Font_stype) o;
        return Objects.equals(color, that.color) &&
                Objects.equals(fontSize, that.fontSize) &&
                Objects.equals(fontName, that.fontName) &&
                Objects.equals(isBold, that.isBold) &&
                Objects.equals(isItalic, that.isItalic) &&
                Objects.equals(fontAlignment, that.fontAlignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, fontSize, fontName, isBold, isItalic, fontAlignment);
    }
}
