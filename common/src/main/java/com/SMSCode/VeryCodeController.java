package com.SMSCode;

import com.alibaba.fastjson.JSON;
import com.SMSCode.SMSCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
public class VeryCodeController implements ServletContextAware {
    private ServletContext application;
    //处理发送验证码请求的Servlet：
    @ResponseBody
    @RequestMapping(value = "/code", produces = {"application/json; charset=UTF-8"})
    public String code(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String phoneNumber = req.getParameter("phoneNumber");
        System.out.println("-----手机号码是:"+phoneNumber);
        if(phoneNumber.trim().equals("")||phoneNumber==null){
            System.out.println("-----手机号码为空！");
            return JSON.toJSONString("手机号码为空！");
        }
        if(!Pattern.matches("^1[3|5|8]\\d{9}$",phoneNumber)){
            System.out.println("-----手机格式号码有误！");
            return JSON.toJSONString("手机格式号码有误！");
        }
//        生成一个6位0-9之间的随机字符串
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for(int i = 0;i<6;i++){
            stringBuffer.append(random.nextInt(10));
        }
        resp.setContentType("text/html;charset=utf-8");
        if(!SMSCode.sendCode(phoneNumber,stringBuffer.toString())){
            return JSON.toJSONString("验证码发送失败！");
        }else {
//                将手机号码、验证码、和当前系统时间存储到session中
            req.getSession().setAttribute("code",stringBuffer.toString());
            req.getSession().setAttribute("number",phoneNumber);
            req.getSession().setAttribute("time",System.currentTimeMillis());
            return JSON.toJSONString("验证码发送成功！");
        }
    }
    //最后是处理登录业务的Servlet：
    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json; charset=UTF-8"})
    public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        req.setCharacterEncoding("utf-8");
        String phoneNumber = req.getParameter("phoneNumber");
        String code = req.getParameter("code");
        if(phoneNumber==null||phoneNumber.trim().equals("")||code==null|code.trim().equals("")){
            System.out.println("-----手机号码或验证码为空！");
            return JSON.toJSONString("手机号码或验证码为空！");
        }
        if(!Pattern.matches("^1[3|5|8]\\d{9}$",phoneNumber)){
            System.out.println("-----手机号码有误！");
            return JSON.toJSONString("手机号码有误！");
        }
//        从session中拿出数据，拿出后就清空对应session里的数据
        HttpSession httpSession = req.getSession();
        String code_session = (String)httpSession.getAttribute("code");
        String number = (String)httpSession.getAttribute("number");
        Long time = (Long)httpSession.getAttribute("time");
        if(code_session==null||code_session.trim().equals("")){
            System.out.println("-----验证码为空!");
            return JSON.toJSONString("验证码为空!");
        }
//        验证码有效期为10分钟
        if((System.currentTimeMillis()-time)/1000/60>=10){
            System.out.println("-----验证码已过期！");
            return JSON.toJSONString("验证码已过期！");
        }
//        发送验证码的手机号码和登陆时提交的手机号码必须一致
        if(!number.trim().equalsIgnoreCase(phoneNumber)){
            System.out.println("手机号码不一致！");
            return JSON.toJSONString("手机号码不一致！");
        }
        if(code_session.trim().equalsIgnoreCase(code)){
            System.out.println("登陆成功！");
            resp.addHeader("Content-Type","text/html;charset=GBK");
//            printWriter.println("<script>window.alert('登陆成功!');</script>");
            return JSON.toJSONString("登陆成功!");
        }else {
            System.out.println("验证码不一致！");
            return JSON.toJSONString("验证码不一致！");
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        application = servletContext;
    }
}
