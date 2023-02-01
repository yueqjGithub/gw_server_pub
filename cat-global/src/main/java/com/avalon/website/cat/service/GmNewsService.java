package com.avalon.website.cat.service;

import com.avalon.website.cat.model.GmNews;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
public interface GmNewsService extends IService<GmNews> {
    List<GmNews> getListBySite (String siteId);

    GmNews getDetail (String id);

    /**
     * client根据语言和站点获取文章列表，只返回已启用
     * @param siteId
     * @param lang
     * @return List<GmNews>
     */
    List<GmNews> getListBySiteAndLang (String siteId, String lang);
}
