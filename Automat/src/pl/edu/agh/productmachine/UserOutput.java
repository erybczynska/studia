package pl.edu.agh.productmachine;

/** Interfejs zarządzający wysyłaniem do informacji od klienta */
public interface UserOutput {
	/**
	 * Metoda wypisująca informację o niewystarczającej ilość środków na dany
	 * zakup
	 * 
	 * @param cost
	 *            cena produktu, który klient chce kupić
	 *
	 * @param accountBalance
	 *            aktualny stan konta
	 */
	void printNotEnoughMoneyForProduct(int cost, int accountBalance);

	/** Metoda informująca, że dany produkt się skończył */
	void printNoProductLeft();

	/**
	 * Metoda wypisująca informację o stanie konta
	 * 
	 * @param groszValue
	 *            ilość pieniędzy na koncie w groszach
	 */
	void printMoneyLeft(int groszValue);

	/** Metoda wyświetlająca menu */
	void printMenu();

	/**
	 * Metoda informująca który produkt klient zakupił
	 * 
	 * @param productNumber
	 *            numer zakupionego produktu
	 */
	void printProductSold(int productNumber);

	/** Metoda informująca o braku możliwości wydania reszty */
	void printCantReturnChange();

	/** Metoda prosząca o wrzecenie monety */
	void askForACoin();

	/**
	 * Metoda prosząca o wybór produktu
	 * 
	 * @param productTypesAmount
	 *            liczba typów produktów
	 */
	void askForProductChoose(int productTypesAmount);

	/**
	 * Metoda wypisująca ile pieniądzy zostanie zwróconych
	 * 
	 * @param groszValue
	 *            ilość pieniędzy, które zostaną zwrócone w groszach
	 */
	void printReturnedMoney(int groszValue);

	/**
	 * Metoda wypisująca produkty znajdujące się w automacie
	 * 
	 * @slots tablica slotów z produktami
	 */
	void printProductPrices(Slot... slots);
}
