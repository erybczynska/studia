
package pl.edu.agh.logLibrary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* Klasa służaca do zapisywania logów do plików */
public class LoggerToFile implements LoggerStrategy {
	private File output;
	private PrintWriter writer = null;

	/*
	 * konstruktor ustawiający nazwę pliku do którego ma być zapisana wiadomość
	 * logu
	 * 
	 * @param output nazwa pliku do którego ma być zapisana wiadomość lug
	 */
	public LoggerToFile(String output) {
		this.output = new File(output);
	}

	/*
	 * metoda zapisująca wiadomość danego logu
	 * 
	 * @param level poziom wiadomości do zapisania
	 * 
	 * @param message wiadomość którą ma zapiać log
	 */
	@Override
	public void log(LogLevel level, String message) {
		if (writer == null)
			try {
				writer = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (message != null) {
			writer.println(message);
			writer.flush();
		}
	}
}

