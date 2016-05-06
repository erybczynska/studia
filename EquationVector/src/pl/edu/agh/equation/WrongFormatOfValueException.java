package pl.edu.agh.equation;

public class WrongFormatOfValueException extends Exception {

	private static final long serialVersionUID = -401678715817962057L;
	
	public WrongFormatOfValueException() {
		super("Argument nie jest liczba!");
	}
}
