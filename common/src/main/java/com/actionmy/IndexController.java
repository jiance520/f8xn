package com.actionmy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Controller
public class IndexController implements ServletContextAware {
    private ServletContext application;
//    @Autowired
//    private IManagerService imanagerService ;
    @RequestMapping("/loginAction")
    public String loginAction(){
        System.out.println("执行loginAction");
        return "index";
    }
//    public ModelAndView loginAction(ModelAndView mav){
//        mav.setViewName("index");
//        return mav;
//    }
    @Override
    public void setServletContext(ServletContext servletContext) {
        application = servletContext;
    }

//登陆验证码
    @ResponseBody
    @RequestMapping(value = "CheckCodeAction",produces = "application/json;chart=UTF-8")
    public String checkCodeAction( String value, HttpSession session){
        Object  vobj = session.getAttribute("rand");
        if(value.equalsIgnoreCase((String)vobj)){
            System.out.println("验证码正确");
            return "true";
        }
        else{
            System.out.println("验证码错误");
            return "false";
        }
    }
    @ResponseBody
    @RequestMapping("ImageAction")
    public void imageAction(String value, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
        String StrFont=this.randstr();
        //对session赋值
        HttpSession ses=request.getSession();
        ses.setAttribute("rand",StrFont);
        System.out.println("-----rand:"+StrFont);
        String fontName="宋体";
        int fontSize=30;
        int width=105;
        int height=40;
        Font font=new Font(fontName,Font.BOLD,fontSize);
        BufferedImage StrImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d=(Graphics2D)StrImage.getGraphics();
        //设置背景色
        g2d.setBackground(getRandColor(100,200));
        //填充背景
        g2d.clearRect(0,0,width,height);
        //设置字体色
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        /*
                      写入随机字符串
        StrFont - 要呈现的 String
        x - 呈现 String 位置的 x 坐标
        y - 呈现 String 位置的 y 坐标
        */
        g2d.drawString(StrFont,1,30);
        //释放此图形的上下文以及它使用的所有系统资源
        g2d.dispose();

        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setDateHeader("expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream os = response.getOutputStream();
        //写入输出流
        ImageIO.write(StrImage,"jpg",os);
        //刷新输出流
        os.flush();
        //关闭输出流
        os.close();
    }
    //取得随机字符串
    protected String randstr(){
        String str="";
        String charstr="23456789ABDEFIJLQRTYabdefghijqrty";
        char[] arrchar=charstr.toCharArray();
        int length=arrchar.length;
        length=length-1;
        Random rd=new Random();
        char ct;
        int it;
        for(int i=0;i<6;i++)
        {
            it=rd.nextInt(length);
            ct=arrchar[it];
            str=str+String.valueOf(ct);
        }
        return str;
    }
    //获得随机颜色
    protected Color getRandColor(int fc, int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
    public static void main(String[] args) {
    }
}
