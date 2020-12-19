package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import com.BankApp.CustomExceptions.PasswordMatchException;
import com.BankApp.CustomExceptions.UsernameException;
import com.BankApp.Models.Customer;
import com.BankApp.Util.ConnectionFactory;

public class CustomerDoa {
	
	public boolean submitApplication(Customer c) {
		boolean isSubmitted = isNameValid(c.username);
		if(isSubmitted) {			
			Connection conn = ConnectionFactory.getConnection();
			String sql = "INSERT INTO account_application values" 
					+ "(?,?,'customer');";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, c.username);
				ps.setString(2, c.getPassword());
				ps.execute();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				isSubmitted = false;
			}
		}
		return isSubmitted;
	}
	
	private boolean isNameValid(String username) {
		Connection conn = ConnectionFactory.getConnection();
		boolean isValid = true;
		String sql = "select username from account_application where account_type = 'customer';";
		HashSet<String> usernames = new HashSet<String>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	
				usernames.add(rs.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isValid = false;
		}
		sql = "select username from customer;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	
				usernames.add(rs.getString("username"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			isValid = false;
		}
		return isValid && usernames.add(username);
	}
	public Customer selectCustomer(String username, String password) throws PasswordMatchException, UsernameException{
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select password from customer where "
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
		return new Customer(username, password);
	}
	
//	public void insertCustomer(Customer c) {
//		Connection conn = ConnectionFactory.getConnection();
//		String sql = "INSERT INTO cafe_customer (name, password, email, num_of_orders) values" 
//				+ "(?,?,?,0);";
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, c.username);
//			ps.setString(2, c.getPassword());
//			ps.execute();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
