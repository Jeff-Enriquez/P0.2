package com.BankApp;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Models.Customer;
import com.BankApp.Util.ConnectionFactory;

public class CustomerDoaTest {
	private static CustomerDoa customerDoa = new CustomerDoa();
	@BeforeAll
	public void createCustomer() {
		String USERNAME = "ewriughertkcxgjzhQWER";
		String PASSWORD  = "ouiwerhtgWERTWRG";
		Connection conn = ConnectionFactory.getConnection();
		String sql = "insert into customer values('" + USERNAME + "', '" + PASSWORD + "');";
		try {
			Statement ps = conn.createStatement();
			ps.executeQuery(sql);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterAll
	public void deleteCustomer() {
		String USERNAME = "ewriughertkcxgjzhQWER";
		Connection conn = ConnectionFactory.getConnection();
		String sql = "delete from customer where username = '" + USERNAME + "';";
		try {
			Statement ps = conn.createStatement();
			ps.executeQuery(sql);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void submitApplication() {
		String USERNAME = "aawhieaWQETGasfdhkalsdkrj";
		String PASSWORD = "asgduaiwehrASDFw";
		// Test 'submitApplication' method from CustomerDoa
		Customer c = new Customer(USERNAME, PASSWORD);
		customerDoa.submitApplication(c);
		String returnedName = null;
		String returnedPW = null;
		Connection conn = ConnectionFactory.getConnection();
		// See if the customer was added to 'account_application'
		String sql = "select * from account_application where username = '" + USERNAME + "';";
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				returnedName = rs.getString("username");
				returnedPW = rs.getString("password");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TEST RESULTS
		assertEquals(USERNAME, returnedName);
		assertEquals(PASSWORD, returnedPW);
	}
	@Test
	public void withdraw() {
		
	}
	@Test
	public void withdrawNegativeException() {
		
	}
	@Test
	public void withdrawOverdrawException() {
		
	}
	@Test
	public void deposit() {
		
	}
	@Test
	public void depositNegativeException() {
		
	}
	@Test
	public void transfer() {
		
	}
	@Test
	public void transferNegativeException() {
		
	}
}
