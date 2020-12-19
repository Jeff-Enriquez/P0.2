package com.BankApp.CustomExceptions;

public class PasswordException extends Exception{
	public PasswordException(){
		super("Password is not correct");
	}
}
