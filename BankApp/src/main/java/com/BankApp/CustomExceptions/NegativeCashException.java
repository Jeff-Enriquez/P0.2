package com.BankApp.CustomExceptions;

public class NegativeCashException extends Exception{
	public NegativeCashException() {
		super("The entered cash value cannot be negative");
	}
}
