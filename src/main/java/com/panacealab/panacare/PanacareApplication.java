package com.panacealab.panacare;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
/***
 * exclude = {MultipartAutoConfiguration.class}
 * 防止springboot 自动处理文件表单
 * */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
//@MapperScan
@EnableTransactionManagement //开启事务
@EnableCaching //redis
public class PanacareApplication {
	private static Logger logger = LoggerFactory.getLogger("PanacareApplication");
	public static void main(String[] args) {
		logger.info("Service Starting");
		SpringApplication.run(PanacareApplication.class, args);
		logger.error("Service Start Over ");
	}



	/**
	 * 解决跨域问题
	 */
	/*@Configuration
	public class CorsConfig {
		private CorsConfiguration buildConfig() {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			// 1 设置访问源地址
			corsConfiguration.addAllowedOrigin("*");
			// 2 设置访问源请求头
			corsConfiguration.addAllowedHeader("*");
			// 3 设置访问源请求方法
			corsConfiguration.addAllowedMethod("*");
			corsConfiguration.setAllowCredentials(true);
			return corsConfiguration;
		}

		@Bean
		public CorsFilter corsFilter() {
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			// 4 对接口配置跨域设置
			source.registerCorsConfiguration("/**", buildConfig());
			return new CorsFilter(source);
		}
	}*/

}
