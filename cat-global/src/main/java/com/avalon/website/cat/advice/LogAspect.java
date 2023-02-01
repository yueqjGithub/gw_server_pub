package com.avalon.website.cat.advice;

import com.alibaba.fastjson.JSON;
import com.avalon.website.cat.constant.CommonConstants;
import com.avalon.website.cat.context.ZzContextHandler;
import com.avalon.website.cat.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 *spring自动切换JDK动态代理和CGLIB
 */
@Component
@Aspect
@ComponentScan
@EnableAspectJAutoProxy
@Slf4j
public class LogAspect {
    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;

    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 在方法执行前进行切面
     */
    @Pointcut("execution(* com.avalon.website..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping)||@annotation(org.springframework.web.bind.annotation.PutMapping)||@annotation(org.springframework.web.bind.annotation.DeleteMapping)||@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        String uri = request.getRequestURI();
        StringBuilder sb = new StringBuilder();
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        Object query = request.getAttribute("params");
        if (StringUtils.isEmpty(query)) {
            String queryString = request.getQueryString();
            Object[] args = point.getArgs();
            if (StringUtils.isEmpty(queryString)) {
                if (args.length > 0) {
                    query = args[0] == null ? null : JSON.toJSONString(args[0].toString());
                }
            } else {
                query = queryString;
            }
            request.setAttribute("param", query);
        }

        sb.append("\t" + request.getRequestURI())
                .append("\t@RequestTime[" + (end - start) + "ms]")
                .append("\t@IP[" + IpUtils.ipAddr(request) + "]")
                .append("\t@Method: " + request.getMethod())
                .append("\t@Operator: " + ZzContextHandler.get(CommonConstants.IAMConstants.USER_NAME))
                .append("\t@Params:" + JSON.toJSONString(query))
                .append("\t@Response: " + response.getStatus())
                .append(JSON.toJSONString(result));
        if (Objects.equals(uri, "/packer/admin/packerStatus")) {
            return point.proceed();
        } else {
            log.info(sb.toString());
        }
        return result;
    }
}


