package com.avalon.website.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("GM_Banners")
@ApiModel("Banner Model")
public class GmBanners extends Model<GmBanners> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "对应站点关系")
    private String siteId;

    @ApiModelProperty(value = "底图地址")
    private String imgUrl;

    @ApiModelProperty(value = "元素信息")
    private String elements;

    @ApiModelProperty(value = "banner最小高度")
    private String minHeight;

    @ApiModelProperty(value = "是否启用")
    private boolean status;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    @ApiModelProperty(value = "设备")
    private Integer type;
}
