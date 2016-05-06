package pl.edu.agh.productmachine;

import java.util.Arrays;
import java.util.Random;

/** Klasa zarządzająca magazynem, implementująca interfejs ProductStorage */
public class DefaultProductStorage implements ProductStorage {

	Slot[] slots;

	/**
	 * Konstruktor parametrowy, w który losowo wypełniany jest automat
	 * 
	 * @param liczba
	 *            slotów jaką ma zawierać tablica
	 */
	public DefaultProductStorage(int slots) {
		this.slots = new Slot[slots];
		Random rand = new Random();
		for (int i = 0; i < slots; i++) {
			this.slots[i] = new Slot(rand.nextInt(7), (rand.nextInt(40) + 10) * 10);
		}
	}

	/**
	 * Metoda zwracająca ilość rodzajów produktów znajdujących się w automacie
	 * 
	 * @return ilość rodzajów produktów znajdujących się w automacie
	 */
	@Override
	public int getNumberOfProductTypes() {
		return slots.length;
	}

	/**
	 * Metoda zwracająca ilość danego produktu
	 * 
	 * @param numer
	 *            slotu
	 * @return ilość danego produktu
	 */
	@Override
	public int getAmountOfProduct(int slot) {
		return slots[slot - 1].getAmountOfSlot();
	}

	/**
	 * Metoda pobierająca produkt z automatu
	 * 
	 * @param numer
	 *            slotu
	 * @return zwraca prawdę gdy pobranie produktu się powiodło w przeciwnym
	 *         przypadku zwraca fałsz
	 */
	@Override
	public boolean dropProduct(int slot) {
		int amount = slots[slot - 1].getAmountOfSlot();
		if (amount > 0) {
			slots[slot - 1].setAmountOfSlot(amount - 1);
			return true;
		}
		return false;
	}

	/**
	 * Metoda sprawdzająca czy slot jest pusty
	 * 
	 * @param numer
	 *            slotu liczony od 1
	 * @return zwraca prawdę jezeli danego produktu nie ma, w przeciwnym razie
	 *         zwraca fałsz
	 */
	@Override
	public boolean isEmptyProductSlot(int slot) {
		return (getAmountOfProduct(slot) <= 0);
	}

	/**
	 * Metoda zwracająca cenę produktu
	 * 
	 * @param numer
	 *            slotu liczony od 1
	 * @return cena produktu
	 */
	@Override
	public int getProductPrice(int slot) {
		return slots[slot - 1].getPriceOfSlot();
	}

	/**
	 * Metoda zwracająca kopię tablicy ze slotami
	 * 
	 * @return kopia tablicy ze slotami
	 */
	@Override
	public Slot[] getSlots() {
		return Arrays.copyOf(slots, slots.length);
	}

}
