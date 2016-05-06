package pl.edu.agh.crawler;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitedPagesDataBase implements VisitedPages {
	private String dbUrl;
	private String userName;
	private String password;

	public VisitedPagesDataBase(String dbUrl, String userName, String password) {
		this.dbUrl = dbUrl;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public boolean pageAlreadyVisited(URL pageURL) {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}

		PreparedStatement stmtPageUrl = null;

		try {
			stmtPageUrl = dbConnection.prepareStatement("select * from visited_pages where page_url like ?");
			stmtPageUrl.setString(1, pageURL.toString());
			ResultSet rsPageUrl = stmtPageUrl.executeQuery();
			return rsPageUrl.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (stmtPageUrl != null) {
				try {
					stmtPageUrl.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addVisitedPage(URL pageURL) {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}

		PreparedStatement stmtPageUrl = null;

		try {
			stmtPageUrl = dbConnection.prepareStatement("insert into visited_pages (page_url) " + "values(?)");
			stmtPageUrl.setString(1, pageURL.toString());
			stmtPageUrl.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmtPageUrl != null) {
				try {
					stmtPageUrl.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			dbConnection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
