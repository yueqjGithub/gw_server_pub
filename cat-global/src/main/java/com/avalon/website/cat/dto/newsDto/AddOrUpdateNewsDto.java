package com.avalon.website.cat.dto.newsDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrUpdateNewsDto {
    private String id;

    @ApiModelProperty("站点ID")
    @NotBlank(message = "站点ID不能为空")
    private String siteId;

    @ApiModelProperty("语言")
    @NotBlank(message = "文章语言不能为空")
    private String language;

    @ApiModelProperty("文章标题")
    @NotBlank(message = "文章标题不能为空")
    private String title;

    @ApiModelProperty("副标题")
    private String subTitle;
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("最后编辑人")
    private String lastEditor;
    @ApiModelProperty("是否启用")
    private Boolean enable;

    private String titleImage;

    private String content;
}
