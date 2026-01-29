package com.pastebinLite.pastebin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc {
	
	public static Connection getConnection() {
		Connection con = null;
		String user = System.getenv("PGUSER") != null ? System.getenv("PGUSER") : "postgres";
		String pass = System.getenv("PGPASSWORD") != null ? System.getenv("PGPASSWORD") : "0512";
		String host = System.getenv("PGHOST") != null ? System.getenv("PGHOST") : "localhost";
		String port = System.getenv("PGPORT") != null ? System.getenv("PGPORT") : "5432";
		String database = System.getenv("PGDATABASE") != null ? System.getenv("PGDATABASE") : "pastebinlite";
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
