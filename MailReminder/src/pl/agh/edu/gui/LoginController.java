package pl.agh.edu.gui;

import java.awt.event.ActionListener;

/**
 * Klasa kontrolująca panel logowania
 * 
 * @author ewelinarybczynska
 *
 */
public class LoginController {
	private Controller controller;

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param controller
	 */
	public LoginController(Controller controller) {
		this.controller = controller;
	}

	public ActionListener getForgetPasswordListener() {
		return null;
	}

	/**
	 * Metoda obsługująca sytuację w której użytkownik zapomni hasła
	 * 
	 * @param mail
	 *            adres e-mail użytkownika
	 * @return
	 */
	public boolean sendForgetPassword(String mail) {
		controller.checkConnection();
		controller.getSocketOut().println(String.format(ClientCommands.FORGET_PASSWORD, mail));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda służąca do zarejestrowania nowego użytkownika
	 * 
	 * @param mail
	 *            adres e-mail osoby rejestrującej się
	 * @return zwraca prawdę gdy rejestracja przebiegnie poprawnie, w przyciwnym
	 *         razie zwraca fałsz
	 */
	public boolean registryNewUser(String mail) {
		controller.checkConnection();
		controller.getSocketOut().println(String.format(ClientCommands.REGISTRY, mail));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda obsługująca logowanie się użytkownika
	 * 
	 * @param mail
	 *            adres e-mail użytkownika
	 * @param password
	 *            hasło użytkownika
	 * @return zwraca prawdę gdy logowanie przebiegnie poprawnie, w przyciwnym
	 *         razie zwraca fałsz
	 */
	public boolean login(String mail, String password) {
		controller.checkConnection();
		controller.getSocketOut().println(String.format(ClientCommands.LOGIN, mail, password));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

}
