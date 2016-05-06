package pl.edu.agh.logLibrary;

/* Klasa implementująca interfejs LoggerType */
public class StandardOutputLogger implements LoggerStrategy {

	/*
	 * @param level poziom wiadomości do zapisania
	 * 
	 * @param message wiadomość którą ma zapiać log
	 */
	@Override
	public void log(LogLevel level, String message) {
		System.out.println(message);
	}
}
