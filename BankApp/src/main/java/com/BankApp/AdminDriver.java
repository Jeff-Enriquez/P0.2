package com.BankApp;

import java.util.ArrayList;
import java.util.Scanner;

import com.BankApp.CustomExceptions.*;
import com.BankApp.Doa.AccountApplicationDoa;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Doa.EmployeeDoa;
import com.BankApp.Models.Customer;
import com.BankApp.Models.User;

public class AdminDriver extends Driver{
	private final static Scanner sc = new Scanner(System.in);
	
	public static void login() {
		AdminDoa adminDoa = new AdminDoa();
		boolean isValid = false;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				adminDoa.selectAdmin(username, password);
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
		System.out.println("2) Withdraw from an account");
		System.out.println("3) Deposit to an account");
		System.out.println("4) Transfer between two accounts");
		System.out.println("5) Cancel an account");
		System.out.println("6) Remove customer");
		System.out.println("7) View customer info");
		System.out.println("8) View all customers");
		String input = sc.nextLine();
		switch(input) {
			case "1":
				processAccount();
				break;
			case "2":
				withdraw();
				break;
			case "3":
				deposit();
				break;
			case "4":
				transfer();
				break;
			case "5":
				cancelAccount();
				break;
			case "6":
				removeCustomer();
				break;
			case "7":
				viewCustomerInfo();
				break;
			case "8":
				viewAllCustomerAccounts();
				break;
			default:
				System.out.println("Invalid input");
		}
	}
	private static void cancelAccount() {
		System.out.print("Enter the account you would like to cancel: ");
		int account = sc.nextInt();
		sc.nextLine();
		AdminDoa adminDoa = new AdminDoa();
		try {
			adminDoa.cancelAccount(account);
		} catch (AccountException e) {
			System.out.println("The account does not exist");
		}
	}
	private static void transfer() {
		System.out.print("Enter the account you would like to transfer FROM: ");
		int account1 = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the account you would like to transfer TO: ");
		int account2 = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the cash amount: ");
		Double cash = sc.nextDouble();
		sc.nextLine();
		CustomerDoa customerDoa = new CustomerDoa();
		try {			
			Double[] balance = customerDoa.transfer(account1, account2, cash);
			System.out.println(cash + " has been transferred from " + account1 + " to account " + account2);
			System.out.println("The new balance for " + account1 + " is: " + balance[0]);
			System.out.println("The new balance for " + account2 + " is: " + balance[1]);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
		} catch (OverdrawException e) {
			System.out.println(account1 + " does not have enough funds to make the transfer.");
		}
	}
	
	private static void deposit() {
		System.out.print("Enter the account you would like to deposit to: ");
		int account = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the cash amount: ");
		Double cash = sc.nextDouble();
		sc.nextLine();
		CustomerDoa customerDoa = new CustomerDoa();
		try {			
			Double balance = customerDoa.deposit(account, cash);
			System.out.println(cash + " has been deposited to account: " + account);
			System.out.println("The new balance is: " + balance);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
		}
	}
	
	private static void withdraw() {
		System.out.print("Enter the account you would like to withdraw from: ");
		int account = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the cash amount: ");
		Double cash = sc.nextDouble();
		sc.nextLine();
		CustomerDoa customerDoa = new CustomerDoa();
		try {			
			Double balance = customerDoa.withdraw(account, cash);
			System.out.println(cash + " has been withdrawn from account: " + account);
			System.out.println("The new balance is: " + balance);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
		} catch (OverdrawException e) {
			System.out.println("The account does not have enough funds to make the withdrawal");
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
		}
	}
	
	private static void removeCustomer() {
		String input;
		System.out.print("Enter the customer username you would like to remove: ");
		input = sc.nextLine();
		AdminDoa adminDoa = new AdminDoa();
		try {			
			adminDoa.deleteCustomer(input);
			System.out.println("Customer: " + input + " has been removed.");
		} catch (UsernameException e) {
			System.out.println("The username does not exist.");
		}
	}
	
	private static void processAccount() {
		Scanner sc = new Scanner(System.in);
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
}
