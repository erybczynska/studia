package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.password.PasswordController;
import pl.agh.edu.password.PasswordHasher;
import pl.edu.agh.database.User;

/**
 * Klasa reprezentująca komendę zmiany hasła
 * 
 * @author ewelinarybczynska
 *
 */
public class ChangePasswordCommand implements Command {

	/**
	 * Metoda zwracająca jedyną instancję ChangePasswordCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param controller
	 *            jedyna instancja PasswordControllera, służąca do weryfikacji
	 *            danych
	 * @return jedyna instancję ChangePasswordCommand
	 */
	public static ChangePasswordCommand newInstance(Status status, String inputLine, PasswordController controller) {
		if (!status.isLoggedIn()) {
			return new ChangePasswordCommand();
		}
		return new ChangePasswordPatternLoggedIn(status, inputLine, controller);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class ChangePasswordPatternLoggedIn extends ChangePasswordCommand {
		private String currentPass = "";
		private String newPass = "";
		private String mail = "";
		private PasswordController controller;

		private ChangePasswordPatternLoggedIn(Status status, String inputLine, PasswordController controller) {
			Matcher matcher = CommandPatterns.CHANGE_PASSWORD.matcher(inputLine);
			if (matcher.matches()) {
				currentPass = matcher.group(1);
				newPass = matcher.group(2);
				mail = status.getMail();
			}
			this.controller = controller;
		}

		@Override
		public String execute() {
			if (controller.verifyData(mail, PasswordHasher.makePasswordHash(currentPass))) {
				User user = User.getUser(mail);
				user.setPasswordHash(PasswordHasher.makePasswordHash(newPass));
				user.update();
				return Responses.COMMAND_SUCCESSFUL_200;
			}
			return Responses.WRONG_MAIL_OR_PASSWORD_430;
		}
	}
}
