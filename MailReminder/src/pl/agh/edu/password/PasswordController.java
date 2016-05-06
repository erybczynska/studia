package pl.agh.edu.password;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.edu.agh.database.Registry;

/**
 * Klasa zarządzająca weryfikacją danych
 * 
 * @author ewelinarybczynska
 *
 */
public class PasswordController {
	private static final String VERIFY_QUERY = "SELECT mail, hash from " + Registry.userTableName
			+ " where mail=? and hash=?";
	private static final PasswordController instance = new PasswordController();

	private PasswordController() {
	}

	/**
	 * Klasa zwracają jedyną instancję PasswordControllera
	 * 
	 * @return jedyna instancja PasswordControllera
	 */
	public static PasswordController getInstance() {
		return instance;
	}

	/**
	 * Metoda weryfikująca dane z bazą danych
	 * 
	 * @param mail
	 *            adres e-mail użytkownika
	 * @param hash
	 *            zahashowane hasło użytkownika
	 * @return zwraca prawdę jeżeli w bazie danych istnieje rekord (użytkownik)
	 *         o podanym adresie e-mail i zahashowanym haśle jak w argumentach
	 */
	public boolean verifyData(String mail, String hash) {
		try (PreparedStatement stmtCorrectData = Registry.newConnection().prepareStatement(VERIFY_QUERY)) {
			stmtCorrectData.setString(1, mail);
			stmtCorrectData.setString(2, hash);
			try (ResultSet rsVerify = stmtCorrectData.executeQuery()) {
				if (rsVerify.next())
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
