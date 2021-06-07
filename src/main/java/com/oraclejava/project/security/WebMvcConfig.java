package com.oraclejava.project.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 외부 이미지 경로 불러오기
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

//	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img_view/**")
                .addResourceLocations("file:///C:/data/"); //리눅스 root에서 시작하는 폴더 경로
        
      
	}
}