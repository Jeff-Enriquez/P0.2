package com.BankApp.Models;

public class User {
	public String username;
	private String password;
	
	User(String username, String password) throws Exception{
		this.username = username;
		this.setPassword(password);
	}
	public String getPassword() {
		return this.password;
	};
	public void setPassword(String password) throws Exception {
		if(password.length() < 2) {
			throw new Exception("User: Password length must be longer than 2.");
		} else {
			this.password = password;
		}
	};
}
