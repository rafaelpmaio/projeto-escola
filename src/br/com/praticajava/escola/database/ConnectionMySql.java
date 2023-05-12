package br.com.praticajava.escola.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySql {

	private static String dbUrl = "jdbc:mysql://localhost:3308/database_escola"; //ajuste o número da porta e nome da database
	private static String dbUser = "root"; //ajuste para seu login
	private static String dbPass = ""; //ajuste para sua senha

	public static Connection connect() {
		try {
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			return connection;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
