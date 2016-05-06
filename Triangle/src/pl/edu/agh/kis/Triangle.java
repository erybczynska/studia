package pl.edu.agh.kis;

/* Klasa przedstawiająca trójkąt */
public class Triangle {
	private int firstSide;
	private int secondSide;
	private int thirdSide;

	/*
	 * Konstruktor parametrowy ustawiający długości boków trójkąta
	 * 
	 * @param firstSide bok trójkąta
	 * 
	 * @param secondSida bok trójkąta
	 * 
	 * @param thirdSide bok trójkąta
	 */

	public Triangle(int firstSide, int secondSide, int thirdSide) {
		this.firstSide = firstSide;
		this.secondSide = secondSide;
		this.thirdSide = thirdSide;
	}

	/*
	 * Metoda sprawdzająca czy z boków da się utowrzyć trójkąt
	 * 
	 * @return true jeżeli jest to trójąt w przeciwnym razie false
	 */
	public boolean isGoodTriagle() {
		if (this.isSidesMakesTriangle() && (this.isSidesNotZero()))
			return true;
		return false;
	}

	/*
	 * Metoda sprawdzająca czy z któryś z boków trójkąta nie jest zerem
	 * 
	 * @return true jeżeli którykolwiek z boków trójąta jest zerem w przeciwnym
	 * razie false
	 */
	public boolean isSidesNotZero() {
		if ((firstSide > 0) && (secondSide > 0) && (thirdSide > 0))
			return true;
		return false;
	}

	/*
	 * Metoda sprawdzająca czy boki tworzą trójkąt
	 * 
	 * @return true jeżeli jest to trójąt w przeciwnym razie false
	 */
	public boolean isSidesMakesTriangle() {
		if ((firstSide < (secondSide + thirdSide)) && (secondSide < (firstSide + thirdSide))
				&& (thirdSide < (firstSide + secondSide)))
			return true;
		return false;
	}

	/*
	 * Metoda sprawdzająca czy trójąt jest równoboczny
	 * 
	 * @return true jeżeli jest to trójąt równoboczny w przeciwnym razie false
	 */
	public boolean isEquilateralTriangle() {
		if (isGoodTriagle()) {
			if ((firstSide == secondSide) && (firstSide == thirdSide))
				return true;
		}
		return false;
	}

	/*
	 * Metoda sprawdzająca czy trójąt jest równoramienny
	 * 
	 * @return true jeżeli jest to trójąt równoramienny w przeciwnym razie false
	 */
	public boolean isIsosceleTriangle() {
		if (isGoodTriagle()) {
			if ((firstSide == secondSide) || (firstSide == thirdSide) || (secondSide == thirdSide))
				return true;
		}
		return false;
	}

	/*
	 * Metoda sprawdzająca czy trójąt jest różnoboczny
	 * 
	 * @return true jeżeli jest to trójąt różnoboczny w przeciwnym razie false
	 */
	public boolean isScaleneTriangle() {
		if (isGoodTriagle()) {
			if ((firstSide != secondSide) && (firstSide != thirdSide) && (secondSide != thirdSide))
				return true;
		}
		return false;
	}
}
