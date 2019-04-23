package com.aop;

import java.lang.reflect.Method;

import org.springframework.aop.*;

public class BeforeInterceptor implements MethodBeforeAdvice{

	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		
		System.out.println(" before interceptor");
		System.out.println("----方法名:"+arg0.getName());
	}

}
