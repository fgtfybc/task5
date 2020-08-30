package com.jnshu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Token_util {
    //指定加密算法为DESede
    private static String ALGORITHM = "DESede";
    //指定密钥存放文件
//    private static String KEYFile = "KeyFile";
    //token的过期时间和私钥
    private static final long EXPIRE_TIME=15*60*1000;
    private static final String TOKEN_SECRET="123456wq";
    private static final Logger log = LogManager.getLogger(Token_util.class);
    private static final SecureRandom sr = new SecureRandom();
    @Autowired
    DESUtilsb desUtil;

    //生成token
    public  String sign(int id){
        try {
            //过期时间
            Date expire_date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //当前时间
            SimpleDateFormat formate = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
            String current_date = formate.format(new Date(System.currentTimeMillis()));
//            Date current_date = new Date(System.currentTimeMillis());
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            /*对id和登录时间进行des加密
             * date转string*/
//            byte[] result = desUtil.encrypt(String.valueOf(id).getBytes());
//            byte[] result1 = desUtil.encrypt(current_date.getBytes());
            return JWT.create()
                    .withHeader(header)
//                    .withClaim("id", new String(result))
//                    .withClaim("login_date", new String(result1))
                    .withExpiresAt(expire_date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //检验token
    public  boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //获取token中的id
    public int get_token_id(String token){
        String id = token.substring(0,token.indexOf("."));
        return Integer.parseInt(id);
    }

    //测试
    public static void main(String[] args) throws Exception{
        Token_util token_util = new Token_util();
        String s = "112/adafaf";
        log.error(s.indexOf("/"));
        log.error(s.substring(0,s.indexOf("/")));
        log.error(Integer.parseInt(s.substring(0,s.indexOf("/"))));
        SimpleDateFormat formate = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        String time_in_format = formate.format(new Date(System.currentTimeMillis()));
        log.error(time_in_format);

        SecureRandom random1 = SecureRandom.getInstance("SHA1PRNG");
        log.error(random1+"    ;    "+random1.nextInt());
        DESUtilsb desUtil = new DESUtilsb();
        int source = 12;
//        String crptograph = token_util.encrypt(String.valueOf(source));
//
//        log.error("原信息 : "+12);
//        log.error("加密 : "+token_util.encrypt(String.valueOf(12)));
//        String target = token_util.decrypt(crptograph);
//        log.error("解密 : "+target);

        //测试token
        String token = token_util.sign(4);
        DecodedJWT jwt = JWT.decode(token);
        byte[] result_t = jwt.getClaim("id").asString().getBytes();

//        byte[] result1 = desUtil.encrypt("4".getBytes());
//        log.error(result1);
//        String id_de =new String( desUtil.decrypt(result_t));
//        String id_de1 =new String( desUtil.decrypt(result1));
//        log.error(id_de);
//        log.error(id_de1);



    }
}
