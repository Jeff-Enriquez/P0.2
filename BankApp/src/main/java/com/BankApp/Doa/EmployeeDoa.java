package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.BankApp.Models.Customer;
import com.BankApp.Models.Employee;
import com.BankApp.Util.ConnectionFactory;
import com.BankApp.CustomExceptions.*;

public class EmployeeDoa {
	
	public Employee selectEmployee(String username, String password) throws PasswordMatchException, UsernameException{
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select password from employee where "
				+ "username = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				throw new UsernameException();
			}
			String confirmPassword = rs.getString("password");
			if(!password.equals(confirmPassword)) {				
				throw new PasswordMatchException();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return new Employee(username, password);
	}
	
	public Customer viewCustomerInfo(String username) throws UsernameException {
		Customer c = getCustomer(username);
		HashMap<Integer, Double> accounts = getAccounts(username);
		c.setAccounts(accounts);
		return c;
	}
	public ArrayList<Customer> viewAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select * from customer;";
		try {			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				Customer c = new Customer(username, password);
				c.setAccounts(getAccounts(username));
				customers.add(c);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	private static HashMap<Integer, Double> getAccounts(String username) {
		HashMap<Integer, Double> accounts = new HashMap<Integer, Double>();
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select * from account where customer = ?;";
		try {			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				accounts.put(rs.getInt("number"), rs.getDouble("balance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	private static Customer getCustomer(String username) throws UsernameException {
		Customer c = null;
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select * from customer where username = ?;";
		try {			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				throw new UsernameException();
			}
			String name = rs.getString("username");
			String password = rs.getString("password");
			c = new Customer(name, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}
