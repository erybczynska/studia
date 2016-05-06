package pl.edu.agh.kis;

/* intefejs do generowania komunikatów */
public interface AnnouncementGenerator {
	/*
	 * Metoda ustawiająca informację o tym, że trójkąt jest równoboczny
	 * 
	 * @return String z informacją, że trójkąt jest równoboczny
	 */
	String getInfoEquilateral();

	/*
	 * Metoda ustawiająca informację o tym, że trójkąt jest równoramienny
	 * 
	 * @return String z informacją, że trójkąt jest równoramienny
	 */
	String getInfoIsosceles();

	/*
	 * Metoda ustawiająca informację o tym, że trójkąt jest różnoboczny
	 * 
	 * @return String z informacją, że trójkąt jest różnoboczny
	 */
	String getInfoScalene();

	/*
	 * Metoda ustawiająca informację o tym, że to nie jest trójkąt
	 * 
	 * @return String z informacją, że to nie jest trójkąt
	 */
	String getInfoWrongLengthOfSides();

}
