package com.lambda;

/*
 * 
 */
public class Demo2 {

	public static void main(String[] args) {
		// 类型声明
		IMathOperation m1 = (int a,int b) ->  a + b ; // 一次性 new 对象 重写方法
		
		int num1 = m1.operation(12, 5);
		
		// 不用类型声明
		IMathOperation m2 = (a,b) ->  a + b ;
		/* 如果Lambda表达式中的语句块只有一行，
		 * 则可以不用使用return语句
		 * 否则有返回值要return
		 */
		IMathOperation m3 = (a,b) -> {
			 int num = a + b ;
			     num++;
			 return num ;
		};
		System.out.println(m3.operation(12, 1));
	}

}
