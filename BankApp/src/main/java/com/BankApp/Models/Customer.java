package com.BankApp.Models;

import java.util.HashSet;

public class Customer extends User {
	HashSet<String> accounts = new HashSet<String>();
	
	public Customer(String username, String password) {
		super(username, password);
	}
	
}
