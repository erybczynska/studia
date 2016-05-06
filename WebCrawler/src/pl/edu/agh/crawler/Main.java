package pl.edu.agh.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		URL rootPage = null;
		try {
			rootPage = new URL("http://www.onet.pl");
		} catch (MalformedURLException e) {
			System.err.println("Blad strony wejsciowej");
			System.exit(1);
		}
		
		WebCrawler webCrawler;
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.println("Którego WebCrawlera masz ochotę użyć?");
			System.out.println("1. WebCrawlerDatabase");
			System.out.println("2. WebCrawlerUrl");
			
			String input = scanner.nextLine();
			if (input.equals("1")) {
				webCrawler = WebCrawlerFactory.createWebCrawler(WebCrowlerType.DATABASE, rootPage);
				break;
			} else if (input.equals("2")) {
				webCrawler = WebCrawlerFactory.createWebCrawler(WebCrowlerType.MEMORY, rootPage);
				break;
			}
		}
		scanner.close();
		webCrawler.run();
	}

}
