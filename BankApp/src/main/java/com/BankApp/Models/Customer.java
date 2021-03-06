package com.BankApp.Models;

import java.util.HashMap;

public class Customer extends User {
	HashMap<Integer, Double> accounts = new HashMap<Integer, Double>();
	
	public Customer(String username, String password) {
		super(username, password);
	}
	
	public void setAccounts(HashMap<Integer, Double> accounts) {
		this.accounts = accounts;
	}
	
	public HashMap<Integer, Double> getAccounts() {
		return accounts;
	}
	
	public void updateAccount(Integer account, Double cash){
		this.accounts.put(account, cash);
	}
}
