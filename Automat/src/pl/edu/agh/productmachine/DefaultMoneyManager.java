package pl.edu.agh.productmachine;

/** Klasa obsugująca pieniądze, implementująca interfejs MoneyManager */ 
public class DefaultMoneyManager implements MoneyManager {
	private static final int SAFE_CAPACITY = 5_000_00;
	private int account = 0;
	private int groszesInSafe = 0;

	/**
	 * Metoda do przyjmowania monety
	 * 
	 * @param gorszValue
	 *            ile groszy zostało przyjętych
	 */
	@Override
	public void putCoin(int groszValue) {
		account += groszValue;
	}

	/**
	 * Metoda zwracająca ilość pieniędzy na koncie
	 * 
	 * @return ilość pieniędzy na koncie
	 */
	@Override
	public int getAccountState() {
		return account;
	}

	/**
	 * Metoda zwracająca pieniadze
	 * 
	 * @return pieniądze, które zostały na koncie po zakupach
	 */
	@Override
	public int returnMoney() {
		int money = getAccountState();
		account = 0;
		return money;
	}

	/**
	 * Metoda obciążająca konto podczas zakupu
	 * 
	 * @param price
	 *            cena zakupionego produktu
	 */
	@Override
	public void chargeAccount(int price) {
		if (!canAfford(price))
			throw new RuntimeException("Zbyt malo na koncie!");
		groszesInSafe += price;
		account -= price;
	}

	/**
	 * Metoda sprawdzająca czy klienta stać na zakup wybranego produnktu
	 * 
	 * @param cena
	 *            wybranego przez klienta produktu
	 * @return zwraca prawdę jeżeli stać na zakup produktu w przeciwnym wypadku
	 *         zwraca fałsz
	 */
	@Override
	public boolean canAfford(int price) {
		return price <= account;
	}

	/**
	 * Metoda zwracająca ile jest pieniędzy w sejfie automatu
	 * 
	 * @return kwota znajdują się w sejfie
	 */
	@Override
	public int getSafeMoneyAmount() {
		return groszesInSafe;
	}

	/**
	 * Metoda zwracająca ile może być maksymalnie pieniędzy w sejfie
	 * 
	 * @return maksymalna kwota, która może znajdować się w sejfie
	 */
	@Override
	public int getSafeCapacity() {
		return SAFE_CAPACITY;
	}

}
