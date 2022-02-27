package com.workflow.general_backend.config;
import com.workflow.general_backend.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器,拦截器放行的前提是，放行路径对应的controller得写出来。
        InterceptorRegistration registration = registry.addInterceptor(new JwtInterceptor());
//        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/c1",
                "/v1/auth/clogin",       //用户登录
                "/v1/auth/alogin",       //管理员登录
                                         //用户注册
                                         //管理员注册
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );

    }
}
