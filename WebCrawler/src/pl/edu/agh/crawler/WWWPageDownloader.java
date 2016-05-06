package pl.edu.agh.crawler;

/* Interfejs zajmujący się pobieraniem zawartości strony www */
public interface WWWPageDownloader {
	String downloadPage(String pageURL) throws DownloaderException;
}
