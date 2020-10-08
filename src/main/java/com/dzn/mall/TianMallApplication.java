package com.dzn.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author duzhongnan
 * @date 2020/9/25 23:01
 */
@SpringBootApplication
@MapperScan({"com.dzn.mall.mapper.ums","com.dzn.mall.dao"})
public class TianMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(TianMallApplication.class, args);
    }
}
