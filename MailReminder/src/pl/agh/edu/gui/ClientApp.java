package pl.agh.edu.gui;

/**
 * Klasa uruchamiająca aplikację
 * 
 * @author ewelinarybczynska
 *
 */
public class ClientApp {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Aplikacja wymaga 1 argumentu będącego adresem serwera");
			return;
		}
		Controller.IP = args[0];
		View view = new View();
		new Controller(view);
	}

}
