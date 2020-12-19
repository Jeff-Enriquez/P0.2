package com.BankApp.Models;

public class Customer extends User {
	private String email;
	private String customerId;
	
	Customer(String username, String email, String password) throws Exception{
		super(username, password);
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
}
