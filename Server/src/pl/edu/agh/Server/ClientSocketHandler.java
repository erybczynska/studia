package pl.edu.agh.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/* Klasa obsługująca pojedynczego klienta */
public class ClientSocketHandler implements Runnable {
	private Socket socket;
	private File root;

	/*
	 * Konstruktor parametrowy ustawiający socket i folder główny plików serwera
	 */
	public ClientSocketHandler(Socket socket, File root) {
		this.socket = socket;
		this.root = root;
	}

	@Override
	/*
	 * metoda obsługująca jednego klienta
	 */
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

			String lineWithGet = socketIn.readLine();
			System.out.println(">" + lineWithGet);
			String line;
			while (!(line = socketIn.readLine()).equals("")) {
				System.out.println(">" + line);
			}
			
			char c;
			while ((c = (char) socketIn.read()) != '\r') {
				System.out.println("> " + c);
			}
			
			String request = getFilePathFromGetLine(lineWithGet);
			File requestedFile = new File(root, request);

			if (!requestedFile.exists()) {
				sendErrorMessage(socketOut);
			} else {
				sendFile(socketOut, requestedFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendErrorMessage(PrintWriter socketOut) {
		socketOut.println("HTTP/1.1 404 Not Found");
		socketOut.println();

		printFile(socketOut, new File(root, "404.html"));

		socketOut.flush();
		socketOut.close();
	}

	private void sendFile(PrintWriter socketOut, File requestedFile) {
		socketOut.println("HTTP/1.1 200 OK");
		socketOut.println();

		printFile(socketOut, requestedFile);

		socketOut.println();
		socketOut.flush();
		socketOut.close();
	}

	private void printFile(PrintWriter socketOut, File requestedFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(requestedFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				socketOut.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFilePathFromGetLine(String getLine) {
		String[] getLineString = getLine.split(" ");
		if (getLineString.length >= 2)
			return getLineString[1];
		return "";
	}

}
