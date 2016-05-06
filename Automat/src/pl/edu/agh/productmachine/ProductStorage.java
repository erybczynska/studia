package pl.edu.agh.productmachine;

/** Interfejs zarządzający magazynem automatu */
public interface ProductStorage {
	/**
	 * Metoda zwracająca ilość rodzajów produktów znajdujących się w automacie
	 * 
	 * @return ilość rodzajów produktów znajdujących się w automacie
	 */
	int getNumberOfProductTypes();

	/**
	 * Metoda zwracająca ilość danego produktu
	 * 
	 * @return ilość danego produktu
	 */
	int getAmountOfProduct(int slot);

	/**
	 * Metoda pobierająca produkt z automatu
	 * 
	 * @param numer
	 *            slotu
	 * @return zwraca prawdę gdy pobranie produktu się powiodło w przeciwnym
	 *         przypadku zwraca fałsz
	 */
	boolean dropProduct(int slot);

	/**
	 * Metoda sprawdzająca czy slot jest pusty
	 * 
	 * @param numer
	 *            slotu
	 * @return zwraca prawdę jezeli danego produktu nie ma, w przeciwnym razie
	 *         zwraca fałsz
	 */
	boolean isEmptyProductSlot(int slot);

	/**
	 * Metoda zwracająca cenę produktu
	 * 
	 * @param numer
	 *            slotu
	 * @return cena produktu
	 */
	int getProductPrice(int slot);

	/**
	 * Metoda zwracająca kopię tablicy ze slotami
	 * 
	 * @return kopia tablicy ze slotami
	 */
	Slot[] getSlots();
}