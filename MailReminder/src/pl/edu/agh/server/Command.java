package pl.edu.agh.server;

/**
 * Intrefejs reprezentujący komendę
 * 
 * @author ewelinarybczynska
 *
 */
public interface Command {
	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 * @return
	 */
	String execute();
}
