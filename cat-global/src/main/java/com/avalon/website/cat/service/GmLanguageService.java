package com.avalon.website.cat.service;

import com.avalon.website.cat.model.GmLanguage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
public interface GmLanguageService extends IService<GmLanguage> {
    void freshLang (String url);
}
