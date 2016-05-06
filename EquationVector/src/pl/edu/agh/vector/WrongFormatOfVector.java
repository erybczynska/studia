package pl.edu.agh.vector;

public class WrongFormatOfVector extends Exception{

	private static final long serialVersionUID = 7836565365518965028L;
	public WrongFormatOfVector() {
		super("Argument nie jest liczba!");
	}
}
