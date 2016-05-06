package pl.edu.agh.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.agh.edu.mailing.MailingController;

/**
 * Klasa realizująca serwer
 */
public class Server implements Runnable {
	public static Path LOGS_DIRECTORY = Paths.get("logs").toAbsolutePath();
	private static final int THREAD_POOL_SIZE = 50;

	/**
	 * metoda uruchamiająca serwer
	 */
	@Override
	public void run() {
		createLogsDirIfNotExists();
		MailingController.getInstance().setReminderToSend();
		ExecutorService executor = null;
		try (ServerSocket serverSocket = new ServerSocket(Port.PORT)) {
			executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			System.out.println("port=" + Port.PORT);
			System.out.println("---- SERVER START ----");
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					executor.submit(new ClientSocketHandler(socket));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}
	}

	private void createLogsDirIfNotExists() {
		try {
			if (Files.notExists(LOGS_DIRECTORY)) {
				Files.createDirectories(LOGS_DIRECTORY);
			}
			if (!Files.isDirectory(LOGS_DIRECTORY)) {
				throw new RuntimeException("Logs file isn't directory");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
