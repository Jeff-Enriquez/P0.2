package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.BankApp.Models.Employee;
import com.BankApp.Util.ConnectionFactory;
import com.BankApp.CustomExceptions.*;

public class EmployeeDoa {
	
	public Employee selectEmployee(String username, String password) throws PasswordMatchException{
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select password from employee where "
				+ "username = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			//throw exception if username does not exist
			String confirmPassword = rs.getString("password");
			if(!password.equals(confirmPassword)) {				
				throw new PasswordMatchException();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		Employee e = null;
		
		return e;
	}
}
