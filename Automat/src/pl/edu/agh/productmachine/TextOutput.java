package pl.edu.agh.productmachine;

/**
 * Klasa zarządzająca informacjami wysyłanymi do klienta, implementuje interfejs
 * UserOutput
 */
public class TextOutput implements UserOutput {
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
	@Override
	public void printNotEnoughMoneyForProduct(int cost, int accountBalance) {
		double plnAcc = accountBalance / 100.0;
		double plnCost = cost / 100.0;

		System.out.format("Zbyt mało pieniędzy na koncie - stan konta=%.2fzł, potrzeba: %.2fzł%n", plnAcc, plnCost);
	}

	/**
	 * Metoda wypisująca informację o stanie konta
	 * 
	 * @param groszValue
	 *            ilość pieniędzy na koncie w groszach
	 */
	@Override
	public void printMoneyLeft(int groszValue) {
		double pln = groszValue / 100.0;
		System.out.format("Stan konta: %.2fzł%n", pln);
	}

	/**
	 * Metoda informująca który produkt klient zakupił
	 * 
	 * @param productNumber
	 *            numer zakupionego produktu
	 */
	@Override
	public void printProductSold(int productNumber) {
		System.out.println("Udało Ci się kupić produkt nr " + productNumber);
	}

	/** Metoda informująca o braku możliwości wydania reszty */
	@Override
	public void printCantReturnChange() {
		System.out.println("Nie mam jak wydać reszty :(");
	}

	/** Metoda wyświetlająca menu */
	@Override
	public void printMenu() {
		System.out.format("1. Wrzucanie monet%n2. Wybieranie produktu%n3. Zwrot reszty%n");
	}

	/** Metoda prosząca o wrzecenie monety */
	@Override
	public void askForACoin() {
		System.out.println("Wrzuc monetę (wpisz liczbę groszy reprezentującą istniejącą monetę)");
	}

	/**
	 * Metoda prosząca o wybór produktu
	 * 
	 * @param productTypesAmount
	 *            liczba typów produktów
	 */
	@Override
	public void askForProductChoose(int productTypesAmount) {
		System.out.println("Wybierz produkt (1-" + productTypesAmount + ")");
	}

	/** Metoda informująca, że dany produkt się skończył */
	@Override
	public void printNoProductLeft() {
		System.out.println("Wybrany produkt niestety się skończył");
	}

	/**
	 * Metoda wypisująca ile pieniądzy zostanie zwróconych
	 * 
	 * @param groszValue
	 *            ilość pieniędzy, które zostaną zwrócone w groszach
	 */
	@Override
	public void printReturnedMoney(int groszValue) {
		double PLN = groszValue / 100.0;
		System.out.format("Zwracam %.2fzł%n", PLN);
	}
	
	/**
	 * Metoda wypisująca produkty znajdujące się w automacie
	 * 
	 * @slots tablica slotów z produktami
	 */
	@Override
	public void printProductPrices(Slot... slots) {
		System.out.println("0. Zrezygnuj z zakupów");
		for (int i = 0; i < slots.length; i++) {
			double pln = slots[i].getPriceOfSlot() / 100.0;
			int amount = slots[i].getAmountOfSlot();
			System.out.format("%d. cena: %.2fzł, ilość %d%n", i + 1, pln, amount);
		}

	}

}
