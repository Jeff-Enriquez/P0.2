package com.BankApp.Models;

import java.util.HashSet;

public class Customer extends User {
	HashSet<String> accounts = new HashSet<String>();
	
	Customer(String username, String password) throws Exception{
		super(username, password);
	}
	
}
