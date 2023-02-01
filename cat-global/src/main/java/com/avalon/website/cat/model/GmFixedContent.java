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
@TableName("GM_Fixed_Content")
@ApiModel(value="GmFixedContent对象", description="")
public class GmFixedContent extends Model<GmFixedContent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "对应站点关系")
    private String siteId;

    @ApiModelProperty(value = "分配给前端的固定内容标识")
    private String fixedId;

    @ApiModelProperty(value = "固定内容点名称")
    private String name;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "图片集合")
    private String images;

    @ApiModelProperty(value = "文字内容")
    private String text;

    @ApiModelProperty(value = "视频集合")
    private String video;

    @ApiModelProperty(value = "富文本")
    private String richText;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
