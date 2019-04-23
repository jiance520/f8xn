package com.aop;

import java.lang.reflect.Method;

import org.springframework.aop.*;

public class AfterInterceptor implements AfterReturningAdvice{

	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		System.out.println("-----------afterReturning");
		
		System.out.println(" arg0 = "+arg0.toString());
		
		System.out.println(" arg3 = "+arg3.toString());
	}

}
