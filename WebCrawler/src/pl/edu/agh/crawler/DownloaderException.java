package pl.edu.agh.crawler;

/* Klasa wyjątku przy pobieraniu */ 
public class DownloaderException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final String STANDARD_MESSAGE = "Nie ma takiego adresu URL";
	
	public DownloaderException() {
		super(STANDARD_MESSAGE);
	}
	
	public DownloaderException(Throwable cause) {
		super(STANDARD_MESSAGE, cause);
	}

}
