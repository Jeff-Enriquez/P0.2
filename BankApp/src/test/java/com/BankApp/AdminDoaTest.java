package com.BankApp;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import com.BankApp.Doa.AdminDoa;
import com.BankApp.Models.Admin;
import com.BankApp.Models.Customer;
import com.BankApp.Models.User;
import com.BankApp.Util.ConnectionFactory;
import com.BankApp.CustomExceptions.*;

public class AdminDoaTest {
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final AdminDoa adminDoa = new AdminDoa();
	@Test
	public void selectAdminUsernameException() {
		assertThrows(UsernameException.class, () -> adminDoa.selectAdmin("", ""));
	}
	@Test
	public void selectAdminPasswordException() {
		assertThrows(PasswordMatchException.class, () -> adminDoa.selectAdmin(USERNAME, ""));
	}
	@Test 
	public void selectAdmin() {
		Admin admin = null;
		try {
			admin = adminDoa.selectAdmin(USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(admin.username, USERNAME);
		assertEquals(admin.getPassword(), PASSWORD);
	}
	@Test
	public void approveApplication() {
		String NAME = "asdfJHLAJkljhasdfjhlawasfd";
		String PW = "ASDFwgASDFQWASDFjhkfdsler";
		// Create a new instance in 'account_application'
		Connection conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO account_application values ('" + NAME +"', '" + PW + "', 'customer');";
		try {
			Statement ps = conn.createStatement();
			ps.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Test aproveCustomerApplication from adminDoa
		User customer = new Customer(NAME, PW);
		adminDoa.approveCustomerApplication(customer);
		// See if the account_application was moved to the customer table
		sql = "select * from customer where username = '" + NAME + "';";
		String returnedName = null;
		String returnedPW = null;
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				returnedName = rs.getString("username");
				returnedPW = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TEST RESULTS
		assertEquals(NAME, returnedName);
		assertEquals(PW, returnedPW);
		// Remove created customer from the table
		sql = "delete from customer where username = '" + NAME +"';";
		try {
			Statement ps = conn.createStatement();
			ps.execute(sql);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
