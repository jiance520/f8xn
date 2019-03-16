package com.redis.entity;

import java.io.Serializable;

// 实体 
public class Goods implements Serializable{

	private String name;
	private int num ;
	private int price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Goods() {
		
	}
	
	public Goods(String name, int num, int price) {
		this.name = name;
		this.num = num;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Goods{" +
				"name='" + name + '\'' +
				", num=" + num +
				", price=" + price +
				'}';
	}
}
