package com.BankApp.CustomExceptions;

public class UsernameException extends Exception{
	public UsernameException(){
		super("Username does not exist");
	}
}