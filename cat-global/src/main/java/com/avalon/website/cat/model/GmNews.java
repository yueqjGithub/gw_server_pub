package com.avalon.website.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
@TableName("GM_News")
@ApiModel(value="GmNews对象", description="")
public class GmNews extends Model<GmNews> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "属于哪个站点")
    private String siteId;

    @ApiModelProperty(value = "文章语言")
    private String language;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "最后编辑人")
    private String lastEditor;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "封面图")
    private String titleImage;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
