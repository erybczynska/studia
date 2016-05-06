package pl.edu.agh.lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReaderByLine {
    public static void main(String[] args) throws Exception {

        URL test = new URL("http://www.onet.pl/");
        BufferedReader input = new BufferedReader(
        new InputStreamReader(test.openStream()));

//        String line;
//        while ((line = input.readLine()) != null)
//            System.out.println(line); 
//        

        StringBuilder sb = new StringBuilder();
        String ln; 
		while ((ln = input.readLine()) != null) {
			sb.append(ln);
			sb.append("\n");
		 }
		sb.toString();
		
        input.close();

        System.out.println(sb);
    }
}
