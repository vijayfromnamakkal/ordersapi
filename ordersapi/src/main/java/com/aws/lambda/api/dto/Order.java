package com.aws.lambda.api.dto;

public class Order {

	public int id;
	public String itemName;
	public int quantity;
	
	
	public Order() {
		
	}
	
	
	public Order(int id, String itemName, int quantity) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.quantity = quantity;
	}
	
	
}
