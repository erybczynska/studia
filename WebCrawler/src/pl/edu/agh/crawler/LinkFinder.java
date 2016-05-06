package pl.edu.agh.crawler;

import java.net.URL;
import java.util.List;

/* Intefejs zajmujący się znajdowaniu na stronie linków */
public interface LinkFinder {
	List<URL> getUrlsFromString(String page);
}
