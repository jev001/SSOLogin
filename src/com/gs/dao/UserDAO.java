package com.gs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
	private DbcpBean dbcp = DbcpBean.getInstance();

	public boolean exist(String username) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbcp.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from user where username='"
					+ username + "';");
			return rs.getInt("count") == 0 ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public String getPassword(String username) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbcp.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from user where username='"
					+ username + "';");
			if (rs.next()) {
				return rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
