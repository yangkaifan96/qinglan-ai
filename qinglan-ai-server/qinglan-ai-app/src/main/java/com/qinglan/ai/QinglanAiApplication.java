package com.qinglan.ai;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 应用程序入口类
 *
 * @author Kaifan Yang
 */
@SpringBootApplication
@MapperScan(
        basePackages = "com.qinglan.ai",
        markerInterface = BaseMapper.class
)
public class QinglanAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QinglanAiApplication.class, args);
    }
}
