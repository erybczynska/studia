
package pl.agh.edu.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class Test {
	
	private static Socket s;
	private static InputStream inputStream;
	
//	public static String getPage (String pagePath) {
//		//
//		
//	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		URL url = new URL("http://www.kis.agh.edu.pl");
		s = new Socket(url.getHost(), 80);
		inputStream = s.getInputStream();
		OutputStream os = s.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF8"));
		pw.println("GET / HTTP/1.1");
		pw.println("Host:www.kis.agh.edu.pl");
		pw.println();
		pw.flush();
	
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
		String ln; 
		
		while((ln = br.readLine()) != null) { 
			System.out.println(">" + ln);
		}
		
		
	}

}
