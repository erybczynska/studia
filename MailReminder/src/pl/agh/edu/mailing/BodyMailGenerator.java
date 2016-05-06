package pl.agh.edu.mailing;

/** 
 * Klasa generująca treść maila 
 * @author ewelinarybczynska
 *
 */
public class BodyMailGenerator {
	
	/**
	 * Metoda generująca treść maila rejestracyjnego
	 * @param password hasło nowego użytkownika 
	 * @return treść maila rejestracyjnego
	 */
	public String makeRegistryEmailBody(String password) {
		return "Hello,<br>Thank you for making an account.<br>Your passoword is - " + password + " <br>On your profile you can change password. Now you'll never miss any event!<br><br>Regards,<br>Mail Reminder";
	}
	
	/** 
	 * Metoda generująca treść maila przypominającego 
	 * @param content zawartość przypomnienia
	 * @return treść maila przypominającego
	 */
	public String makeReminderEmailBody(String content) {
		return "Hello,<br>Reminder - <b>" + content + "</b><br><br>Regards,<br>Mail Reminder";
	}
	
	/** 
	 * Metoda generująca treść maila z nowym hasłem
	 * @param newPassword nowe hasło użytkownika 
	 * @return treść maila z nowym hasłem
	 */
	public String makeNewPasswordEmailBody(String newPassword) {
		return "Hello,<br>Your new password is  - " + newPassword + "<br><br>Regards,<br>Mail Reminder";
	}

}
