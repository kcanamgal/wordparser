package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@ApiModel("段落")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paragraph {
    @ApiModelProperty("段落文本")
    private String paragraphText;

    @ApiModelProperty("段落编号")
    private BigInteger paragraphId;

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

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public BigInteger getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(BigInteger paragraphId) {
        this.paragraphId = paragraphId;
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

    public Boolean getBold() {
        return isBold;
    }

    public void setBold(Boolean bold) {
        isBold = bold;
    }

    public Boolean getItalic() {
        return isItalic;
    }

    public void setItalic(Boolean italic) {
        isItalic = italic;
    }

    public Boolean getInTable() {
        return isInTable;
    }

    public void setInTable(Boolean inTable) {
        isInTable = inTable;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Integer getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(Integer lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public Integer getFontAlignment() {
        return fontAlignment;
    }

    public void setFontAlignment(Integer fontAlignment) {
        this.fontAlignment = fontAlignment;
    }

    public Boolean getTableRowEnd() {
        return isTableRowEnd;
    }

    public void setTableRowEnd(Boolean tableRowEnd) {
        isTableRowEnd = tableRowEnd;
    }

    public Integer getIndentFromLeft() {
        return indentFromLeft;
    }

    public void setIndentFromLeft(Integer indentFromLeft) {
        this.indentFromLeft = indentFromLeft;
    }

    public Integer getIndentFromRight() {
        return indentFromRight;
    }

    public void setIndentFromRight(Integer indentFromRight) {
        this.indentFromRight = indentFromRight;
    }

    public Integer getFirstLineIndent() {
        return firstLineIndent;
    }

    public void setFirstLineIndent(Integer firstLineIndent) {
        this.firstLineIndent = firstLineIndent;
    }
}
