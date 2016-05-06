package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.password.PasswordController;
import pl.agh.edu.password.PasswordHasher;

/**
 * Metoda reprezentująca komendę logowania
 * 
 * @author ewelinarybczynska
 *
 */
public class LoginCommand implements Command {
	private LoginCommand() {
	}

	/**
	 * Metoda zwracająca jedyną instancję LoginCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param controller
	 *            instancja PasswordController, zarządzająca weryfikacją danych
	 * @return
	 */
	public static LoginCommand newInstance(Status status, String inputLine, PasswordController controller) {
		if (status.isLoggedIn()) {
			return new LoginCommand();
		}
		return new LoginCommandNotLoggedIn(status, inputLine, controller);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.ALREADY_LOGGED_IN_503;
	}

	private static class LoginCommandNotLoggedIn extends LoginCommand {
		private Status status;
		private String mail;
		private String pass;
		private PasswordController controller;

		private LoginCommandNotLoggedIn(Status status, String inputLine, PasswordController controller) {
			this.status = status;
			Matcher matcher = CommandPatterns.LOGIN.matcher(inputLine);
			if (matcher.matches()) {
				mail = matcher.group(1);
				pass = matcher.group(2);
			}
			this.controller = controller;
		}

		@Override
		public String execute() {
			if ((mail != null) && (pass != null)) {
				if (controller.verifyData(mail, PasswordHasher.makePasswordHash(pass))) {
					status.setLoggedIn();
					status.setMail(mail);
					return Responses.USER_LOGGED_IN_230;
				}
				return Responses.WRONG_MAIL_OR_PASSWORD_430;
			}
			return Responses.WRONG_SYNTAX_540;
		}
	}

}
