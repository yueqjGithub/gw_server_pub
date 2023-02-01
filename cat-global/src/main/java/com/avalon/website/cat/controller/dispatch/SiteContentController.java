package com.avalon.website.cat.controller.dispatch;

import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmApp;
import com.avalon.website.cat.model.GmBanners;
import com.avalon.website.cat.model.GmFixedContent;
import com.avalon.website.cat.model.GmNews;
import com.avalon.website.cat.service.GmAppService;
import com.avalon.website.cat.service.GmBannersService;
import com.avalon.website.cat.service.GmFixedContentService;
import com.avalon.website.cat.service.GmNewsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "站点获取资源")
public class SiteContentController {
    @Resource
    GmFixedContentService fcService;

    @Resource
    GmAppService appService;

    @Resource
    GmNewsService gmNewsService;

    @Resource
    GmBannersService gmBannersService;

    @GetMapping("/fixed/{siteId}/{fixedId}")
    @ApiOperation("获取固定内容")
    public AvalonHttpResp<?> getFixedContent (@PathVariable String siteId, @PathVariable String fixedId) {
        QueryWrapper<GmApp> siteWp = new QueryWrapper<>();
        GmApp site = appService.getOne(siteWp.eq("site_id", siteId));
        if (null == site) {
            return AvalonHttpResp.failed("站点标识错误");
        }
        QueryWrapper<GmFixedContent> fcWp = new QueryWrapper<>();
        GmFixedContent result = fcService.getOne(fcWp.eq("site_id", site.getId()).eq("fixed_id", fixedId));
        return AvalonHttpResp.ok(result);
    }

    @GetMapping("/news/{siteId}/{lang}")
    @ApiOperation("获取站点文章")
    public AvalonHttpResp<?> getNews (
            @PathVariable String siteId,
            @PathVariable String lang
    ) {
        QueryWrapper<GmApp> siteWp = new QueryWrapper<>();
        GmApp site = appService.getOne(siteWp.eq("site_id", siteId));
        if (null == site) {
            return AvalonHttpResp.failed("站点标识错误");
        }
        return AvalonHttpResp.ok(gmNewsService.getListBySiteAndLang(site.getId(), lang));
    }

    @GetMapping("/news/detail")
    @ApiOperation("获取文章详情")
    public AvalonHttpResp<GmNews> getDetails (@RequestParam String id) {
        if (null == id) {
            return AvalonHttpResp.failed("id为空");
        }
        return AvalonHttpResp.ok(gmNewsService.getDetail(id));
    }

    @GetMapping("/banners/{siteId}")
    @ApiOperation("获取banner")
    public AvalonHttpResp<List<GmBanners>> getBanners (@PathVariable String siteId) {
        GmApp app = appService.getOne(new LambdaQueryWrapper<GmApp>().eq(GmApp::getSiteId, siteId));
        LambdaQueryWrapper<GmBanners> lwp = new LambdaQueryWrapper<>();
        lwp.eq(GmBanners::getSiteId, app.getId()).eq(GmBanners::isStatus, true).orderByDesc(GmBanners::getUpdateTime);
        return AvalonHttpResp.ok(gmBannersService.list(lwp));
    }
}
