package pl.edu.agh.server;

import pl.edu.agh.database.DatabaseController;

/**
 * Klasa uruchamiająca serwer
 * 
 * @author ewelinarybczynska
 *
 */
public class ServerApp {

	/** Metoda uruchamiająca serwer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseController.createTablesIfNotExists();
		new Thread(new Server()).start();
	}

}
