package com.avalon.website.cat.service.impl;

import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmLanguage;
import com.avalon.website.cat.mapper.GmLanguageMapper;
import com.avalon.website.cat.service.GmLanguageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ejlchina.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaobin.wang
 * @since 2022-04-14
 */
@Service
@Slf4j
public class GmLanguageServiceImpl extends ServiceImpl<GmLanguageMapper, GmLanguage> implements GmLanguageService {

    @Override
    public void freshLang (String url) {
        List<GmLanguage> list = list();
        List<Integer> sList = new ArrayList<>(Collections.emptyList());
        for (GmLanguage item : list) {
            sList.add(item.getId());
        }
        removeByIds(sList);
        Map map = OkHttps.async(url).get().getResult().getBody().toBean(Map.class);
        if (null != map && 0 == Integer.parseInt(String.valueOf(map.get("status")))) {
            log.info("获取常量配置多语言配置成功");
            ArrayList<GmLanguage> langList = (ArrayList<GmLanguage>) map.get("data");
            saveBatch(langList);
            log.info("刷新语言成功");
        } else {
            log.warn("获取常量配置服务多语言配置失败");
        }
    }
}
