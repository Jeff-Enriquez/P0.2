package com.BankApp;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.BankApp.CustomExceptions.*;
import com.BankApp.Doa.CustomerDoa;
import com.BankApp.Models.Customer;
import com.BankApp.Util.ConnectionFactory;

public class CustomerDoaTest {
	private static CustomerDoa customerDoa = new CustomerDoa();
	private static Integer ACCNUMBER = null;
	private static Integer ACCNUMBER2 = null;
	private static final String USERNAME = "ewriughertkcxgjzhQWER";
	private static final String PASSWORD = "ouiwerhtgWERTWRG";
	private static final String USERNAME2 = "ehwutjhsdWERTfds";
	private static final String PASSWORD2 = "jgvxNVGDqwerqg";
	
	public static Integer createCustomer(String name, String pwd) {
		Integer accNum = null;
		Connection conn = ConnectionFactory.getConnection();
		String sql;
		sql = "insert into customer values('" + name + "', '" + pwd + "');";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "insert into account(balance, customer) values(1000.0, '" + name + "');";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "select number from account where customer = '" + name + "';";
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			accNum = rs.getInt("number");
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accNum;
	}
	public static void deleteCustomer(String name) {
		Connection conn = ConnectionFactory.getConnection();
		String sql;
		sql = "delete from customer where username = '" + name + "';";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void resetBalance(Integer accNum) {
		Connection conn = ConnectionFactory.getConnection();
		String sql;
		sql = "update account set balance = 1000.0 where number = " + accNum + ";";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@BeforeAll
	public static void createCustomers() {
		ACCNUMBER = createCustomer(USERNAME, PASSWORD);
		ACCNUMBER2 = createCustomer(USERNAME2, PASSWORD2);
	}
	@AfterAll
	public static void deleteCustomers() {
		deleteCustomer(USERNAME);
		deleteCustomer(USERNAME2);
	}
	@BeforeEach
	public void resetBalances() {
		resetBalance(ACCNUMBER);
	}
	@Test
	public void submitApplication() {
		String NAME = "aawhieaWQETGasfdhkalsdkrj";
		String PWD = "asgduaiwehrASDFw";
		// Run 'submitApplication' method from CustomerDoa
		Customer c = new Customer(NAME, PWD);
		customerDoa.submitApplication(c);
		String returnedName = null;
		String returnedPW = null;
		Connection conn = ConnectionFactory.getConnection();
		// See if the customer was added to 'account_application'
		String sql = "select * from account_application where username = '" + NAME + "';";
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
		assertEquals(PWD, returnedPW);
		// Delete created customer
		sql = "delete from account_application where username = '" + NAME + "'";
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void withdraw() {
		Double balance = 0.0;
		try {
			balance = customerDoa.withdraw(ACCNUMBER, 10.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(990.0, balance);
	}
	@Test
	public void withdrawNegativeException() {
		assertThrows(NegativeCashException.class, () -> customerDoa.withdraw(ACCNUMBER, -10.0));
	}
	@Test
	public void withdrawOverdrawException() {
		assertThrows(OverdrawException.class, () -> customerDoa.withdraw(ACCNUMBER, 1000000.0));
	}
	@Test
	public void deposit() {
		Double balance = 0.0;
		try {
			balance = customerDoa.deposit(ACCNUMBER, 10.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1010.0, balance);
	}
	@Test
	public void depositNegativeException() {
		assertThrows(NegativeCashException.class, () -> customerDoa.deposit(ACCNUMBER, -10.0));
	}
	@Test
	public void transfer() {
		Double[] balance = null;
		try {
			balance = customerDoa.transfer(ACCNUMBER, ACCNUMBER2, 50.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(950.0, balance[0]);
		assertEquals(1050.0, balance[1]);
	}
	@Test
	public void transferNegativeException() {
		assertThrows(NegativeCashException.class, () -> customerDoa.transfer(ACCNUMBER, ACCNUMBER2, -10.0));
	}
}
