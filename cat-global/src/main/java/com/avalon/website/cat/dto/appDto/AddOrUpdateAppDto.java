package com.avalon.website.cat.dto.appDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrUpdateAppDto {
    private String id;

    @ApiModelProperty(value = "站点名")
    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    @ApiModelProperty(value = "分配给站点的标识")
    @NotBlank(message = "站点标识不能为空")
    private String siteId;

    @ApiModelProperty(value = "站点语言配置，英文逗号隔开")
    private String language;

    @ApiModelProperty(value = "前端工程路径")
    @NotBlank(message = "前端工程路径不能为空")
    private String proPath;

    @ApiModelProperty(value = "站点地址")
    private String url;
}
