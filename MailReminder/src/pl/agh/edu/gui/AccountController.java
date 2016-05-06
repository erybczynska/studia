package pl.agh.edu.gui;

/**
 * Klasa kontrolująca panel użytkownika
 * 
 * @author ewelinarybczynska
 *
 */
public class AccountController {
	private RemindersController remindersController;
	private Controller controller;

	/**
	 * Konstruktor parametrowy ustawiający controller
	 * 
	 * @param controller
	 */
	public AccountController(Controller controller) {
		this.controller = controller;
		remindersController = new RemindersController(controller);
	}

	/**
	 * Metoda służąca do zmiany hasła
	 * 
	 * @param currentPass
	 *            aktualne hasło użytkownika
	 * @param newPass
	 *            nowe hasło użytkownika
	 * @return zwraca prawdę jeżeli zmiana hasła przebiegnie pomyślnie, w
	 *         przyciwnym razie zwraca fałsz
	 */
	public boolean changePassword(String currentPass, String newPass) {
		controller.checkConnection();
		controller.getSocketOut().println(String.format(ClientCommands.CHANGE_PASSWORD, currentPass, newPass));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda służąca do wylogowywania
	 * 
	 * @return zwraca prawdę jeżeli wylogowanie powiedzie się, w przeciwnym
	 *         razie zwraca fałasz
	 */
	public boolean logout() {
		controller.checkConnection();
		controller.getSocketOut().println(ClientCommands.LOGOUT);
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda służąca do usuwania użytkownika
	 * 
	 * @return zwraca prawdę gdy użytkownik zostanie prawidłowo usunięty, w
	 *         przyciwnym razie zwraca fałsz
	 * 
	 */
	public boolean deleteUser() {
		controller.checkConnection();
		controller.getSocketOut().println(String.format(ClientCommands.DELETE_USER));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda zwracająca instancję RemindersControllera
	 * 
	 * @return instancja RemindersController
	 */
	public RemindersController getRemindersController() {
		return remindersController;
	}

}
