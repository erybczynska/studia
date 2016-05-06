package pl.edu.agh.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadQueueDataBase implements DownloadQueue {

	private String dbUrl;
	private String userName;
	private String password;

	public DownloadQueueDataBase(String dbUrl, String userName, String password) {
		this.dbUrl = dbUrl;
		this.userName = userName;
		this.password = password;
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addPage(URL pageURL) {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		
        PreparedStatement stmtPageUrl = null;	
        
        try {
			stmtPageUrl = dbConnection.prepareStatement("insert into page_queue (page_url) " + "values(?)");
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

	@Override
	public boolean isEmpty() {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		
        PreparedStatement stmtPageUrl = null;	
        
		try {
			stmtPageUrl = dbConnection.prepareStatement("select * from page_queue");
			ResultSet rsPageUrl = stmtPageUrl.executeQuery();
			return !rsPageUrl.next();
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
	public URL getNextPage() {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		
		PreparedStatement stmtGetPageId = null;
		PreparedStatement stmtGetPageUrl = null;
		PreparedStatement stmtDeleteId = null;

        
        try {
        	stmtGetPageId = dbConnection.prepareStatement("SELECT MIN(id) FROM page_queue");
            ResultSet result = stmtGetPageId.executeQuery();
            result.next();
            int id = result.getInt(1);
            
            stmtGetPageUrl = dbConnection.prepareStatement("SELECT page_url FROM page_queue WHERE id = ?");
            stmtGetPageUrl.setInt(1, id);
            ResultSet resultPageUrl = stmtGetPageUrl.executeQuery();
            resultPageUrl.next();
            URL pageUrl = new URL(resultPageUrl.getString(1));
            
            stmtDeleteId = dbConnection.prepareStatement("DELETE FROM page_queue WHERE ID = ?");
            stmtDeleteId.setInt(1, id);
            stmtDeleteId.executeUpdate();
            
            return pageUrl;
            
		} catch (SQLException | MalformedURLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmtGetPageId != null) {
				try {
					stmtGetPageId.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override 
	public int size() {
		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		
		PreparedStatement stmtGetSize = null;
        
        try {
        	stmtGetSize = dbConnection.prepareStatement("SELECT * FROM page_queue");
            ResultSet result = stmtGetSize.executeQuery();
            int size = 0; 
            while(result.next()) {
            	size++;
            }
            return size;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmtGetSize != null) {
				try {
					stmtGetSize.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
