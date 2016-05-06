package pl.agh.edu.password;

import java.util.Random;

/**
 * Klasa generują hasła
 * 
 * @author ewelinarybczynska
 *
 */

public class PasswordGenerator {

	/**
	 * Metoda genereująca ośmioliterowe hasło
	 * 
	 * @return ośmioliterowe, losowe hasło
	 */
	public static String generateNewPassword() {
		Random generator = new Random();
		StringBuilder genereatedPassword = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			genereatedPassword.append((char) (97 + generator.nextInt(25)));
		}
		return genereatedPassword.toString();
	}
}
