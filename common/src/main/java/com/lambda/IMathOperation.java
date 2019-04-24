package com.lambda;

/*
 * 函数接口指的是只有一个函数的接口，
 * 这样的接口可以隐式转换为Lambda表达式
 * 默认方法和静态方法不会破坏函数式接口的定义
 */
@FunctionalInterface
public interface IMathOperation {
	int operation(int a, int b);
	
	default int test(){;
	
	    return 10 ;
	}
}
