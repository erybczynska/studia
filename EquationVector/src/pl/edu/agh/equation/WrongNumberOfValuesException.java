package pl.edu.agh.equation;

public class WrongNumberOfValuesException extends Exception {
	private static final long serialVersionUID = 9150402322870946929L;
	
	public WrongNumberOfValuesException() {
		super("Zla ilosc argumentow!");
	}
}