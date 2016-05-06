package pl.edu.agh.productmachine;

/** Interfejs diagnozujący automat */

public interface Diagnostic {
	/**
	 * Metoda zwracająca ilość pieniędzy w sejfie
	 * 
	 * @return ilość pieniędzy w sejfie automatu
	 */
	int countMoneyInSafe();

	/**
	 * Metoda zwracająca maksymalną ilość pieniędzy która może być przechowywana
	 * w sejfie
	 * 
	 * @return maksymalną ilość pieniędzy która może być przechowywana w sejfie
	 */
	int getSafeCapacity();

	/**
	 * Metoda sprawdzająca czy potrzebne uzupełnienie produtów w automacie
	 * 
	 * @return zwraca prawdę gdy potrzbne jest uzupełnienie produktów w
	 *         automacie, w przeciwnym przypadku zwraca fałsz
	 */
	boolean isRefillNeeded();
}
