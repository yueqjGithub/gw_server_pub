package com.avalon.website.cat.controller;


import com.avalon.website.cat.dto.fixedContentDto.AddOrUpdateFCDto;
import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmFixedContent;
import com.avalon.website.cat.service.GmAppService;
import com.avalon.website.cat.service.GmFixedContentService;
import com.avalon.website.cat.utils.ZzBeanCopierUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/admin/fixedContent")
@Api(tags = "固定内容")
public class GmFixedContentController {
    @Resource
    GmFixedContentService fixedContentService;

    @Resource
    GmAppService gmAppService;

    @GetMapping("")
    @ApiOperation("根据站点ID获取固定内容列表")
    public AvalonHttpResp<List<GmFixedContent>> getList (String siteId) {
        if (null == siteId) {
            return AvalonHttpResp.failed("站点ID不能为空");
        }
        if (gmAppService.getById(siteId) == null) {
            return AvalonHttpResp.failed("没有对应站点信息");
        }
        QueryWrapper<GmFixedContent> wp = new QueryWrapper<>();
        wp.eq("site_id", siteId);
        return AvalonHttpResp.ok(fixedContentService.list(wp));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除固定内容")
    public AvalonHttpResp<?> deleteFixed (@PathVariable(required = true) String id) {
        return AvalonHttpResp.ok(fixedContentService.removeById(id));
    }

    @PostMapping("")
    @ApiOperation("添加固定内容")
    public AvalonHttpResp<?> addFixed (@Validated @RequestBody AddOrUpdateFCDto dto) {
        if (gmAppService.getById(dto.getSiteId()) == null) {
            return AvalonHttpResp.failed("未找到对应站点");
        }

        QueryWrapper<GmFixedContent> wp = new QueryWrapper<>();
        // 验证fixeId在站点下唯一
        wp.eq("site_id", dto.getSiteId()).eq("fixed_id", dto.getFixedId());
        if (fixedContentService.list(wp).size() > 0) {
            return AvalonHttpResp.failed("站点下标识ID重复");
        } else {
            wp.clear();
        }

        // 验证标识名称唯一
        wp.eq("name", dto.getName()).eq("site_id", dto.getSiteId());
        if (fixedContentService.list(wp).size() > 0) {
            return AvalonHttpResp.failed("站点下标识标识名称重复");
        }

        GmFixedContent target = ZzBeanCopierUtils.copy(dto, new GmFixedContent());
        return AvalonHttpResp.ok(fixedContentService.save(target));
    }

    @PutMapping("")
    @ApiOperation("更新固定内容")
    public AvalonHttpResp<?> updateFixed (@Validated @RequestBody AddOrUpdateFCDto dto) {
        if (gmAppService.getById(dto.getSiteId()) == null) {
            return AvalonHttpResp.failed("未找到对应站点");
        }
        GmFixedContent already = fixedContentService.getById(dto.getId());
        QueryWrapper<GmFixedContent> wp = new QueryWrapper<>();
        // 验证标识ID唯一
        wp.eq("site_id", dto.getSiteId()).eq("fixed_id", dto.getFixedId());
        if (!Objects.equals(fixedContentService.getOne(wp).getId(), dto.getId())) {
            return AvalonHttpResp.failed("固定内容标识被占用");
        } else {
            wp.clear();
        }

        // 验证标识名称唯一
        wp.eq("site_id", dto.getSiteId()).eq("name", dto.getName());
        if (fixedContentService.getOne(wp) != null && !Objects.equals(fixedContentService.getOne(wp).getId(), dto.getId())) {
            return AvalonHttpResp.failed("名称被占用");
        }

        GmFixedContent target = ZzBeanCopierUtils.copy(dto, already);
        return AvalonHttpResp.ok(fixedContentService.updateById(target));
    }
}
