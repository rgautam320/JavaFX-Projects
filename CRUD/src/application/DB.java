package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	public static Connection getConnection()
	{
		Connection connection;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:crud.db");
			return connection;
		} catch (Exception e) {
			System.out.println("Connection Failed");
			System.out.println("Error: " + e);
			return null;
		}
	}
}
