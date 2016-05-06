package pl.edu.agh.productmachine;

/** Interfejs zarządzający odbieraniem od informacji od klienta */
public interface UserInput {
	/**
	 * Meteoda służąca do wyboru produktu
	 * 
	 * @return numer produktu wybranego przez klienta
	 */
	int getProductChoice();

	/**
	 * Metoda zwraca ilość przekazanych pieniędzy przez klienta
	 * 
	 * @return ilość przekazanych przez klienta pieniędzy w gorszach
	 */
	int getGroszValueInput();

	/**
	 * Metoda rozpoznająca co chce wykonać klient
	 * 
	 * @return opcja z menu, którą wybrał klient
	 */
	InputChoice recognizeInput();
}