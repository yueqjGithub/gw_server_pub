package com.avalon.website.cat.dto.bannerDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBannerDto {
    private String id;

    @NotNull(message = "站点ID不能为空")
    private String siteId;

    private String imgUrl;

    @NotNull(message = "无效的banner高度")
    private String minHeight;

    @NotNull(message = "请设置banner类型")
    @ApiModelProperty("1-pc,2-mobile")
    private Integer type;

    @ApiModelProperty("是否启用")
    private boolean status;

    @ApiModelProperty("banner内元素集合JSON字符串")
    private String elements;
}
