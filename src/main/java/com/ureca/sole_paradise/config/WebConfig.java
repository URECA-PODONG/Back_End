package com.ureca.sole_paradise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

     @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")  // 모든 하위 경로 포함
                    .allowedOriginPatterns("http://localhost:5173", "https://sole-paradise.vercel.app")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")  // 모든 헤더 허용
                    .allowCredentials(true);
        }

}
