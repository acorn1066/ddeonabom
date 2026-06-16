package kh.ddeonabom.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kh.ddeonabom.common.interceptor.CheckAdminInterceptor;
import kh.ddeonabom.common.interceptor.NavbarInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private NavbarInterceptor navbarInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(navbarInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**");
        registry.addInterceptor(new CheckAdminInterceptor())
        		.addPathPatterns("/admin/**");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/reviews/");
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}