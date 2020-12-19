package com.BankApp;

import java.util.Scanner;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Doa.EmployeeDoa;
import com.BankApp.Models.Employee;

public class EmployeeDriver extends Driver{
	Scanner sc = new Scanner(System.in);
	
	public static void login() {
		EmployeeDoa employeeDoa = new EmployeeDoa();
		boolean isValid = false;
		Employee e = null;
		while(!isValid) {
			isValid = true;
			String username = getUsername();
			String password = getPassword();
			try {
				e = employeeDoa.selectEmployee(username, password);
			} catch(PasswordMatchException exception) {
				System.out.println("Password was incorrect, please input information again.");
				isValid = false;
			} catch(UsernameException exception) {
				System.out.println("Username does not exist, please input information again.");
				isValid = false;
			}
		}
	}
}
