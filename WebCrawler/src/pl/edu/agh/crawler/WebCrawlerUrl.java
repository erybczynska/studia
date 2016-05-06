package pl.edu.agh.crawler;

import java.net.URL;
import java.util.List;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerStrategy;
import pl.edu.agh.logLibrary.StandardOutputLogger;

/* Klasa obsługująca aplikację webCrawler */
public class WebCrawlerUrl implements WebCrawler {
	private DownloadQueue queue = new DownloadQueueURL();
	private LinkFinder finder = new DefaultLinkFinder();
	private VisitedPages visitedPages = new VisitedPagesSet();
	private WWWPageDownloader downloader = new WWWPageDownloaderURL();
	private Logger log;

	/*
	 * Konstruktor parametrowy ustawiający adres strony startowej od której
	 * rozpocznie się wyszukiwanie adresów url
	 * 
	 * @param rootPage adres strony początkowej
	 */
	public WebCrawlerUrl(URL rootPage) {
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
	public WebCrawlerUrl(URL rootPage, LoggerStrategy loggerType) {
		queue.addPage(rootPage);
		log = new DefaultLogger(loggerType);
	}

	/* Metoda obsługująca webCrawlera */
	public void run() {
		URL page;
		while ((page = queue.getNextPage()) != null) {
			handlePage(page);
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
