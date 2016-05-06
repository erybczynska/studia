package pl.edu.agh.logLibrary;

/* Klasa main służąca do tesowania działania logów */ 
public class Main {

	public static void main(String[] args) {
		Logger log = new DefaultLogger(new StandardOutputLogger());
		log.warning("Warning");
		log.setLogger(new LoggerToFile("log.txt"));	
		log.warning("NEW WARNING IN FILE");
	}
}
