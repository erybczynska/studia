package pl.edu.agh.crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/* Klasa obsługująca odwiedzone strony */
public class VisitedPagesSet implements VisitedPages {
	private Set<URL> urls = new HashSet<>();

	/*
	 * Metoda sprawdzająca czy dana strona została już odwiedzona
	 * 
	 * @return zwraca true jeżeli została odwiedzona, w przeciwnym wypadku
	 * zwraca false
	 */
	@Override
	public boolean pageAlreadyVisited(URL pageURL) {
		return urls.contains(pageURL);
	}

	/*
	 * <Metoda dodająca stronę do odwiedzonych stron
	 * 
	 * @param pageUrl adres url stronyp
	 */
	@Override
	public void addVisitedPage(URL pageURL) {
		urls.add(pageURL);
	}
}
