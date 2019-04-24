package com.lambda;

public class Demo4 {

	public static int num2 = 10 ;
	
	public static void main(String[] args) {
		/*
		 * 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。
		 * ambda 表达式可以引用外层局部变量，
		 * 但是不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
		 * 最好把引用的外层局部变量标记为 final 
		 */
		int num = 10 ;
		final int num3 = 40 ;
		IGreetingService  g1 = (e) -> {
			//int num = 20 ;
			//System.out.println(e+"---"+num++);
			System.out.println(e+"---"+num3);
		} ;

		g1.sayMessage("hello world");
	}

}
