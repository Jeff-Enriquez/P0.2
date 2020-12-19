package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.BankApp.Models.Customer;
import com.BankApp.Util.ConnectionFactory;

public class CustomerDoa {
	public void insertCustomer(Customer c) {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO cafe_customer (name, password, email, num_of_orders) values" 
				+ "(?,?,?,0);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.username);
			ps.setString(2, c.getPassword());
			ps.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}