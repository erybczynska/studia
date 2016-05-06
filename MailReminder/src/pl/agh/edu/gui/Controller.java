package pl.agh.edu.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import pl.edu.agh.server.Port;

/**
 * Klasa reprezentująca kontrolę aplikacji
 * 
 * @author ewelinarybczynska
 *
 */
public class Controller {
	public static String IP = "localhost";

	private Socket socket;
	private Scanner socketIn;
	private PrintStream socketOut;
	private LoginController loginController = new LoginController(this);
	private AccountController accountController = new AccountController(this);

	/**
	 * Konstruktor parametrowy ustawiający widok
	 * 
	 * @param view
	 *            widok
	 */
	public Controller(View view) {
		view.setController(this);
	}

	/**
	 * Metoda pobierająca loginController
	 * 
	 * @return loginController
	 */
	public LoginController getLoginController() {
		return loginController;
	}

	/**
	 * Metoda pobierająca accountController
	 * 
	 * @return accountController
	 */
	public AccountController getAccountController() {
		return accountController;
	}

	/**
	 * Metoda tworząca połączenie
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(IP, Port.PORT);
		socketIn = (new Scanner(new BufferedInputStream(socket.getInputStream())));
		socketOut = (new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true));
	}

	/**
	 * Metoda sprawdzająca połaczenie
	 * 
	 */
	public void checkConnection() {
		if (socket == null) {
			try {
				connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda pobierająca socketIn
	 * 
	 * @return socketIn
	 */
	public Scanner getSocketIn() {
		return socketIn;
	}

	/**
	 * Metoda pobierająca socketOut
	 * 
	 * @return socketOut
	 */
	public PrintStream getSocketOut() {
		return socketOut;
	}

}
