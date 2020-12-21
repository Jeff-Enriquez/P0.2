package com.BankApp.CustomExceptions;

public class OverdrawException extends Exception{
	public OverdrawException(){
		super("The account does not have sufficient funds to make withdrawal");
	}
}
