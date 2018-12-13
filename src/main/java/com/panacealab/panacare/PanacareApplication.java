package com.panacealab.panacare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan
public class PanacareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanacareApplication.class, args);
	}
}
