package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

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
    private List<Double> fontSize;

    @ApiModelProperty("字体名")
    private List<String> fontName;

    @ApiModelProperty("是否为粗体")
    private List<Boolean> isBold;

    @ApiModelProperty("是否为斜体")
    private List<Boolean> isItalic;

    @ApiModelProperty("是否在表格中")
    private Boolean isInTable;

    @ApiModelProperty("大纲级别")
    private Integer lvl;

    @ApiModelProperty("行距")
    private Integer lineSpacing;

    @ApiModelProperty("字间距")
    private Integer fontAlignment;

    @ApiModelProperty("是否到表尾")
    private Boolean isTableRowEnd;

    @ApiModelProperty("左方缩排")
    private Integer indentFromLeft;

    @ApiModelProperty("右方缩排")
    private Integer indentFromRight;

    @ApiModelProperty("第一行缩排")
    private Integer firstLineIndent;

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

    public List<Double> getFontSize() {
        return fontSize;
    }

    public void setFontSize(List<Double> fontSize) {
        this.fontSize = fontSize;
    }

    public List<String> getFontName() {
        return fontName;
    }

    public void setFontName(List<String> fontName) {
        this.fontName = fontName;
    }

    public List<Boolean> getBold() {
        return isBold;
    }

    public void setBold(List<Boolean> bold) {
        isBold = bold;
    }

    public List<Boolean> getItalic() {
        return isItalic;
    }

    public void setItalic(List<Boolean> italic) {
        isItalic = italic;
    }

    public Boolean isInTable() {
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

    public Boolean isTableRowEnd() {
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
