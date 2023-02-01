package com.avalon.website.cat.controller;


import com.avalon.website.cat.dto.newsDto.AddOrUpdateNewsDto;
import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.mapper.GmNewsMapper;
import com.avalon.website.cat.model.GmApp;
import com.avalon.website.cat.model.GmNews;
import com.avalon.website.cat.service.GmAppService;
import com.avalon.website.cat.service.GmNewsService;
import com.avalon.website.cat.utils.ZzBeanCopierUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/admin/news")
@Api(tags = "新闻")
public class GmNewsController {

    @Resource
    GmAppService gmAppService;

    @Resource
    GmNewsService gmNewsService;

    @Resource
    GmNewsMapper gmNewsMapper;

    @GetMapping("")
    @ApiOperation("获取站点新闻")
    public AvalonHttpResp<List<GmNews>> getNews (String siteId) {
        if (null == siteId) {
            return AvalonHttpResp.failed("siteId不能为空");
        }
        if (gmAppService.getById(siteId) == null) {
            return AvalonHttpResp.failed("未找到站点");
        }
        return AvalonHttpResp.ok(gmNewsService.getListBySite(siteId));
    }

    @GetMapping("{id}")
    @ApiOperation("获取新闻详情")
    public AvalonHttpResp<GmNews> getNewsDetail (@PathVariable String id) {
        if (null == id) {
            return AvalonHttpResp.failed("文章ID不能为空");
        }
        if (gmNewsService.getById(id) == null) {
            return AvalonHttpResp.failed("未找到对应文章");
        }
        return AvalonHttpResp.ok(gmNewsService.getDetail(id));
    }

    @PostMapping("")
    @ApiOperation("添加文章")
    public AvalonHttpResp<?> addNews (@Validated @RequestBody AddOrUpdateNewsDto dto) {
        GmApp app = gmAppService.getById(dto.getSiteId());
        if (app == null) {
            return AvalonHttpResp.failed("未找到对应站点");
        }
        GmNews target = ZzBeanCopierUtils.copy(dto, new GmNews());
        return AvalonHttpResp.ok(gmNewsMapper.insert(target));
    }

    @PutMapping("")
    @ApiOperation("修改文章")
    public AvalonHttpResp<?> updateNews (@Validated @RequestBody AddOrUpdateNewsDto dto) {
        GmNews already = gmNewsService.getById(dto.getId());
        if (null == already) {
            return AvalonHttpResp.failed("未找到对应文章");
        }
        GmNews news = ZzBeanCopierUtils.copy(dto, already);
        news.setUpdateTime(null);
        return AvalonHttpResp.ok(gmNewsMapper.updateById(news));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章")
    public AvalonHttpResp<?> deleteNews (@PathVariable String id) {
        if (null == id) {
            return AvalonHttpResp.failed("id不能为空");
        }
        gmNewsService.removeById(id);
        return AvalonHttpResp.ok("删除成功");
    }
}
