package pl.edu.agh.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class WWWPageDownloaderSocket implements WWWPageDownloader {
	private Socket socket;
	private InputStream inputStream;
	private PrintWriter writer;

	@Override
	public String downloadPage(String pageURL) throws DownloaderException {
		URL url;
		try {
			url = new URL(pageURL);
			socket = new Socket(url.getHost(), 80);
			inputStream = socket.getInputStream();
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		writer.println("GET / HTTP/1.1");
		writer.println("Host: "+url.getHost());
		writer.println();
		writer.flush();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"))) {
			StringBuilder page =  new StringBuilder(); 
			String line;
			while ((line = reader.readLine()) != null) {
				page.append(line);
			}
			return page.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
