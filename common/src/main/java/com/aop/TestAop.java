package com.aop;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.impl.Demo;

/**
 * @version 时间
 *
 */
public class TestAop {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Demo d2 = (Demo)ctx.getBean("demo");
		int rs = d2.add(12,5);
//		System.out.println(d2);
//		System.out.println(rs);
	}
}
