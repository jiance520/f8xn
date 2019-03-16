package com.actionservlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ImageServlet extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public ImageServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("    This is ");
        out.print(this.getClass());
        out.println(", using the POST method");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
        */
        String StrFont=this.randstr();
        //对session赋值
        HttpSession ses=request.getSession();
        ses.setAttribute("rand",StrFont);

        String fontName="宋体";
        int fontSize=32;
        int width=120;
        int height=45;
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
        g2d.drawString(StrFont,5,35);
        //释放此图形的上下文以及它使用的所有系统资源
        g2d.dispose();

        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setDateHeader("expires", 0);
        response.setContentType("image/jpeg");
        System.out.println("StrImage"+StrImage);
        ServletOutputStream os = response.getOutputStream();
        //写入输出流
        ImageIO.write(StrImage,"jpg",os);
        //刷新输出流
        os.flush();
        //关闭输出流
        os.close();

        //System.out.println("------code------------"+StrFont);
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
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
    protected Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}

