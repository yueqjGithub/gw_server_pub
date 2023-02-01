package com.avalon.website.cat.config;

import com.avalon.sdk.iam.IamInterceptor;
import com.avalon.sdk.iam.bo.DataAuthBo;
import com.avalon.website.cat.constant.IamLimitEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${iam.auth.authHost}")
    private String authHost;
    @Value("${iam.auth.projectToken}")
    private String projectToken;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Map<String, DataAuthBo> authMap = IamLimitEnum.getAll();
        registry
                .addInterceptor(new IamInterceptor(authMap, authHost, projectToken)).addPathPatterns("/admin/**")
                .excludePathPatterns("/api/**");
//                .excludePathPatterns("/app/sensitive/v1/detection","/admin/packerRecord/doChannelVersions/**").excludePathPatterns("/admin/packerStatus")
//                .excludePathPatterns("/file/upload");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
