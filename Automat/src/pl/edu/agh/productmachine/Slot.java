package pl.edu.agh.productmachine;
/** Klasa repreentująca slot */
public class Slot {
	private int amount;
	private int price;
	
	/** Kontruktor parametrowy */
	public Slot(int amount, int price) {
		this.amount = amount;
		this.price = price;
	}

	/** 
	 * Metoda podająca ilość produktów w slocie 
	 * @return ilość produktów w slocie 
	 */
	public int getAmountOfSlot() {
		return amount;
	}

	/** 
	 * Metoda podająca ilość produktów w slocie 
	 * @return ilość produktów w slocie 
	 */
	public void setAmountOfSlot(int amount) {
		this.amount = amount;
	}

	/** 
	 * Metoda zwarająca cenę produktów w slocie
	 * @return cena produktów w slocie
	 */
	public int getPriceOfSlot() {
		return price;
	}

	/** 
	 * Metoda ustawiająca cenę produktów w slocie
	 * @param price cena produktów w slocie
	 */
	public void setPriceOfSlot(int price) {
		this.price = price;
	}

}
