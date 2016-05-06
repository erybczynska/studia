package pl.edu.agh.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/* Klasa zajmijąca się pobraniem strony do stringa */
public class WWWPageDownloaderURL implements WWWPageDownloader {

	/*
	 * Metoda zajmująca się pobraniem strony WWW do stringa
	 * 
	 * @param pageURL adres url strony do pobrania
	 * 
	 * @return string z zawartością strony WWW podanej jako argument
	 */
	@Override
	public String downloadPage(String pageURL) throws DownloaderException {
		URL page = getURLFromString(pageURL);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(page.openStream()))) {
			return getStringFromPage(br);
		} catch (IOException e) {
			throw new DownloaderException(e);
		}
	}

	private URL getURLFromString(String pageURL) throws DownloaderException {
		try {
			return new URL(pageURL);
		} catch (MalformedURLException e) {
			throw new DownloaderException(e);
		}
	}

	private String getStringFromPage(BufferedReader input) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = input.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
