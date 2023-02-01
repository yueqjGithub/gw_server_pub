package com.avalon.website.cat.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("GM_Language")
@ApiModel(value="GmLanguage对象", description="")
public class GmLanguage extends Model<GmLanguage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Integer id;

    @ApiModelProperty(value = "语言中文名称")
    private String name;
    @ApiModelProperty(value = "语言代码")
    private String code;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
