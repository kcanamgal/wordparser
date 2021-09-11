package com.example.demo.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("表格")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Table {
    @ApiModelProperty("在表格之前的文本")
    private String textBefore;

    @ApiModelProperty("在表格之后的文本")
    private String textAfter;

    @ApiModelProperty("表格前的段落")
    private Integer paragraphBefore;

    @ApiModelProperty("表格后的段落")
    private Integer paragraphAfter;

    @ApiModelProperty("表格文本内容")
    private String tableContent; //类型存疑
}
