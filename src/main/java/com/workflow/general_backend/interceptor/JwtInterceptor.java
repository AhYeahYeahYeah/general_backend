package com.workflow.general_backend.interceptor;

import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.mapper.AdminMapper;
import com.workflow.general_backend.utils.JwtUtils;
import com.workflow.general_backend.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    //    @Autowired
//    RedisTemplate redisTemplate;
    @Autowired
    RedisUtils redisUtils;
    @Resource
    AdminMapper adminMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String JWT = request.getHeader("Authorization");
        String url = request.getRequestURI();
        String method = request.getMethod();
        if(method.equals("OPTIONS"))
            return true;
        String required = "";
        System.out.println(method);

        System.out.println(url);
        if (url.equals("/v1/entity/workflow")) {
            required = "1";
            System.out.println("服务编排");
        } else if (url.equals("/v1/entity/customer")) {
            if (method.equals("GET") || method.equals("DELETE"))
                required = "2";
            System.out.println("客户管理");
        } else if (url.equals("/v1/entity/admin")) {
            if (method.equals("GET"))
                required = "3";
            System.out.println("管理员管理");
        } else if (url.equals("/v1/entity/product")) {
            required = "4";
            System.out.println("产品管理");
        } else {
            if (url.equals("/v1/entity/usergroup") || url.equals("/v1/entity/whitelist")
                    || url.equals("/v1/entity/blacklist")) {
                required = "5";
                System.out.println("用户组名单");
            }
        }

        Jws<Claims> claimsJws;
        try {
            // 校验JWT字符串
            claimsJws = JwtUtils.decode(JWT);
            // 取出playload
            Claims claims = claimsJws.getBody();
            // 取出playload中的数据(如果是自己自定义的键值可以通过get()方法取出，假设储存的值可以转换String;如果是playload中的标准字段可以直接通过函数取出)
            // 取出用户账号即account
            String account = (String) claims.get("account");

            // 将redis中根据account取出的token转化为String类型
            String JWTR = (String) redisUtils.get(account);
            // 判断是否和原本存的token相等

            // 还可能继续加异常判断

            if (!JWT.equals(JWTR)) {
                System.out.println("1-false");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return false;
            } else {
                System.out.println("1-true");
            }
            if (required.equals(""))
                return true;

            Admin admin = adminMapper.findAdminByAccount(account);
            String pids = admin.getPermissions();
            pids = pids.replace("[", "").replace("]", "");
            String[] pidList = pids.split(",");
            for (String i : pidList
            ) {
                if (i.equals("0")) {
                    System.out.println("root");
                    return true;
                }
                if (i.equals(required)) {
                    System.out.println("权限适配");
                    return true;
                }
            }
            System.out.println("权限不适配");
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return false;

        } catch (JwtException e) {
            claimsJws = null;
            e.printStackTrace();
        }
        return false;
    }
}

