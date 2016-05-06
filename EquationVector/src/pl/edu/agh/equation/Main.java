package pl.edu.agh.equation;

import java.io.IOException;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.StandardOutputLogger;

public class Main {
	
	public static void main(String[] args) {
		Logger log = new DefaultLogger(new StandardOutputLogger());
		
		if (args.length != 2) {
			log.error("Wywolonie: java Equations nazwa_pliku_wejsciowego nazwa_pliku_wyjsciowego");
			System.exit(0);
		}
		
		EquationFromFile eq = new EquationFromFile("input.txt", "output.txt");
		try {
			eq.writeRootsToFile();
		} catch (IOException e) {
			log.error("Błąd wejścia/wyjścia");
		}
	}

}
