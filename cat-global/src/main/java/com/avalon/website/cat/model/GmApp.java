package com.avalon.website.cat.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("GM_App")
@ApiModel(value="GmApp对象", description="")
public class GmApp extends Model<GmApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "站点名")
    private String siteName;

    @ApiModelProperty(value = "分配给站点的标识")
    private String siteId;

    @ApiModelProperty(value = "站点语言配置，英文逗号隔开")
    private String language;

    @ApiModelProperty(value = "前端工程路径")
    private String proPath;

    @ApiModelProperty(value = "是否发布中")
    private Boolean publicIng;

    @ApiModelProperty(value = "站点地址")
    private String url;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
