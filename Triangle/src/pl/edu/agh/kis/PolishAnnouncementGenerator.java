package pl.edu.agh.kis; 

/* Klasa ustawiająca informacje zwrotne w języku polskim */
public class PolishAnnouncementGenerator implements AnnouncementGenerator {

	/* Metoda ustawiająca informację o tym, że trójkąt jest równoboczny
	 * 
	 * @return String z informacją, że trójkąt jest równoboczny
	 */
	@Override
	public String getInfoEquilateral() {
		return "Trójkat jest równoboczny.";
	}

	/* Metoda ustawiająca informację o tym, że trójkąt jest równoramienny
	 * 
	 * @return String z informacją, że trójkąt jest równoramienny
	 */
	@Override
	public String getInfoIsosceles() {
		return "Trójkat jest równoramienny.";
	}

	/* Metoda ustawiająca informację o tym, że trójkąt jest różnoboczny
	 * 
	 * @return String z informacją, że trójkąt jest różnoboczny
	 */
	@Override
	public String getInfoScalene() {
		return "Trójkat jest różnoboczny.";
	}

	/* Metoda ustawiająca informację o tym, że to nie jest trójkąt 
	 * 
	 * @return String z informacją, że to nie jest trójkąt 
	 */
	@Override
	public String getInfoWrongLengthOfSides() {
		return "To nie jest trójkąt!";
	}

}
