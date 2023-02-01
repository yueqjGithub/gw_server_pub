package com.avalon.website.cat.controller;

import com.avalon.website.cat.dto.bannerDto.AddBannerDto;
import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmApp;
import com.avalon.website.cat.model.GmBanners;
import com.avalon.website.cat.service.GmAppService;
import com.avalon.website.cat.service.GmBannersService;
import com.avalon.website.cat.utils.ZzBeanCopierUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/banners")
@Api(tags = "Banner管理")
public class GmBannersController {
    @Resource
    GmBannersService gmBannersService;

    @Resource
    GmAppService gmAppService;

    @GetMapping("/{siteId}")
    @ApiOperation("根据站点获取banner")
    public AvalonHttpResp<List<GmBanners>> getBanners (@PathVariable String siteId) {
        LambdaQueryWrapper<GmBanners> lwp = new LambdaQueryWrapper<>();
        lwp.eq(GmBanners::getSiteId, siteId);
        return AvalonHttpResp.ok(gmBannersService.list(lwp));
    }

    @PostMapping("/add")
    @ApiOperation("新增Banner")
    public AvalonHttpResp<?> addBanner (@Validated @RequestBody AddBannerDto dto) {
        String siteId = dto.getSiteId();
        GmApp app = gmAppService.getById(siteId);
        if (null == app) {
            return AvalonHttpResp.failed("无效的站点ID");
        }
        GmBanners copy = ZzBeanCopierUtils.copy(dto, new GmBanners());
        return AvalonHttpResp.ok(gmBannersService.save(copy));
    }

    @PostMapping("/update")
    @ApiOperation("更新banner")
    public AvalonHttpResp<?> updateBanner (@Validated @RequestBody AddBannerDto dto) {
        if (null == dto.getId()) {
            return AvalonHttpResp.failed("id不能为空");
        }
        GmBanners banner = gmBannersService.getById(dto.getId());
        GmBanners copy = ZzBeanCopierUtils.copy(dto, banner);
        return AvalonHttpResp.ok(gmBannersService.updateById(copy));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除banner")
    public AvalonHttpResp<?> delBanner (@PathVariable String id) {
        if (null == gmBannersService.getById(id)) {
            return AvalonHttpResp.failed("未找到对应数据");
        }
        return AvalonHttpResp.ok(gmBannersService.removeById(id));
    }
}
