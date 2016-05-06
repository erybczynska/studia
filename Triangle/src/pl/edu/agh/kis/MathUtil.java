package pl.edu.agh.kis;

/* Klasa sprawdzająca typ trójkąta i podająca informację na ten temat */
public class MathUtil {

	private MathUtil() {
	}

	/*
	 * Metoda sprawdzjaca typ trójkąta
	 * 
	 * @param firstSide bok trójkąta
	 * 
	 * @param secondSide bok trójkata
	 * 
	 * @param thirdSide bok trójkąta
	 * 
	 * @param infoEquilateral informacja, że trójkąt jest równoboczny
	 * 
	 * @param infoIsosceles informacja, że trójkąt jest równoramienny
	 * 
	 * @param infoScalene informacja, że trójkąt jest różnoboczny
	 * 
	 * @param infoWrongLengthOfSides informacja, że to nie jest trójkąt
	 * 
	 * @return informacja na temat trójkąta
	 * 
	 */
	public static String specifyTypeOfTriangle(int firstSide, int secondSide, int thirdSide, String infoEquilateral,
			String infoIsosceles, String infoScalene, String infoWrongLengthOfSides) {
		Triangle triangle = new Triangle(firstSide, secondSide, thirdSide);

		if (triangle.isEquilateralTriangle())
			return infoEquilateral;
		if (triangle.isIsosceleTriangle())
			return infoIsosceles;
		if (triangle.isScaleneTriangle())
			return infoScalene;
		return infoWrongLengthOfSides;
	}
}
