package com.pastebinLite.pastebin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc {
	
	public static Connection getConnection() {
		Connection con = null;
		String user = "postgres";
		String pass = "0512";
		String url = "jdbc:postgresql://localhost:5432/pastebinlite";
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url,user,pass);
		}catch(Exception e) {
			System.out.println("Connection err: "+e.getMessage());
		}
		return con;
	}
}
