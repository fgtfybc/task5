package com.jnshu.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtilB {
    // 私钥
    private static final String SECRET_KEY = "this is a secret key";

    public static void main(String[] args) {
        // 生成token
        String jwtToken = Jwts.builder()
                // 头部
                .setHeaderParam("typ", "JWT")

                // jwt 标注中的申明
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(new Date().getTime() + 10000L))// 过期时间
                .setSubject("19930311")// jwt面向的客户
                .setIssuer("huan")// jwt的签发者

                // 公共申明和私有申明
                .claim("user_id", "admin")
                .claim("phone", "18251421000")
                .claim("age", 25)
                .claim("sex", "男")

                // 签证
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())

                .compact();

        System.out.println("生成的 jwt token 如下:" + jwtToken);

        // 验证jwt
        Jws<Claims> claimsJws = Jwts.parser()
                // 验证签发者字段iss 必须是 huan
                .require("iss", "huan")
                // 设置私钥
                .setSigningKey(SECRET_KEY.getBytes())
                // 解析jwt字符串
                .parseClaimsJws(jwtToken);

        // 获取头部信息
        JwsHeader header = claimsJws.getHeader();
        // 获取载荷信息
        Claims payload = claimsJws.getBody();

        System.out.println("解析过来的jwt的header如下:" + header.toString());
        System.out.println("解析过来的jwt的payload如下:" + payload.toString());

    }
}
