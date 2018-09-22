package com.shalou.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//主要入口和方法
@SpringBootApplication
public class DemoApplication {

    //跨域处理
    @Configuration
    public class WebConfig extends WebMvcConfigurationSupport {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
        }
    }

    //主方法
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
