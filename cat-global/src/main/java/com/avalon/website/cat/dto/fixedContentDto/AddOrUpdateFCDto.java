package com.avalon.website.cat.dto.fixedContentDto;

import com.avalon.website.cat.model.GmFixedContent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrUpdateFCDto extends GmFixedContent {
    private String id;

    @ApiModelProperty(value = "站点ID")
    @NotBlank(message = "siteId不能为空")
    private String siteId;

    @ApiModelProperty(value = "固定内容标识")
    @NotBlank(message = "fixedId不能为空")
    private String fixedId;

    @ApiModelProperty(value = "内容名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "图片")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "富文本")
    private String richText;
}
