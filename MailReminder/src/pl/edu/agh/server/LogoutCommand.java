package pl.edu.agh.server;

/**
 * Klasa reprezentująca komendę wylogowanie
 * 
 * @author ewelinarybczynska
 *
 */
public class LogoutCommand implements Command {

	/**
	 * Metoda zwracająca jedyną instancję LogoutCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @return jedyna instancję LogoutCommand
	 */
	public static LogoutCommand newInstance(Status status) {
		if (!status.isLoggedIn()) {
			return new LogoutCommand();
		}
		return new LogoutCommandLoggedIn(status);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class LogoutCommandLoggedIn extends LogoutCommand {
		private Status status;

		private LogoutCommandLoggedIn(Status status) {
			this.status = status;
		}

		@Override
		public String execute() {
			status.setLoggedOut();
			return Responses.LOGOUT_SUCCESSFUL_240;
		}
	}

}
