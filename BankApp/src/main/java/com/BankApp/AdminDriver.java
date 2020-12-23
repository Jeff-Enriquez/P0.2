package com.BankApp;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.BankApp.CustomExceptions.*;
import com.BankApp.Doa.AccountApplicationDoa;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Doa.EmployeeDoa;
import com.BankApp.Doa.JointAccountApplicationDoa;
import com.BankApp.Models.Admin;
import com.BankApp.Models.Customer;
import com.BankApp.Models.User;

public class AdminDriver extends Driver{
	private final static Scanner sc = new Scanner(System.in);
	private static Logger logger = LogManager.getLogger(AdminDriver.class);
	private static Admin currentUser = null;
	public static void login() {
		AdminDoa adminDoa = new AdminDoa();
		boolean isValid = false;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				adminDoa.selectAdmin(username, password);
				currentUser = new Admin(username, password);
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
		System.out.println("3) Withdraw from an account");
		System.out.println("4) Deposit to an account");
		System.out.println("5) Transfer between two accounts");
		System.out.println("6) Cancel an account");
		System.out.println("7) Remove customer");
		System.out.println("8) View customer info");
		System.out.println("9) View all customers");
		String input = sc.nextLine();
		switch(input) {
			case "1":
				processAccount();
				break;
			case "2":
				processJointAccount();
				break;
			case "3":
				withdraw();
				break;
			case "4":
				deposit();
				break;
			case "5":
				transfer();
				break;
			case "6":
				cancelAccount();
				break;
			case "7":
				removeCustomer();
				break;
			case "8":
				viewCustomerInfo();
				break;
			case "9":
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
			logger.info("User: " + currentUser.username + " transferred " + cash + " from customer account: " + account1 + " to customer account: " + account2);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
			logger.warn("Admin User: " + currentUser.username + " tried to transfer with an account that does not exist.");
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
			logger.warn("Admin User: " + currentUser.username + " tried to deposit with a negative number");
		} catch (OverdrawException e) {
			System.out.println(account1 + " does not have enough funds to make the transfer.");
			logger.warn("Admin User: " + currentUser.username + " tried to transfer but account: " + account1 + " does not have enough funds to do so.");
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
			logger.info("Admin User: " + currentUser.username + " deposited " + cash + " to customer account: " + account);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
			logger.warn("Admin User: " + currentUser.username + " tried to deposit to an account that does not exist. To account number: " + account);
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
			logger.warn("Admin User: " + currentUser.username + " tried to deposit with a negative number");
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
			logger.info("Admin User: " + currentUser.username + " withdrew " + cash + " from customer account: " + account);
		} catch (AccountException e) {
			System.out.println("The account does not exist.");
			logger.warn("Admin User: " + currentUser.username + " tried to withdraw from an account that does not exist. From account number: " + account);
		} catch (OverdrawException e) {
			System.out.println("The account does not have enough funds to make the withdrawal");
			logger.warn("Admin User: " + currentUser.username + " tried to withdraw from " + cash + " from account: " + account + ". But there were not enough funds in the account to do so.");
		} catch (NegativeCashException e) {
			System.out.println("The withdrawal value cannot be negative");
			logger.warn("Admin User: " + currentUser.username + " tried to withdraw with a negative number");
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
