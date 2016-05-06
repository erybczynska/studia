package pl.edu.agh.crawler;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerStrategy;
import pl.edu.agh.logLibrary.StandardOutputLogger;

/* Klasa obsługująca aplikację webCrawler */
public class WebCrawlerDataBase implements WebCrawler {
	private static final String dbUrl = "jdbc:h2:tcp://localhost/~/WebCrawler";
	private static final String userName = "sa";
	private static final String password = "";

	private DownloadQueue queue = new DownloadQueueDataBase(dbUrl, userName, password);
	private LinkFinder finder = new DefaultLinkFinder();
	private VisitedPages visitedPages = new VisitedPagesDataBase(dbUrl, userName, password);
	private WWWPageDownloader downloader = new WWWPageDownloaderSocket();
	private Logger log;

	/*
	 * Konstruktor parametrowy ustawiający adres strony startowej od której
	 * rozpocznie się wyszukiwanie adresów url
	 * 
	 * @param rootPage adres strony początkowej
	 */
	public WebCrawlerDataBase(URL rootPage) {
		this(rootPage, new StandardOutputLogger());
	}

	/*
	 * Konstruktor parametrowy ustawiający adres strony startowej od której
	 * rozpocznie się wyszukiwanie adresów url oraz typ logger
	 * 
	 * @param rootPage adres strony początkowej
	 * 
	 * @param loggerType typ logera
	 */
	public WebCrawlerDataBase(URL rootPage, LoggerStrategy loggerType) {
		cleanDataBase();
		queue.addPage(rootPage);
		log = new DefaultLogger(loggerType);
	}

	/* Metoda obsługująca webCrawlera */
	public void run() {
		while (!queue.isEmpty()) {
			handlePage(queue.getNextPage());
		}
	}

	private void cleanDataBase() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e2) {
			throw new RuntimeException(e2);

		}

		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}

		PreparedStatement stmtCleanVisitedPages = null;
		PreparedStatement stmtCleanPageQueue = null;

		try {
			stmtCleanVisitedPages = dbConnection.prepareStatement("DELETE FROM visited_pages");
			stmtCleanVisitedPages.executeUpdate();

			stmtCleanPageQueue = dbConnection.prepareStatement("DELETE FROM page_queue");
			stmtCleanPageQueue.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmtCleanVisitedPages != null || stmtCleanPageQueue != null) {
				try {
					stmtCleanPageQueue.close();
					stmtCleanVisitedPages.close();

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

	private void handlePage(URL page) {
		log.info("Aktualnie przetwarzana strona: " + page);
		try {
			visitedPages.addVisitedPage(page);
			String pageData = downloader.downloadPage(page.toString());
			List<URL> urlsFound = finder.getUrlsFromString(pageData);
			for (URL url : urlsFound) {
				if (!visitedPages.pageAlreadyVisited(url))
					queue.addPage(url);
			}
		} catch (DownloaderException e) {
			return;
		} finally {
			log.info("W kolejce pozostało: " + queue.size());
		}
	}

	/*
	 * Metoda ustawiająca typ loggera
	 * 
	 * @param type typ loggera
	 */
	public void setLoggerType(LoggerStrategy type) {
		log.setLogger(type);
	}

}
