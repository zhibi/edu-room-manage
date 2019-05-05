package edu.room.manage.common.config;

import edu.room.manage.common.config.interceptor.ConsoleInterceptor;
import edu.room.manage.common.config.interceptor.LoginInterceptor;
import edu.room.manage.common.config.interceptor.ParamInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 执笔
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String filePath;

    @Autowired
    private ConsoleInterceptor consoleInterceptor;
    @Autowired
    private LoginInterceptor   loginInterceptor;
    @Autowired
    private ParamInterceptor   paramInterceptor;

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(consoleInterceptor)
                .addPathPatterns("/console/**")
                .excludePathPatterns("/console/login", "/console/logout");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**");

        registry.addInterceptor(paramInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + filePath + "upload//");
    }
}
