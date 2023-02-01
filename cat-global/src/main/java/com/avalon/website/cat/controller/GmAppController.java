package com.avalon.website.cat.controller;

import com.avalon.website.cat.dto.appDto.AddOrUpdateAppDto;
import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmApp;
import com.avalon.website.cat.service.GmAppService;
import com.avalon.website.cat.utils.ZzBeanCopierUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
@Slf4j
@RestController
@RequestMapping("/admin/sites")
@Api(tags = "站点")
public class GmAppController {
    @Resource
    GmAppService gmAppService;

    @GetMapping("")
    @ApiOperation("站点列表")
    public AvalonHttpResp<List<GmApp>> getApps (String appLimit) {
        QueryWrapper<GmApp> wp = new QueryWrapper<>();
        List<GmApp> appList = gmAppService.list(wp);
        if (appLimit != null && !Objects.equals(appLimit, "*")) {
            List<String> limits = Arrays.asList(appLimit.split(","));
            List<GmApp> results = new ArrayList<>();
            for (GmApp item : appList) {
                if (limits.contains(item.getSiteId())) {
                    results.add(item);
                }
            }
            return AvalonHttpResp.ok(results);
        }
        return AvalonHttpResp.ok(appList);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除站点")
    public AvalonHttpResp<?> delApps (@PathVariable String id) {
        GmApp app = gmAppService.getById(id);
        if (app == null) {
            return AvalonHttpResp.failed("无效的站点");
        }
        gmAppService.removeById(id);
        return AvalonHttpResp.ok("删除成功");
    }

    @PostMapping("")
    @ApiOperation("新增或删除站点")
    public AvalonHttpResp<?> addOrUpdateApp (@Validated @RequestBody AddOrUpdateAppDto dto) {
        LambdaQueryWrapper<GmApp> wp = new LambdaQueryWrapper<>();
        if (dto.getId() != null) {
            GmApp already = gmAppService.getById(dto.getId());
            if (already == null) {
                return AvalonHttpResp.failed("未找到对应站点");
            }
            GmApp idAlready = gmAppService.getOne(wp.eq(GmApp::getSiteId, dto.getSiteId()));
            if (idAlready != null && !Objects.equals(idAlready.getId(), already.getId())) {
                return AvalonHttpResp.failed("站点标识重复");
            } else {
                wp.clear();
            }
            GmApp nameAlready = gmAppService.getOne(wp.eq(GmApp::getSiteName, dto.getSiteName()));
            if (nameAlready != null && !Objects.equals(nameAlready.getId(), already.getId())) {
                return AvalonHttpResp.failed("站点名称重复");
            }
            GmApp target = ZzBeanCopierUtils.copy(dto, already);
            return AvalonHttpResp.ok(gmAppService.updateById(target));
        }
        if (gmAppService.getOne(wp.eq(GmApp::getSiteId, dto.getSiteId())) != null) {
            return AvalonHttpResp.failed("站点标识重复");
        } else {
            wp.clear();
        }
        if (gmAppService.getOne(wp.eq(GmApp::getSiteName, dto.getSiteName())) != null) {
            return AvalonHttpResp.failed("站点名称重复");
        }
        GmApp target = ZzBeanCopierUtils.copy(dto, new GmApp());
        return AvalonHttpResp.ok(gmAppService.save(target));
    }
}
