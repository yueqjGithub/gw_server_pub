package com.avalon.website.cat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @description:
 * @author: xb.wang
 * @create: 2022-04-13 11:20
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.avalon"})
@MapperScan(basePackages = {"com.avalon.website.cat.mapper"})
public class CatApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatApplication.class,args);
    }
}
