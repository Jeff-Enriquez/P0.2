package com.BankApp.Models;

public class Account {
	private String customerId;
	private Double balance;
	
	Account(String customerId){
		this.customerId = customerId;
		this.balance = 0.0;
	}
	
	public void deposit(Double cash) throws Exception {
		if(cash <= 0) {
			throw new Exception("Account: deposit must be greater than 0.");
		} else {
			this.balance += cash;
		}
	}
	
	public void withdraw(Double cash) throws Exception {
		if(cash <= 0) {
			throw new Exception("Account: withdraw must be greater than 0");
		} else {
			this.balance -= cash;
		}
	}
	
	public Double getBalance() {
		return balance;
	}
}
