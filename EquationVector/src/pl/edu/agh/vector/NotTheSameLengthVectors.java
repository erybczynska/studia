package pl.edu.agh.vector;

public class NotTheSameLengthVectors extends Exception {
	private static final long serialVersionUID = -2240619599549096668L;
	
	public NotTheSameLengthVectors() {
		super("Wektory nie są takiej samej długości!");
	}

}
