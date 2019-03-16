package com.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class TokenInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
			System.out.println("----进view 前");
	
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
			System.out.println("----出Controller");
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		if(arg2 instanceof HandlerMethod){ //拦截的是不是请求方法，如果是才进@Controller
			HandlerMethod hm = (HandlerMethod)arg2;
			Method md = hm.getMethod() ; // 取方法
			Token tk = (Token)(md.getAnnotation(Token.class));//通过方法取注解
			if(tk != null){ // 要有注解
				boolean check = tk.remove() ; // 取注解属性的值
				if(check){ 
					System.out.println("----要进行重复提交判断");
					//取 session
					HttpSession session = request.getSession();
					// 判断 
					boolean ck = ifRepeat(request, session);
					//清空session: 无论对错都要清空 
					session.removeAttribute("token");
					//== TRUE 要做错误处理
					if(ck){
						System.out.println("-----不能重复提交！！！");
						response.sendRedirect("repeat.jsp");
						return false ; // 中止 当前访问的路径
					}
				}
			}
		}
		System.out.println("----进Controller 前");
		return true;
	}

	// 判断是否重复提交：== TRUE 要做错误处理
	private boolean ifRepeat(HttpServletRequest request,HttpSession session){
		boolean check = true;
		// 得到 页面传的值 
		String pages = request.getParameter("token"); 
		// 得到 session中的值
		Object sobj = session.getAttribute("token");
		System.out.println("pages:"+pages);
		System.out.println("sobj:"+sobj);
		
		if(pages !=null && sobj !=null && pages.equals(sobj)){
			check = false ; //不是重复提交
		}
		
		return check ;
	}
}
