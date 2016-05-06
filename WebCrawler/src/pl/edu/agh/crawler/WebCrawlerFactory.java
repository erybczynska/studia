package pl.edu.agh.crawler;

import java.net.URL;

public class WebCrawlerFactory {
	public static WebCrawler createWebCrawler(WebCrowlerType type, URL rootPage) {
		switch (type) {
		case DATABASE:
			return new WebCrawlerDataBase(rootPage);
		default:
			return new WebCrawlerUrl(rootPage);
		}
	}

}
