package com.ling.other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Administrator
 */
@MapperScan(value = "com.ling.other.mapper")
@SpringBootApplication
@EnableScheduling   // 启动定时任务
//@ComponentScan(basePackages = {},excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
