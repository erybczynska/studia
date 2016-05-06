package pl.agh.edu.gui;

import javax.swing.JFrame;

/**
 * Klasa reprezentująca widok
 * 
 * @author ewelinarybczynska
 *
 */
public class View extends JFrame {

	private static final long serialVersionUID = 827536779073807274L;
	private LoginPanel loginPanel;
	private AccountPanel accountPanel;

	/**
	 * Konstruktor tworzący widok
	 * 
	 */
	public View() {
		init();
		setSize(800, 600);
		setLocation(250, 280);
		setTitle("Mail Reminder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param controller
	 */
	public void setController(Controller controller) {
		loginPanel.setController(controller.getLoginController());
		accountPanel.setController(controller.getAccountController());
	}

	/**
	 * Metoda pobierająca hało
	 * 
	 * @return hasło
	 */
	public String getPassword() {
		return loginPanel.getPassword();
	}

	/**
	 * Metoda pobierająca adres e-mail
	 * 
	 * @return adres e-mail
	 */
	public String getMail() {
		return loginPanel.getMail();
	}

	/**
	 * Metoda zmieniajaca panel na panel użytkownika
	 * 
	 * @param mail
	 *            adres e-mail użytkownika
	 */
	public void setAccountPanel(String mail) {
		accountPanel.setMail(mail);
		switchView(ViewType.ACCOUNT_VIEW);
	}

	/**
	 * Metoda służąca do wylogowania
	 * 
	 */
	public void logout() {
		switchView(ViewType.LOGIN_VIEW);
		loginPanel.setMail();
		loginPanel.setPassword();
	}

	/**
	 * Metoda zmieniająca widok na panel powiadomień
	 * 
	 */
	public void manageReminders() {
		switchView(ViewType.REMINDERS_VIEW);
	}

	private void switchView(ViewType type) {
		hideAllPanels();
		switch (type) {
		case LOGIN_VIEW:
			loginPanel.setVisible(true);
			break;
		default:
		case ACCOUNT_VIEW:
			accountPanel.setVisible(true);
			break;
		}
	}

	private void hideAllPanels() {
		loginPanel.setVisible(false);
		accountPanel.setVisible(false);
	}

	private void init() {
		loginPanel = new LoginPanel(this);
		accountPanel = new AccountPanel(this);
		accountPanel.setVisible(false);
		add(loginPanel);
		add(accountPanel);
	}

}
