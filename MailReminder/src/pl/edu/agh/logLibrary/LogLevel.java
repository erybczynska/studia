package pl.edu.agh.logLibrary;
/* Enum do kategoryzowanie typów wiadomości */ 
public enum LogLevel {
	WARNING(1), INFO(2), ERROR(3);
	/*
	 * numeryczna wartość rodzaju wiadomości 
	 */
	public final int level;

	private LogLevel(int level) {
		this.level = level;
	}
}
