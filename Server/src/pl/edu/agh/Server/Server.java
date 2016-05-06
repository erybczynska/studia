package pl.edu.agh.Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* Klasa realizująca serwer */
public class Server implements Runnable {
	private static final File root = new File("www-root");
	private short port;

	/* Konstruktor parametrowy, ustawiający numer portu */
	public Server(short port) {
		this.port = port;
	}

	@Override
	/*
	 * metoda uruchamiająca serwer
	 * 
	 */
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					new Thread(new ClientSocketHandler(socket, root)).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
