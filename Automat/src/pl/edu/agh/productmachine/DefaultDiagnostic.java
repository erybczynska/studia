package pl.edu.agh.productmachine;

/** Klasa diagnozująca automat, impementująca interfejs Diagnostic */
public class DefaultDiagnostic implements Diagnostic {

	private MoneyManager moneyManager;
	private ProductStorage stor;

	/*
	 * Konstruktor paramatryczny
	 * 
	 * @param m obiekt klasy MoneyManager, jest źródłem informacji o płatnościach 
	 * 
	 * @param storage obiekt klasy ProductStorage, jest źródłem informacji o magazynie
	 */
	public DefaultDiagnostic(MoneyManager m, ProductStorage storage) {
		moneyManager = m;
		stor = storage;
	}

	/**
	 * Metoda zwracająca ilość pieniędzy w sejfie
	 * 
	 * @return ilość pieniędzy w sejfie automatu
	 */
	@Override
	public int countMoneyInSafe() {
		return moneyManager.getSafeMoneyAmount();
	}

	/**
	 * Metoda zwracająca maksymalną ilość pieniędzy która może być przechowywana
	 * w sejfie
	 * 
	 * @return maksymalną ilość pieniędzy która może być przechowywana w sejfie
	 */
	@Override
	public int getSafeCapacity() {
		return moneyManager.getSafeCapacity();
	}

	/**
	 * Metoda sprawdzająca czy potrzebne uzupełnienie produtów w automacie
	 * 
	 * @return zwraca prawdę gdy potrzbne jest uzupełnienie produktów w
	 *         automacie, w przeciwnym przypadku zwraca fałsz
	 */
	@Override
	public boolean isRefillNeeded() {
		int emptySlots = 0;
		int numberOfSlots = stor.getNumberOfProductTypes();
		for (int i = 1; i <= numberOfSlots; i++) {
			if (stor.isEmptyProductSlot(i))
				emptySlots++;
		}
		return emptySlots > (numberOfSlots / 2);
	}

}
