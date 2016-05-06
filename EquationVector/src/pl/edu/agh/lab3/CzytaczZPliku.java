package pl.edu.agh.lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CzytaczZPliku {

	public CzytaczZPliku() {
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Musisz podac nazwe pliku");
			return; // System.exit(1);
		}
		String filePath = args[0];
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String ln = null; 
		
		while ((ln = br.readLine()) != null) {
			System.out.println(">" + ln);
		}
		br.close();
	}
}

