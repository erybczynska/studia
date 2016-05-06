package pl.edu.agh.kis;

/* Klasa ustawiająca informacje zwrotne w języku angielskim */

public class EnglishAnnouncementGenerator implements AnnouncementGenerator {

	/* Metoda ustawiająca informację o tym, że trójkąt jest równoboczny
	 * 
	 * @return String z informacją, że trójkąt jest równoboczny
	 */
	@Override
	public String getInfoEquilateral() {
		return "This triangle is equilateral";		
	}


	/* Metoda ustawiająca informację o tym, że trójkąt jest równoramienny
	 * 
	 * @return String z informacją, że trójkąt jest równoramienny
	 */
	@Override
	public String getInfoIsosceles() {
		return "This triangle is isosceles";		
	}

	/* Metoda ustawiająca informację o tym, że trójkąt jest różnoboczny
	 * 
	 * @return String z informacją, że trójkąt jest różnoboczny
	 */
	@Override
	public String getInfoScalene() {
		return "This triangle is scalene";		
	}

	/* Metoda ustawiająca informację o tym, że to nie jest trójkąt 
	 * 
	 * @return String z informacją, że to nie jest trójkąt 
	 */
	@Override
	public String getInfoWrongLengthOfSides() {
		return "This isn't a triangle!";		
	}

}
