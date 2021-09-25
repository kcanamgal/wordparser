package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@ApiModel("段落格式")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paragraph_stype {
    @ApiModelProperty("段落编号")
    private BigInteger paragraphId;

    @ApiModelProperty("行距")
    private Double lineSpacing;

    @ApiModelProperty("左方缩排")
    private Integer indentFromLeft;

    @ApiModelProperty("右方缩排")
    private Integer indentFromRight;

    @ApiModelProperty("第一行缩排")
    private Integer firstLineIndent;

    @ApiModelProperty("大纲级别")
    private Integer lvl;

    public BigInteger getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(BigInteger paragraphId) {
        this.paragraphId = paragraphId;
    }

    public Double getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(Double lineSpacing) {
        this.lineSpacing = lineSpacing;
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

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }
}
