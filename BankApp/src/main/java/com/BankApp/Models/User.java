package com.BankApp.Models;

public class User {
	public String username;
	private String password;
	
	User(String username, String password){
		this.username = username;
		this.setPassword(password);
	}
	public String getPassword() {
		return this.password;
	};
	public void setPassword(String password){
		this.password = password;
	};
	@Override
	public String toString() {
		return "User: " + username;
	}
}
