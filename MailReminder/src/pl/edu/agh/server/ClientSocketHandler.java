package pl.edu.agh.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerToFile;

/* Klasa obsługująca pojedynczego klienta */
public class ClientSocketHandler implements Runnable {
	private static Random random = new Random();
	private Socket socket;
	private Scanner socketIn;
	private PrintStream socketOut;
	private Logger logger;

	/*
	 * Konstruktor parametrowy ustawiający socket i folder główny plików serwera
	 */
	public ClientSocketHandler(Socket socket) throws IOException {
		this.socket = socket;
		socketIn = new Scanner(socket.getInputStream());
		socketOut = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true);
		logger = new DefaultLogger(getLoggerStrategy());
	}

	private LoggerToFile getLoggerStrategy() {
		return new LoggerToFile(Server.LOGS_DIRECTORY.resolve(getLogFileName()).toString());
	}

	private String getLogFileName() {
		return System.currentTimeMillis() +"_" + (10 + random.nextInt(90) + ".log");
	}
 
	@Override
	/*
	 * metoda obsługująca jednego klienta
	 */
	public void run() {
		try {
			logger.info("Connected with: " + socket.getInetAddress().getHostAddress());
			Status status = new Status();
			LineExecutor executor = new LineExecutor(status);
			while (!Thread.currentThread().isInterrupted() && socketIn.hasNext()) {
				String inputLine = socketIn.nextLine();
				logger.info("> " + inputLine);
				String response = executor.execute(inputLine);
				logger.info("RESPONSE: " + response);
				socketOut.println(response);
				if (response.equals(Responses.BYE_221)) {
					break;
				}
			}
		} finally {
			closeSocket();
		}
	}

	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
