package com.BankApp;

import java.util.Scanner;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Doa.AccountApplicationDoa;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Models.Admin;
import com.BankApp.Models.User;

public class AdminDriver extends Driver{
	private final static Scanner sc = new Scanner(System.in);
	
	public static void login() {
		AdminDoa adminDoa = new AdminDoa();
		boolean isValid = false;
		Admin a = null;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				a = adminDoa.selectAdmin(username, password);
			} catch(PasswordMatchException exception) {
				System.out.println("Password was incorrect, please input information again.");
				isValid = false;
			} catch(UsernameException exception) {
				System.out.println("Username does not exist, please input information again.");
				isValid = false;
			}
		}
		String input = "YES";
		while(input.equals("YES")) {			
			adminActions();
			System.out.print("Enter 'YES' to request another action: ");
		}
	}
	
	private static void adminActions() {
		System.out.println("Would you like to: ");
		System.out.println("1) Approve / Deny accounts");
		System.out.println("2) Withdraw from an account");
		System.out.println("3) Deposit to an account");
		System.out.println("4) Transfer between two accounts");
		System.out.println("5) Cancel an account");
		String input = sc.nextLine();
		switch(input) {
			case "1":
				processAccount();
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			default:
				System.out.println("Invalid input");
		}
	}
	
	private static void processAccount() {
		Scanner sc = new Scanner(System.in);
		String input;
		AccountApplicationDoa accAppDoa = new AccountApplicationDoa();
		CustomerDoa customerDoa = new CustomerDoa();
		System.out.println("There are currently " + accAppDoa.applicationCount() + " pending.");
		User user = accAppDoa.getApplication();
		if(user != null) {			
			System.out.println("Would you like to 1) approve or 2) deny");
			System.out.println("Application: username - " + user.username + ", account type - " + user.getAccountType());
			System.out.print("Enter response: ");
			input = sc.nextLine();
			if(input.equals("1")) {
				approveApplication(user);
			} else if(input.equals("2")) {
				AdminDoa adminDoa = new AdminDoa();
				adminDoa.denyApplication(user);
			} else {
				System.out.println("Invalid input");
			}
		}
	}
	
	private static void approveApplication(User user) {
		if(user.getAccountType().equals("customer")) {
			AdminDoa adminDoa = new AdminDoa();
			adminDoa.approveCustomerApplication(user);
		}
	}
}
