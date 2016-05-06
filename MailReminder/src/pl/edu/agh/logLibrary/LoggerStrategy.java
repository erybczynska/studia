package pl.edu.agh.logLibrary;

/* Interfejs definiujący postępowanie z danym logiem */
public interface LoggerStrategy {

	/*
	 * metoda zapisująca wiadomość danego logu
	 * 
	 * @param level poziom wiadomości do zapisania
	 * 
	 * @param message wiadomość którą ma zapiać log
	 */
	void log(LogLevel level, String message);
}
