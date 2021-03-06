package com.BankApp;

import java.util.Scanner;

public class Main {
	final static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		String continueApplication = "YES";
		System.out.println("Welcome to Bank App");
		while(continueApplication.equals("YES")) {			
			System.out.println("Would you like to:");
			System.out.println("1) Create Account");
			System.out.println("2) Customer Login");
			System.out.println("3) Employee login");
			System.out.println("4) Admin login");
			String input = sc.nextLine();
			switch(input) {
			case "1":
				CustomerDriver.create();
				break;
			case "2":
				CustomerDriver.login();
				break;
			case "3":
				EmployeeDriver.login();
				break;
			case "4":
				AdminDriver.login();
				break;
			default:
				System.out.println("Your input was incorrect");
			}
			
			System.out.println("Enter 'YES' if you would like to go back to the welcome screen");
			continueApplication = sc.nextLine();
		}
	}
}
