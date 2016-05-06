package pl.edu.agh.productmachine;

/** Interfejs służący do zarządzania pieniędzmi */
public interface MoneyManager {
	/**
	 * Metoda do przyjmowania monety
	 * 
	 * @param gorszValue
	 *            ile groszy zostało przyjętych
	 */
	void putCoin(int groszValue);

	/**
	 * Metoda obciążająca konto podczas zakupu
	 * 
	 * @param price
	 *            cena zakupionego produktu
	 */
	void chargeAccount(int price);

	/**
	 * Metoda zwracająca ilość pieniędzy na koncie
	 * 
	 * @return ilość pieniędzy na koncie
	 */
	int getAccountState();

	/**
	 * Metoda zwracająca pieniadze
	 * 
	 * @return pieniądze, które zostały na koncie po zakupach
	 */
	int returnMoney();

	/**
	 * Metoda zwracająca ile jest pieniędzy w sejfie automatu
	 * 
	 * @return kwota znajdują się w sejfie
	 */
	int getSafeMoneyAmount();

	/**
	 * Metoda sprawdzająca czy klienta stać na zakup wybranego produnktu
	 * 
	 * @param cena
	 *            wybranego przez klienta produktu
	 * @return zwraca prawdę jeżeli stać na zakup produktu w przeciwnym wypadku
	 *         zwraca fałsz
	 */
	boolean canAfford(int price);

	/**
	 * Metoda zwracająca ile może być maksymalnie pieniędzy w sejfie
	 * 
	 * @return maksymalna kwota, która może znajdować się w sejfie
	 */
	int getSafeCapacity();
}
