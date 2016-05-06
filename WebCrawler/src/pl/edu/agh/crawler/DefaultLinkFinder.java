package pl.edu.agh.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Klasa wyszukująca adresy url na stronie */
public class DefaultLinkFinder implements LinkFinder {
	private static final Pattern WEB_URL_PATTERN = Pattern
			.compile("<[aA][^>]+[hH][rR][eE][fF]\\s*=\\s*['\"]([^'\"]+)['\"]");

	/*
	 * Metoda znajdująca na stronie adresy url i jeżeli nie został już
	 * odwiedzony doddająca ja do listy
	 * 
	 * @param strona internetowa zapisana w stringu
	 * 
	 * @visetedPages kolejka stron odwiedzonych
	 * 
	 * @return lista nieowiedzonych adresów url
	 */
	@Override
	public List<URL> getUrlsFromString(String page) {
		List<URL> urlList = new ArrayList<>();
		Matcher matcher = WEB_URL_PATTERN.matcher(page);
		while (matcher.find()) {
			String link = matcher.group(1);
			URL url;
			try {
				url = new URL(link);
			} catch (MalformedURLException e) {
				continue;
			}
			urlList.add(url);
		}
		return urlList;
	}

}
