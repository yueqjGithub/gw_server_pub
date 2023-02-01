package com.avalon.website.cat.service.impl;

import com.avalon.website.cat.model.GmNews;
import com.avalon.website.cat.mapper.GmNewsMapper;
import com.avalon.website.cat.service.GmNewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@Service
public class GmNewsServiceImpl extends ServiceImpl<GmNewsMapper, GmNews> implements GmNewsService {
    @Override
    public List<GmNews> getListBySite (String siteId) {
        QueryWrapper<GmNews> wp = new QueryWrapper<>();
        wp.eq("site_id", siteId).select("id", "site_id", "title_image", "last_editor", "enable", "language", "title", "sub_title", "author", "create_time", "update_time")
                .orderByDesc("create_time");
        return list(wp);
    }

    @Override
    public GmNews getDetail (String id) {
        return getById(id);
    }

    @Override
    public List<GmNews> getListBySiteAndLang (String siteId, String lang) {
        QueryWrapper<GmNews> wp = new QueryWrapper<>();
        wp.eq("site_id", siteId)
                .eq("language", lang)
                .eq("enable", true)
                .select("id", "site_id", "title_image", "last_editor", "enable", "language", "title", "sub_title", "author", "create_time", "update_time")
                .orderByDesc("update_time");
        return list(wp);
    }
}
