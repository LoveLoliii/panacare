package com.panacealab.panacare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
/***
 * exclude = {MultipartAutoConfiguration.class}
 * 防止springboot 自动处理文件表单
 * */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
//@MapperScan

@EnableCaching //redis
public class PanacareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanacareApplication.class, args);
	}
}
