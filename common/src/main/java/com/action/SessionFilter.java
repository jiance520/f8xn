package com.action;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SessionFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;

        String uri = request.getRequestURI();
        //System.out.println(" uri = "+uri);
        String qs = request.getQueryString();
        //System.out.println(" qs = "+qs);
        String path = uri.substring(uri.lastIndexOf("/") + 1);//获取最后的文件名
        //System.out.println(" path = "+path);
        ArrayList<String> cklist = new ArrayList<String>();//把要过滤的网站加入一个数组
            cklist.add("vip.html");
            cklist.add("friendship.html");
            cklist.add("feature.html");
        if (cklist.contains(path)) {//判断当前访问的网站是否在过滤数据名单中
            System.out.println("网站需要登陆访问，在SessionFilter名单中path=:"+path);
            Object uobj = request.getSession().getAttribute("users");
            if (uobj == null) {
                System.out.println("网站没有登陆，不能访问path=:"+path+"，重定向");
                response.sendRedirect("index.jsp");
            } else {
                System.out.println("网站已登陆，访问path=:"+path);
                arg2.doFilter(arg0, arg1);
            }
        } else {
            System.out.println("网站不需要登陆访问，不在SessionFilter名单中path=:"+path);
            arg2.doFilter(arg0, arg1);
        }

    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
