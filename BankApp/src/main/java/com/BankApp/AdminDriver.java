package com.BankApp;

import java.util.Scanner;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Models.Admin;

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
		
	}
}
