package com.fastcampus.snsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//특정설정 제외법
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class FastcampusSnsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastcampusSnsProjectApplication.class, args);
    }

}
