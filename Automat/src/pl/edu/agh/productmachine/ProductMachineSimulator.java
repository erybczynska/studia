package pl.edu.agh.productmachine;

/** Klasa uruchamiająca automat, implementująca interfejs ProductMachine */

public class ProductMachineSimulator implements ProductMachine {
	private MoneyManager moneyManager = new DefaultMoneyManager();
	private ProductStorage storage = new DefaultProductStorage(10);
	private UserInput input = new TextInput();
	private UserOutput output = new TextOutput();
	private Diagnostic diag = new DefaultDiagnostic(moneyManager, storage);

	/** Metoda uruchamiająca automat */
	@Override
	public void run() {
		while (true) {
			output.printMoneyLeft(moneyManager.getAccountState());
			output.printMenu();
			InputChoice choice = input.recognizeInput();

			switch (choice) {
			case PUT_A_COIN:
				puttingACoin();
				break;
			case CHOOSE_PRODUCT:
				choosingProduct();
				break;
			case GET_CHANGE:
				gettingChange();
				break;
			default:
				System.out.println("Niepoprawny wybór");
			}
		}
	}

	/**
	 * Metoda zwarająca obiekt diagnostyczny danego automatu
	 * 
	 * @return obiekt diagnostyczny danego automatu
	 */
	@Override
	public Diagnostic getDiagnostic() {
		return diag;
	}

	private void puttingACoin() {
		output.askForACoin();
		int groszValue = input.getGroszValueInput();
		if (groszValue == -1)
			return;
		moneyManager.putCoin(groszValue);
	}

	private void choosingProduct() {
		output.printProductPrices(storage.getSlots());
		output.askForProductChoose(storage.getNumberOfProductTypes());
		int productChoice = input.getProductChoice();
		if (productChoice == -1)
			return;

		if (!checkPossibilityOfTransaction(productChoice))
			return;

		int price = storage.getProductPrice(productChoice);
		moneyManager.chargeAccount(price);
		storage.dropProduct(productChoice);
	}

	private boolean checkPossibilityOfTransaction(int productChoice) {
		if (storage.isEmptyProductSlot(productChoice)) {
			output.printNoProductLeft();
			return false;
		}

		int price = storage.getProductPrice(productChoice);
		if (moneyManager.getAccountState() < price) {
			output.printNotEnoughMoneyForProduct(price, moneyManager.getAccountState());
			return false;
		}

		return true;
	}

	private void gettingChange() {
		int change = moneyManager.returnMoney();
		output.printReturnedMoney(change);
	}

}
