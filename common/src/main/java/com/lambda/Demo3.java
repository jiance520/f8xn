package com.lambda;

import java.util.ArrayList;
import java.util.Comparator;

public class Demo3 {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList();
		
		for(int i = 0;i<5;i++){
			list.add(i);
		}
        // lambda 实现  Comparator 接口
		
		Comparator<Integer> c1 = new Comparator<Integer>() {
			
			public int compare(Integer o1, Integer o2){
				
				return o1.intValue() - o2.intValue() ;
			}
		};
		
		Comparator<Integer> c2 = (a, b) -> a - b;
		
		Comparator<Integer> c3 = (a, b) -> {
			return a - b ;
		};
		
		list.sort((a,b) ->{	
			return b - a;
			//return a - b ;
		});
		
		System.out.println(list);
	}

}
