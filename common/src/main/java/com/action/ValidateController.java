//package com.action;
//
//
//import com.entity.Manager;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.ServletContextAware;
//
//import javax.servlet.ServletContext;
//
///**
// * @version 时间：2018年7月14日 上午11:41:48
// *
// */
//@Controller
//public class ValidateController implements ServletContextAware{
//	private ServletContext application;
//	@Override
//	public void setServletContext(ServletContext arg0) {
//		this.application = arg0;
//	}
//	@RequestMapping("ValidateLogin")
//	public String validateLogin(@Validated Manager vote_user,BindingResult binder){
//		if(binder.hasErrors()){
//			System.out.println("后台JSR303验证失败，密码必须是数字或字母1-10个");
//			System.out.println(binder.getFieldError().getDefaultMessage());
//			return "redirect:login.jsp";
//		}
//		else{
//			System.out.println("后台JSR303验证成功");
//			return "forward:LoginAction";
//		}
//	}
//}
