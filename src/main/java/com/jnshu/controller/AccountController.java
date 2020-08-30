package com.jnshu.controller;

import com.jnshu.pojo.Account;
import com.jnshu.service.AccountService;
import com.jnshu.util.DESUtils;
import com.jnshu.util.MD5Util;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    DESUtils des = new DESUtils();

    MD5Util MD5 = new MD5Util();

    //转到注册页面
    @RequestMapping(value = "toregister")
    public String toregister() {
        return "register";
    }

    //注册账户
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Account account, Model model,HttpServletRequest request) {
        logger.info(account);//输入的账号密码

        String u = account.getUsername();
        String p = account.getPassword();

        logger.info("输入的账户为"+u+"输入的密码为"+p);

        //判断输入的用户名是否为空或者 空字符
        if(u!=null&&u!=""){
            logger.info("用户名不为空");

            if(p!=null&&p!=""){
                logger.info("密码不为空");


            //判断数据库中是否有此用户名
           Account accounta =accountService.selectAccount(u);

            //如果查询出的账户为空（代表用户不存在），才能注册
            if(ObjectUtils.isEmpty(accounta)) {
                logger.info("此用户名不存在，可以注册");

                try {

                    //对传入的密码用MD5进行加密加盐
                    String MD5Password = MD5Util.generate(p);

                    //设置密码为新密码
                    account.setPassword(MD5Password);

                    //插入数据
                    int id = accountService.insert(account);

                    System.out.println("注册成功 请登录");
                    //返回登录页面
                    return "login";
                }catch (Exception e){
                    String error ="注册失败 请再试试";
                    logger.info("注册失败 请重新输入");
                    request.setAttribute("error",error);
                    return "register";
                }
            }else {
                String error = "用户名已存在，请更换用户名注册或直接登录";
                logger.info("用户名已存在，请更换用户名注册或直接登录");
                request.setAttribute("error",error);
                return "register";
            }
        }else {
                String error = "用户密码不能为空！";
                logger.info("用户密码不能为空！");
                request.setAttribute("error",error);
                return "register";
        }
        }else {
            String error = "用户名不能为空，请重新输入用户名";
            logger.info("用户名不能为空，请重新输入用户名");
            request.setAttribute("error",error);
            return "register";
        }
    }


     /*   //判断账号密码是否为空
        if (account.getUsername().length() != 0 && account.getPassword().length() != 0) {
            //判断该账号是否存在
            if (accountService.selectAccount(account.getUsername()) == null) {
                logger.info("加密前密码为：" + account.getPassword());
                //对密码进行加密
                String pswMD5 = MD5Util.generate(account.getPassword());
                logger.info("加密后密码为：" + pswMD5);
                account.setPassword(pswMD5);
                account.setCreateat(1L);
                account.setCreateby("管理员");
                account.setUpdateat(1L);
                account.setUpdateby("管理员");
                accountService.insert(account);
                model.addAttribute("msg", "注册成功");
                return "login";
            } else {
                model.addAttribute("msg", "该账号已存在！");
                return "register";
            }
        } else {
            model.addAttribute("msg", "账号和密码不能为空！");
            return "register";
        }*/


    //转到登录页面
    @RequestMapping(value = "tologin")
    public String tologin() {
        System.out.println("跳转登录");
        return "login";
    }

    //登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login( Account account,Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
            logger.info("输入的表格数据为"+account);

            String u =account.getUsername();
            String p =account.getPassword();

            logger.info("取出账号为"+u+"------"+"取出密码为"+p);

            //判断输入的用户是否为空或者 空字符
        if(u!=null && u!=""){
            logger.info("用户名通过");
            Account account1 = accountService.selectAccount(u);
            logger.info("查询出的内容为"+account1);
            //判断此用户中是否有用户名
            if(!ObjectUtils.isEmpty(account1)){
                logger.info("用户不为空");
                    //验证输入密码有没有被篡改过 没有就返回true
                if(MD5Util.verify(p,account1.getPassword())==true){
                    logger.info("密码正确");
                    //把用户id转化为long类型
                    Long B = account1.getId();

                    //对此用户id进行des加密
                    String desId = des.encryptFromLong(B);
                    System.out.println("对用户id的加密结果为======"+desId);

                    //对当前时间进行des加密
                    String desTime = des.encryptFromLong(System.currentTimeMillis());
                    System.out.println("对当前时间加密的结果为====="+desTime);

                    //对用户id和登录时间一起进行des加密
                    String token = des.encrypt(desId+"|"+account1.getUsername()+"|"+desTime);
                    logger.info("对用户id和登陆时间加密的结果（token）为========" + token);

                    Cookie tokenCookie =new Cookie("token", token);

                    //设置cookie存在的时间
                    tokenCookie.setMaxAge(30*60);

                    httpServletResponse.addCookie(tokenCookie);

                    System.out.println("登录成功，正在进入主页面");
                    return "redirect:/a/student";
                }else {
                    String error = "密码错误，请重新输入";
                    logger.info("密码错误，请重新输入");
                    httpServletRequest.setAttribute("error",error);
                    return "login";
                }
            }else {
                String error ="账户不存在，请重新输入";
                logger.info("账户不存在，请重新输入");
                httpServletRequest.setAttribute("error",error);
                return "login";
            }
        }else {
            String error = "用户名为空，请重新输入";
            logger.info("用户名为空，请重新输入");
            httpServletRequest.setAttribute("error",error);
            return "login";
        }
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request,HttpSession session,HttpServletResponse response){
        //创建一个cookie对象，得到cookie
        Cookie[] cookies =request.getCookies();
        //先取出cookie
        for (Cookie cookie:cookies) {
            //如果存在name为token的cookie,则取出并修改token的时效为0
            if(cookie.getName().equals("token")){
                cookie.setMaxAge(0);
                logger.info("被删除的token是"+cookie.getName());
                response.addCookie(cookie);
                request.setAttribute("error","注销成功，欢迎再次登录！");
                return "redirect:a";
            }
        }
        return "redirect:a";
    }
}
//输入的账号密码 并且判断账号密码是否为空
/*
//----------------------------第一次---------------------------------------------------------------------
    //转到注册页面
    @RequestMapping(value = "toregister",method = RequestMethod.GET)
    public String register1(){
        logger.info("-----转到注册页面----");
        return "register";
    }

    //注册账户
    @RequestMapping(value="register",method = RequestMethod.POST)
    public String register(Account account,Model model){
        logger.info(account);//输入的账号密码

        //判断账号密码是否为空
        if(account.getUsername().length() !=0 && account.getPassword().length() != 0){
            //判断该账号是否存在
            if(accountService.selectAccount(account.getUsername())==null){
                logger.info("加密前密码为"+account.getPassword());
                //对密码进行加密
                String pswMD5 = MD5Util.generate(account.getPassword());
                logger.info("加密后密码为"+pswMD5);
                account.setPassword(pswMD5);
                account.setCreateat(1L);
                account.setCreateby("管理员");
                account.setUpdateat(1L);
                account.setUpdateby("管理员");
                accountService.insert(account);
                model.addAttribute("msg","注册成功");
                return "login";
            }else {
                model.addAttribute("msg","该账号已存在！");
                return "register";
            }
        }else {
            model.addAttribute("msg","账号和密码不能为空");
            return "register";
        }
    }

    //转到登录页面
    @RequestMapping(value = "login")
    public String login(String username, String password, Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        logger.info("加密前的信息"+password);

        //判断用户名和密码是否不为空
        if(username != null && username.length() != 0 && password !=null && password.length()!=0){
            Account account = accountService.selectAccount(username);
            //验证账号是否存在
            if(account != null){
                //验证账户密码是否正确
                if(MD5Util.verify(password,account.getPassword())){
                    logger.info("登录成功");
                    Long id = account.getId();//根据用户名获取id
                    //使用系统当前时间生成唯一token,格式为键值对
                    String token = id+"="+System.currentTimeMillis();
                    //使用DES加密、
                    String tokenDES = DESUtils.getEncryptString(token);
                    logger.info("加密后的token",tokenDES);
                    //保存到cookies中
                    Cookie cookie = new Cookie("token", tokenDES);
                    //设置cookie过期时间 单位为秒
                    cookie.setMaxAge(3600);
                    //设置cookie有效路径
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                    return "redirect:/u/profession";
                }else {
                    model.addAttribute("error","账号或密码错误");//如果账号密码错误则提示该消息
                    return "login";
                }
            }else {
                model.addAttribute("error","该账号不存在");//如果账号不存在则提示该消息
                return "login";
            }
        }else {
            model.addAttribute("error","请先登录");//如果未登录就访问/u/profession则提示该消息
            return "login";
        }
    }


    public String logout(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        //session.removeAttribute("loginname");消耗session
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/home";
            }
        }
        return "redirect:/home";
    }
//------------------------------第一次完结----------------------------------------------------------------------
*/

    /*
    //注册账户
    @RequestMapping(value = "/a/u/register",method = RequestMethod.POST)
    public String register(@RequestParam("username") String username,@RequestParam(value = "account",required = false) Account account, @RequestParam(value = "model",required = false) Model model){
        logger.info("新注册用户信息====="+account);
        List<Account> accounts = accountService.selectByName(username);
        logger.info("查看用户是否存在数据库中==="+accounts);
        if(!CollectionUtils.isEmpty(accounts)){
            logger.info("用户已经存在======");
            return "redirect:/a/u/login";
        }else {
            if(account.getUsername()!=null&&account.getUsername().length()>0
                    &&account.getPassword()!=null
                    &&account.getPassword().length()>0){
                logger.info("注册用户的信息===="+account);
                int row =accountService.insert(account);
                logger.info("插入成功======"+row);
               *//* model.addAttribute("msg","注册成功");*//*
                return "redirect:/student";
            }else {
              *//*  model.addAttribute("msg","注册失败");*//*
                return "redirect:/register";
            }
        }
    }

    //转到登录页面
    @RequestMapping(value = "/a/u/login",method = RequestMethod.GET)
    public String login1() {

        logger.info("-----转到登录页面----");
        return "login";
    }

    //登录
    @RequestMapping(value = "/a/u/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "account",required = false) Account account,@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam(value = "model",required = false) Model model){
       logger.info("Account========"+account);
       //如果能查出来数据 说明数据库哟这条数据
        List<Account>list =accountService.selectByAccount(username,password);
        System.out.println("list============="+list);
        if(!CollectionUtils.isEmpty(list)){
           *//* model.addAttribute("msg","登录成功");*//*
            return "redirect:/u/profession";
        }else {
           *//* model.addAttribute("msg","登录失败");*//*
            return "login";
        }
    }
}*/