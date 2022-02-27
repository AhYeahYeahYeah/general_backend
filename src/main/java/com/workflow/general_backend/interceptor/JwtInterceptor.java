package com.workflow.general_backend.interceptor;

import com.workflow.general_backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String JWT = request.getHeader("Authorization");

        Jws<Claims> claimsJws;
        try {
            // 校验JWT字符串
            claimsJws = JwtUtils.decode(JWT);
            // 取出playload
            Claims claims=claimsJws.getBody();
            // 取出playload中的数据(如果是自己自定义的键值可以通过get()方法取出，假设储存的值可以转换String;如果是playload中的标准字段可以直接通过函数取出)
            // 取出用户名即account
            String account=(String)claims.get("account");
            // 将redis中根据account取出的token转化为String类型
            String JWTR= (String) redisTemplate.opsForValue().get(account);
            // 判断是否和原本存的token相等

            // 还可能继续加异常判断
            if(!JWT.equals(JWTR))
                System.out.println("false");
            else
                System.out.println("true");
            return JWT.equals(JWTR);

        }catch (JwtException e) {
            claimsJws = null;
            e.printStackTrace();
        }
        return false;
    }
}

