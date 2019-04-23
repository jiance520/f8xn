package com.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.*;

public class RoundInterceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {

        System.out.println("-------------环绕增强");
		System.out.println("-----执行前");
        Method mm = arg0.getMethod();
		System.out.println("方法名:"+mm.getName());

        Object obj = arg0.proceed();//执行方法
         
        System.out.println("-----执行后");
        System.out.println(" 返回值:"+obj);
		return obj;
	}
}
