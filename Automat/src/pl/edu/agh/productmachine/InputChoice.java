package pl.edu.agh.productmachine;

/**Enum do wyboru opcji w menu */
public enum InputChoice {
	PUT_A_COIN(1), CHOOSE_PRODUCT(2), GET_CHANGE(3);
	
	/** Wartość liczbowa odpowiadająca opcji */ 
	public final int value;
	private InputChoice(int val) {
		value = val;
	}
}
