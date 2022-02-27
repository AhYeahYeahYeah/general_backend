package com.workflow.general_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {
    // token时效：24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String APP_SECRET = "szxcmljhbcmCSYszxcmljhbcmCSYszxcmljhbcmCSYszxcmljhbcmCSY";

    /**
     * 根据用户id和昵称生成token
     * //@param id  用户id
     * @param account 用户名
     * @return JWT规则生成的token
     */
//    public static String getJwtToken(String id, String name){
    public static String getJwtToken(String account){
        String JwtToken = Jwts.builder()
//                .setSubject("User")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
//                .claim("id", id)
                .claim("account", account)
                // 传入Key对象
                .signWith(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static Jws<Claims> decode(String jwtToken) {
        // 传入Key对象
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken);
        return claimsJws;
    }
}
