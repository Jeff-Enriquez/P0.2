package com.BankApp.Models;

public class Account {
	private String customer;
	private String accountId;
	private Double balance;
	
	Account(String customer, String accountId){
		this.customer = customer;
		this.accountId = accountId;
		this.balance = 0.0;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public String getCustomer() {
		return customer;
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
