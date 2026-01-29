package com.pastebinLite.pastebin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc {
	
	public static Connection getConnection() {
		Connection con = null;
		String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
		String pass = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "0512";
		String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
		String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
		String database = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "pastebinlite";
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, user, pass);
		}catch(Exception e) {
			System.out.println("Connection err: "+e.getMessage());
		}
		return con;
	}
}
