package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class UserInterceptor {

	public void before(JoinPoint point){
		System.out.println("-----之前");
		
		point.getArgs();//参数
		
		String name = point.getSignature().getName();//方法名
		System.out.println(" name = "+name);
	}
	
	public void after (JoinPoint point,Object result){
		System.out.println("-----之后");
		
		String name = point.getSignature().getName();//方法名
		System.out.println(" name = "+name);
		
		System.out.println(" 返回值:"+result);
	}
	
	public Object round(ProceedingJoinPoint point){
		System.out.println("----环绕前");
		
		Object obj =null;
		
		try {
			obj = point.proceed();
			
			System.out.println("----环绕后");
			System.out.println("ProceedingJoinPoint"+point);
			System.out.println(" 返回值:"+obj);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		   return obj;
	}
}
