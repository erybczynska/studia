package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.mailing.MailSender;
import pl.agh.edu.password.PasswordGenerator;
import pl.agh.edu.password.PasswordHasher;
import pl.edu.agh.database.User;

/**
 * Klasa reprezentującą komednę dodawania nowego użytkownika
 * 
 * @author ewelinarybczynska
 *
 */
public class AddUserCommand implements Command {
	private AddUserCommand() {
	}

	/**
	 * Metoda zwracająca jedyną instację AddUserCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param sender
	 *            jedyna instancja MailSendera, służącego do wysyłania
	 *            wiadomości e-mail
	 * @return
	 */
	public static AddUserCommand newInstance(Status status, String inputLine, MailSender sender) {
		if (status.isLoggedIn()) {
			return new AddUserCommand();
		}
		return new AddUserCommandNotLoggedIn(inputLine, sender);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.ALREADY_LOGGED_IN_503;
	}

	private static class AddUserCommandNotLoggedIn extends AddUserCommand {
		private String mail;
		private MailSender sender;

		private AddUserCommandNotLoggedIn(String inputLine, MailSender sender) {
			Matcher matcher = CommandPatterns.ADD_USER.matcher(inputLine);
			if (matcher.matches()) {
				mail = matcher.group(1);
			}
			this.sender = sender;
		}

		@Override
		public String execute() {
			if (mail != null) {
				if (User.getUser(mail) == null) {
					String pass = PasswordGenerator.generateNewPassword();
					User user = new User(mail, PasswordHasher.makePasswordHash(pass));
					user.create();
					sender.sendRegistryMail(mail, pass);
					return Responses.COMMAND_SUCCESSFUL_200;
				}
				return Responses.USER_EXIST_505;
			}
			return Responses.WRONG_SYNTAX_540;
		}
	}

}
