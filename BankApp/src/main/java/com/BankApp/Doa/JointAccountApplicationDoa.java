package com.BankApp.Doa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.BankApp.Util.ConnectionFactory;

public class JointAccountApplicationDoa {
	public String getApplication() {
		String username = null;
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select * from joint_application limit 1;";
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if(rs.next()) {
				username = rs.getString("username");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return username;
	}
	
	public Integer applicationCount() {
		Integer count = 0;
		Connection conn = ConnectionFactory.getConnection();
		String sql = "select count(*) as total from joint_application";
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			count = rs.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
