package com.jnshu.Interceptor;

import com.jnshu.pojo.Account;
import com.jnshu.service.AccountService;
import com.jnshu.util.DESUtils;
import com.jnshu.util.DESUtilsa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor1 implements HandlerInterceptor {

    @Autowired
    AccountService accountService;

    private static Logger logger = LogManager.getLogger(MyInterceptor1.class);

    DESUtils des = new DESUtils();



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*  *//*----------------------------张豪-----------------------------------------------------------*//*
        // 判断cookie中是否携带token，并进行验证
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            logger.info("Cookie长度为: " + cookies.length);
            logger.info("拦截器获取到的Cookie: " + String.valueOf(cookies));
            logger.info("开始遍历");
            // 遍历
            for (Cookie cookie : cookies) {
                logger.info("当前cookie的值: " + cookie.getValue() + " 名字为:" + cookie.getName());
                // 判断是否有token
                if (cookie.getName().equals("token")) {
                    String tokenDES = cookie.getValue();
                    logger.info("tokenDES: " + tokenDES);

                    String token = DESUtilsa.getDecryptString(tokenDES);//解密
                    logger.info("token的解密value:" + token);

                    // 分割字符串 获取id
                    Long id = Long.valueOf(token.split("=")[0]);
                    logger.info("id为: " + id);
                    //验证token有效性
                    if (accountService.select(new Account(id)) != null) {
                        return true;
                    } else {
                        logger.debug("token验证失败,跳回登陆页面");
                        // httpServletRequest https://blog.csdn.net/gris0509/article/details/6340987
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/tologin");
                        return false;
                    }
                }
            }
        }
        logger.debug("cookies不存在");
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/tologin");
        return false;
    }

    *//*----------------------------张豪-----------------------------------------------------------*//*

         */









        /*-------------------------------------------------------------------------------------------------------*/
        //   请求cookie里的信息,放入cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        //如果cookies的长度等于0,表示没有cookies,返回登录页面,让用户登录一下下,发给用户一个cookie,下次光临的时候用户就有cookie了
        if (cookies.length == 0) {
            logger.info("没有cookie");

            try {
                //如果没有token  就从header里取token
                String token = httpServletRequest.getHeader("token");

                logger.info("从header里取出的token为" + token);

                //用des对token进行解密
                String firstDecrypt = des.decrypt(token);
                logger.info("对token进行第一次解密的结果" + firstDecrypt);

                //把token解密后的结果根据|进行拆分
                String[] mingwen = firstDecrypt.split("//|");
                logger.info("把token解密后的结果根据|进行拆分");

                //token根据 | 拆分出的第一个（用户id）
                String TokenId = mingwen[0];
                logger.info("token中解密出第一个是id" + TokenId);
                long id = des.decryptToLong(TokenId);
                logger.info("把id解密并转换成long类型" + id);

                //long类型转化为int类型
                int AId = (int) id;

                //根据id查询数据库中有无此用户
                Account accountA = accountService.findById(AId);

                //如果用户为空，返回登录界面
                if (accountA == null) {
                    logger.info("没有此token内所含Id的用户,必须去登录");
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }

                //token根据 | 拆分出的第二个（用户名称）  这个不需要验证
                String TokenName = mingwen[1];
                logger.info("token中解密出第二个是名字" + TokenName);

                //token根据 | 拆分出的第三个（用户登陆时间）
                String TokenTime = mingwen[2];
                logger.info("token中解密出第三个是登陆时间" + TokenTime);
                long time = des.decryptToLong(TokenTime);
                logger.info("把时间解密并转换成long类型" + time);

                //把当前时间（long）减去登陆时间（long）  如果>=30分钟   意味着时间已经超过了  返回登录页面
                if (System.currentTimeMillis() - time >= 30 * 60 * 1000) {
                    System.out.println("token过期=====");
                    //返回登录页面
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }
                logger.info("token（id,有效期）验证通过，登录成功");
                return true;

            } catch (Exception e) {
                httpServletResponse.sendRedirect("/tologin");
                return false;
            }

        } else {

            //     用户有cookies
            logger.info("有cookie");
            //遍历一下用户的cookies,放入cookie,看看他的cookie是什么呀

            try {

                String Mytoken = null;

                for (Cookie cookie : cookies) {
                    //如果他的cookie与我发给他的cookie相等,就可以直接返到页面里
                    if (cookie.getName().equals("token")) {
                        Mytoken = cookie.getValue();
                        break;
                    }
                }
                logger.info("从cookie取出的token 为:" + Mytoken);

                //用des对token进行解密
                String MingwenA = des.decrypt(Mytoken);
                logger.info("对token进行第一次解密的结果" + MingwenA);

                //把token解密后的结果根据|进行拆分
                String[] mingwenB = MingwenA.split("\\|");
                logger.info("对token解密后的结果根据|进行拆分后的结果" + mingwenB);

                //token根据 | 拆分出的第一个（用户id）
                String TokenId = mingwenB[0];
                logger.info("token中解密出第一个是id" + TokenId);
                long id = des.decryptToLong(TokenId);
                logger.info("把id解密并转换成long类型" + id);

                //long类型转化为int类型
                int BId = (int) id;

                //根据id查询数据库中有无此用户
                Account accountB = accountService.findById(BId);

                //如果用户为空，返回登录界面
                if (accountB == null) {
                    logger.info("没有此token内所含Id的用户,必须去登录");
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }

                //token根据 | 拆分出的第二个（用户名称）
                String TokenName = mingwenB[1];
                logger.info("token中解密出第二个是名字" + TokenName);

                //token根据 | 拆分出的第三个（用户登陆时间）
                String TokenTime = mingwenB[2];
                logger.info("token中解密出第三个是登陆时间" + TokenTime);
                long time = des.decryptToLong(TokenTime);
                logger.info("把时间解密并转换成long类型" + time);


                //把当前时间（long）减去登陆时间（long）  如果>=30分钟   意味着时间已经超过了  返回登录页面
                if (System.currentTimeMillis() - time >= 30 * 60 * 1000) {
                    System.out.println(time);
                    System.out.println(System.currentTimeMillis());
                    System.out.println("token过期=====");
                    //返回登录页面
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }

                logger.info("token（id,有效期）验证通过，登录成功");
                return true;
            } catch (Exception e) {

                httpServletResponse.sendRedirect("/tologin");
                return false;
            }
            /*-------------------------------------------------------------------------------------------------------*/

            /*}*/








      /*  //   请求cookie里的信息,放入cookies
        Cookie[] cookies = httpServletRequest.getCookies();

        //如果cookies的长度等于0,表示没有cookies,返回登录页面,让用户登录一下下,发给用户一个cookie,下次光临的时候用户就有cookie了
        if (cookies.length == 0) {
            logger.info("没有cookie");

            try {
                //如果没有token  就从header里取token
                String token = httpServletRequest.getHeader("token");

                logger.info("从header里取出的token为" + token);

                //用des对token进行解密
                String firstDecrypt = des.decrypt(token);
                logger.info("对token进行第一次解密的结果" + firstDecrypt);

                //把token解密后的结果根据|进行拆分
                String[] mingwen = firstDecrypt.split("//|");
                logger.info("把token解密后的结果根据|进行拆分");

                //token根据 | 拆分出的第一个（用户id）
                String TokenId = mingwen[0];
                logger.info("token中解密出第一个是id" + TokenId);
                long id = des.decryptToLong(TokenId);
                logger.info("把id解密并转换成long类型" + id);

                //long类型转化为int类型
                int AId = (int) id;

                //根据id查询数据库中有无此用户
                Account accountA = accountService.findById(AId);

                //如果用户为空，返回登录界面
                if (accountA == null) {
                    logger.info("没有此token内所含Id的用户,必须去登录");
                    httpServletResponse.sendRedirect("/login");
                    return false;
                }

                //token根据 | 拆分出的第二个（用户名称）  这个不需要验证
                String TokenName = mingwen[1];
                logger.info("token中解密出第二个是名字" + TokenName);

                //token根据 | 拆分出的第三个（用户登陆时间）
                String TokenTime = mingwen[2];
                logger.info("token中解密出第三个是登陆时间" + TokenTime);
                long time = des.decryptToLong(TokenTime);
                logger.info("把时间解密并转换成long类型" + time);

                //把当前时间（long）减去登陆时间（long）  如果>=30分钟   意味着时间已经超过了  返回登录页面
                if (System.currentTimeMillis() - time >= 30 * 60 * 1000) {
                    System.out.println("token过期=====");
                    //返回登录页面
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }
                logger.info("token（id,有效期）验证通过，登录成功");
                return true;

            } catch (Exception e) {
                httpServletResponse.sendRedirect("/tologin");
                return false;
            }

        } else {

            //     用户有cookies
            logger.info("有cookie");
            //遍历一下用户的cookies,放入cookie,看看他的cookie是什么呀

            try {

                String Mytoken = null;

                for (Cookie cookie : cookies) {
                    //如果他的cookie与我发给他的cookie相等,就可以直接返到页面里
                    if (cookie.getName().equals("token")) {
                        Mytoken = cookie.getValue();
                        break;
                    }
                }
                logger.info("从cookie取出的token 为:" + Mytoken);

                //用des对token进行解密
                String MingwenA = des.decrypt(Mytoken);
                logger.info("对token进行第一次解密的结果" + MingwenA);

                //把token解密后的结果根据|进行拆分
                String[] mingwenB = MingwenA.split("\\|");
                logger.info("对token解密后的结果根据|进行拆分后的结果" + mingwenB);

                //token根据 | 拆分出的第一个（用户id）
                String TokenId = mingwenB[0];
                logger.info("token中解密出第一个是id" + TokenId);
                long id = des.decryptToLong(TokenId);
                logger.info("把id解密并转换成long类型" + id);

                //long类型转化为int类型
                int BId = (int) id;

                //根据id查询数据库中有无此用户
                Account accountB = accountService.findById(BId);

                //如果用户为空，返回登录界面
                if (accountB == null) {
                    logger.info("没有此token内所含Id的用户,必须去登录");
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }

                //token根据 | 拆分出的第二个（用户名称）
                String TokenName = mingwenB[1];
                logger.info("token中解密出第二个是名字" + TokenName);

                //token根据 | 拆分出的第三个（用户登陆时间）
                String TokenTime = mingwenB[2];
                logger.info("token中解密出第三个是登陆时间" + TokenTime);
                long time = des.decryptToLong(TokenTime);
                logger.info("把时间解密并转换成long类型" + time);


                //把当前时间（long）减去登陆时间（long）  如果>=30分钟   意味着时间已经超过了  返回登录页面
                if (System.currentTimeMillis() - time >= 30 * 60 * 1000) {
                    System.out.println("token过期=====");
                    //返回登录页面
                    httpServletResponse.sendRedirect("/tologin");
                    return false;
                }

                logger.info("token（id,有效期）验证通过，登录成功");
                return true;
            } catch (Exception e) {

                httpServletResponse.sendRedirect("/tologin");
                return false;
            }

        }*/
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
