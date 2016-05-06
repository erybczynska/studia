package pl.agh.edu.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SpeedTest {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.h2.Driver");

		int n = 1000;
		try {
			long time1 = System.currentTimeMillis();
			makeRecordsWithReconnecting(n, "jdbc:h2:tcp://localhost/~/test", "sa", "");
			long time2 = System.currentTimeMillis();
			System.out.println("Czas wstawiania " + n + " rekordów z odnawianiem połączenia: " + (time2 - time1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			long time1 = System.currentTimeMillis();
			makeRecordsWithoutReconnecting(n, "jdbc:h2:tcp://localhost/~/test", "sa", "");
			long time2 = System.currentTimeMillis();
			System.out.println("Czas wstawiania " + n + " rekordów bez odnawiania połączenia: " + (time2 - time1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void makeRecordsWithReconnecting(int n, String url, String user, String password)
			throws SQLException {
		for (int i = 0; i < n; i++) {
			Connection conn = DriverManager.getConnection(url, user, password);
			JDBCTest1.insertEmployeeV2(conn, "Ala", "Kowalska", null);
			conn.close();
		}

	}

	public static void makeRecordsWithoutReconnecting(int n, String url, String user, String password)
			throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, password);
		for (int i = 0; i < n; i++) {
			JDBCTest1.insertEmployeeV2(conn, "Ala", "Kowalska", null);
		}
		conn.close();
	}
}
