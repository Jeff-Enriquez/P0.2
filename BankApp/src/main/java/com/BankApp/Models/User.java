package com.BankApp.Models;

public class User {
	public String username;
	private String password;
	private String accountType = null;
	
	User(String username, String password){
		this.username = username;
		this.setPassword(password);
	}
	
	public User(String username, String password, String accountType){
		this.username = username;
		this.setPassword(password);
		this.accountType = accountType;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getAccountType() {
		return accountType;
	}
	@Override
	public String toString() {
		return "User: " + username;
	}

}
