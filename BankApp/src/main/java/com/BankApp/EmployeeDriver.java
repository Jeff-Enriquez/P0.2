package com.BankApp;

import java.util.ArrayList;
import java.util.Scanner;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Doa.AccountApplicationDoa;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Doa.EmployeeDoa;
import com.BankApp.Doa.JointAccountApplicationDoa;
import com.BankApp.Models.Customer;
import com.BankApp.Models.User;

public class EmployeeDriver extends Driver{
	private final static Scanner sc = new Scanner(System.in);
	
	public static void login() {
		EmployeeDoa employeeDoa = new EmployeeDoa();
		boolean isValid = false;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				employeeDoa.selectEmployee(username, password);
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
			actions();
			System.out.print("Enter 'YES' to request another action: ");
			input = sc.nextLine();
		}
	}
	private static void actions() {
		System.out.println("Would you like to: ");
		System.out.println("1) Approve / Deny accounts");
		System.out.println("2) Approve / Deny JOINT accounts");
		System.out.println("3) View customer info");
		System.out.println("4) View all customers");
		String input = sc.nextLine();
		switch(input) {
			case "1":
				processAccount();
				break;
			case "2":
				processJointAccount();
				break;
			case "3":
				viewCustomerInfo();
				break;
			case "4":
				viewAllCustomerAccounts();
				break;
			default:
				System.out.println("Invalid input");
		}
	}
	private static void viewCustomerInfo() {
		System.out.print("Enter the customer username: ");
		String username = sc.nextLine();
		try {
			EmployeeDoa employeeDoa = new EmployeeDoa();
			Customer c = employeeDoa.viewCustomerInfo(username);
			printCustomerInfo(c);
		} catch(UsernameException e) {
			System.out.println("User does not exist");
		}
	}
	private static void viewAllCustomerAccounts() {
		EmployeeDoa employeeDoa = new EmployeeDoa();
		ArrayList<Customer> customers = employeeDoa.viewAllCustomers();
		if(customers == null) {
			System.out.println("There are no customers");
		} else {
			for(Customer c : customers) {
				printCustomerInfo(c);
			}
		}
	}
	private static void printCustomerInfo(Customer c) {
		System.out.println("-----CUSTOMER-----");
		System.out.println("\tUsername: " + c.username);
		System.out.println("\tPassword: " + c.getPassword());
		System.out.println("accounts: ");
		if(c.getAccounts().size() == 0) {
			System.out.println("This user does not have any accounts");
		} else {				
			c.getAccounts().forEach((accNum, balance) -> {
				System.out.println("\tAccount Number: " + accNum + " Balance: " + balance);
			});
		}
	}
	
	private static void processJointAccount() {
		String input;
		JointAccountApplicationDoa jointAccAppDoa = new JointAccountApplicationDoa();
		System.out.println("There are currently " + jointAccAppDoa.applicationCount() + " pending.");
		String username = jointAccAppDoa.getApplication();
		if(username != null) {			
			System.out.println("Would you like to 1) approve or 2) deny");
			System.out.println("Customer: " + username + " would like a joint account");
			System.out.print("Enter response: ");
			input = sc.nextLine();
			AdminDoa adminDoa = new AdminDoa();
			if(input.equals("1")) {
				adminDoa.approveJointAccountApplication(username);
			} else if(input.equals("2")) {
				adminDoa.denyJointAccountApplication(username);
			} else {
				System.out.println("Invalid input");
			}
		}
	}
	
	private static void processAccount() {
		String input;
		AccountApplicationDoa accAppDoa = new AccountApplicationDoa();
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
