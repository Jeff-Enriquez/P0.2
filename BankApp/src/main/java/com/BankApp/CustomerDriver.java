package com.BankApp;

import java.util.Scanner;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Models.Customer;

public class CustomerDriver extends Driver{
	private final static Scanner sc = new Scanner(System.in);
	
	public static void login() {
		CustomerDoa customerDoa = new CustomerDoa();
		boolean isValid = false;
		Customer c = null;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				c = customerDoa.selectCustomer(username, password);
			} catch(PasswordMatchException exception) {
				System.out.println("Password was incorrect, please input information again.");
				isValid = false;
			} catch(UsernameException exception) {
				System.out.println("Username does not exist, please input information again.");
				isValid = false;
			}
		}
	}
	
	public static void create() {
		String username = getUsername();
		String password = createPassword();
		boolean isSubmitted = false;
		while(!isSubmitted) {
			Customer c = new Customer(username, password);
			CustomerDoa customerDoa = new CustomerDoa();
			isSubmitted = customerDoa.submitApplication(c);
			if(!isSubmitted) {
				System.out.println("That username has been taken.");
				System.out.print("Please enter a new username: ");
				username = sc.nextLine();
			}
		}
	}
}
