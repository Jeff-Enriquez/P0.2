package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.BankApp.CustomExceptions.*;
import com.BankApp.Models.Admin;
import com.BankApp.Models.User;
import com.BankApp.Util.ConnectionFactory;

public class AdminDoa {
	public Admin selectAdmin(String username, String password) throws PasswordMatchException, UsernameException{
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select password from admin where "
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
		return new Admin(username, password);
	}
	public void approveCustomerApplication(User user) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "call approveCustomerApplication(?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.username);			
			ps.setString(2, user.getPassword());
			ps.execute();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void denyApplication(User user) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "delete from account_application where username = ?;";
		try {			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.username);
			ps.execute();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteCustomer(String username) throws UsernameException {
		CustomerDoa customerDoa = new CustomerDoa();
		boolean isNameInDB = customerDoa.isNameInDB(username);
		if(!isNameInDB) {
			throw new UsernameException();
		}
		Connection conn = ConnectionFactory.getConnection();
		String sql = "delete from customer where username = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void cancelAccount(int account) throws AccountException {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "delete from account where number = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, account);
			int i = ps.executeUpdate();
			ps.close();
			if(i == 0) {
				throw new AccountException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
