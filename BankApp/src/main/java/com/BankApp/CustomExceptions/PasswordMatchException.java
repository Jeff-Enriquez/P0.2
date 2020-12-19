package com.BankApp.CustomExceptions;

public class PasswordMatchException extends Exception{
	public PasswordMatchException(){
		super("The password is not correct");
	}
}
