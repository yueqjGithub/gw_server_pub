package com.avalon.website.cat.controller;


import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmLanguage;
import com.avalon.website.cat.service.GmLanguageService;
import com.ejlchina.okhttps.OkHttps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/admin/lang")
@Api(tags = "语言")
@Slf4j
public class GmLanguageController {
    @Resource
    GmLanguageService gmLanguageService;

    @Value("${constant.server.url}")
    private String constantUrl;

    @GetMapping("")
    @ApiOperation("获取所有语言")
    public AvalonHttpResp<List<GmLanguage>> getAllLang () {
        return AvalonHttpResp.ok(gmLanguageService.list());
    }

    @GetMapping("/refresh")
    @ApiOperation("刷新语言")
    public AvalonHttpResp<?> freshLang () {
        gmLanguageService.freshLang(constantUrl);
        return AvalonHttpResp.ok();
    }
}
