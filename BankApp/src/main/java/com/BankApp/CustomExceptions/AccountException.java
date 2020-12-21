package com.BankApp.CustomExceptions;

public class AccountException extends Exception{
	public AccountException(){
		super("Account does not exist");
	}
}
