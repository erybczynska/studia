package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.mailing.MailSender;
import pl.agh.edu.password.PasswordGenerator;
import pl.agh.edu.password.PasswordHasher;
import pl.edu.agh.database.User;

/**
 * Klasa reprezentująa komendę przypomnienia hasła
 * 
 * @author ewelinarybczynska
 *
 */
public class ForgetPasswordCommand implements Command {

	private ForgetPasswordCommand() {
	}

	/**
	 * Metoda zwracająca jedyną instancję ForgetPasswordCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param sender
	 *            instacja MailSender, służąca do wysłania nowego,
	 *            wygenerowanego hasła
	 * @return jedyna instancję ForgetPasswordCommand
	 */
	public static ForgetPasswordCommand newInstance(Status status, String inputLine, MailSender sender) {
		if (status.isLoggedIn()) {
			return new ForgetPasswordCommand();
		}
		return new ForgetPasswordCommandNotLoggedIn(status, inputLine, sender);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 *   
	 */
	@Override
	public String execute() {
		return Responses.ALREADY_LOGGED_IN_503;
	}

	private static class ForgetPasswordCommandNotLoggedIn extends ForgetPasswordCommand {
		private String mail;
		private MailSender sender;

		private ForgetPasswordCommandNotLoggedIn(Status status, String inputLine, MailSender sender) {
			Matcher matcher = CommandPatterns.FORGET_PASSWORD.matcher(inputLine);
			if (matcher.matches()) {
				mail = matcher.group(1);
			}
			this.sender = sender;
		}

		@Override
		public String execute() {
			User user = User.getUser(mail);
			if (user != null) {
				String newPassword = PasswordGenerator.generateNewPassword();
				sender.sendNewPassowordMail(mail, newPassword);
				user.setPasswordHash(PasswordHasher.makePasswordHash(newPassword));
				user.update();
				return Responses.COMMAND_SUCCESSFUL_200;
			}
			return Responses.WRONG_MAIL_OR_PASSWORD_430;
		}
	}

}
