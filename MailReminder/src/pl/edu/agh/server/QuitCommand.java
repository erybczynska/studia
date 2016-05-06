package pl.edu.agh.server;

/**
 * Klasa reperezentująca komendę wyjść
 * 
 * @author ewelinarybczynska
 *
 */
public class QuitCommand implements Command {

	private QuitCommand() {
	}

	/**
	 * Klasa zwracająca jedyną instancję QuitCommand
	 * 
	 * @return jedyną instancję QuitCommand
	 */
	public static QuitCommand newInstance() {
		return new QuitCommand();
	}

	/**
	 * Metoda wykonująca działanie komendy
	 */
	@Override
	public String execute() {
		return Responses.BYE_221;
	}

}
