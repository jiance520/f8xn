package com.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * @version 时间：2018年6月11日 上午10:08:55
 *后置切面
 */
public class AfterBean implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,Object arg3) throws Throwable {
		System.out.println("arg0"+arg0);
		System.out.println("arg1"+arg1);
		System.out.println("arg2[0]"+arg2[0]);
		System.out.println("arg3"+arg3);
		System.out.println("记录日记");
	}
}
