package pl.edu.agh.vector;

public class WrongNumberOfVectors extends Exception {

	private static final long serialVersionUID = 5628871739538890367L;
	public WrongNumberOfVectors() {
		super("Zła liczba wektorów!");
	}
}
