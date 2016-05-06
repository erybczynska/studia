package pl.edu.agh.server;

/**
 * Metoda reprezentująca status zalogowania
 * 
 * @author ewelinarybczynska
 *
 */
public class Status {
	private boolean isLoggedIn = false;
	private String mail;

	/**
	 * Metoda zwracająca stan zalogowania
	 * 
	 * @return zwraca prawdę gdy użytkownik jest zalogowany, w przeciwnym razie
	 *         zwraca fałsz
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Metoda ustawiająca stan zalogowania na zalogowany
	 * 
	 */
	public void setLoggedIn() {
		isLoggedIn = true;
	}

	/**
	 * Metoda ustawiająca stan zalogowania na wylogowany
	 * 
	 */
	public void setLoggedOut() {
		isLoggedIn = false;
		mail = null;
	}

	/**
	 * Metoda pobierająca adres e-mail użytkownika
	 * 
	 * @return adres e-mail użytkownika
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Metoda ustawiająca adres e-mail użytkownika
	 * 
	 * @param mail
	 *            adres e-mail użytkownika
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

}
