package com.BankApp;

import java.util.Scanner;

public class Driver {
	private final static Scanner sc = new Scanner(System.in);
	
	public static String getUsername() {
		System.out.print("Enter username: ");
		return sc.nextLine();
	}
	public static String getPassword() {
		System.out.print("Enter password: ");
		return sc.nextLine();
	}
	public static String createPassword() {
		String password1 = "p1";
		String password2 = "p2";
		while(!password1.equals(password2)) {
			System.out.print("Enter password: ");
			password1 = sc.nextLine();
			while(!isValidPassword(password1)) {
				System.out.println("Password must be more than 2 characters");
				System.out.print("Enter password: ");
				password1 = sc.nextLine();
			}
			System.out.print("Confirm pasword: ");
			password2 = sc.nextLine();
			if(!password1.equals(password2)) {
				System.out.println("Passwords do not match. Try again.");
			}
		}
		return password1;
	}
	public static boolean isValidPassword(String password) {
		boolean isValid = true;
		if(password.length() < 2) {
			isValid = false;
		}
		return isValid;
	}
}
