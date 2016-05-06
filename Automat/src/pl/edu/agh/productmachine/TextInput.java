package pl.edu.agh.productmachine;

import java.util.Scanner;
import static pl.edu.agh.productmachine.InputChoice.*;

/**
 * Klasa zarządzająca informacjami pobranymi od klienta, implementuje interfejs
 * UserInput
 */
public class TextInput implements UserInput {
	private Scanner read = new Scanner(System.in);

	/**
	 * Meteoda służąca do wyboru produktu
	 * 
	 * @return numer produktu wybranego przez klienta
	 */
	@Override
	public int getProductChoice() {
		int productChoice = read.nextInt();
		if (productChoice >= 1 && productChoice <= 10)
			return productChoice;

		return -1;
	}

	/**
	 * Metoda zwraca ilość przekazanych pieniędzy przez klienta
	 * 
	 * @return ilość przekazanych przez klienta pieniędzy w gorszach
	 */
	@Override
	public int getGroszValueInput() {
		int groszValue = read.nextInt();
		if (isACoin(groszValue))
			return groszValue;
		else
			return -1;
	}

	/**
	 * Metoda rozpoznająca co chce wykonać klient
	 * 
	 * @return opcja z menu, którą wybrał klient
	 */
	@Override
	public InputChoice recognizeInput() {
		int input = read.nextInt();
		switch (input) {
		case 1:
			return PUT_A_COIN;
		case 2:
			return CHOOSE_PRODUCT;
		case 3:
			return GET_CHANGE;
		}
		return null;
	}

	private boolean isACoin(int groszValue) {
		if (groszValue == 10 || groszValue == 20 || groszValue == 50 || groszValue == 100 || groszValue == 200
				|| groszValue == 500) {
			return true;
		}
		return false;
	}

}
