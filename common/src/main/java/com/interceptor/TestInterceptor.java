package com.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TestInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {/*handler接收action方法*/
        System.out.println("----进入controller 前");
        HttpSession session = request.getSession();
        String sessiontoken = (String)request.getSession().getAttribute("token");
        String requesttoken = request.getParameter("token");
        System.out.println("-----sessiontoken:"+sessiontoken);
        System.out.println("-----requesttoken:"+requesttoken);
        if(requesttoken!=null&&sessiontoken!=null&&requesttoken.equals(sessiontoken)){
            System.out.println("-----不是重复提交！！！");
            session.removeAttribute("token");//清空session: 无论对错都要清空
            return true;
        }
        else {
            System.out.println("-----不能重复提交！！！");
            session.removeAttribute("token");//清空session: 无论对错都要清空
            response.sendRedirect("deng_web/repeat.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("----出controller");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("----进入view 前");
    }
}
