package pl.edu.agh.vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerToFile;
import pl.edu.agh.logLibrary.StandardOutputLogger;

public class VectorsSumFromFile {
	private File input;
	private File output;
	private Logger fileLogger = null;
	private Logger stdOutLogger = new DefaultLogger(new StandardOutputLogger());

	public VectorsSumFromFile(String input, String output) {
		this.input = new File(input);
		this.output = new File(output);
	}

	public void writeRootsToFile() throws IOException {
		if (!input.exists())
			throw new IOException("Plik nie istnieje!");

		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			writeRootsLinesToFile(br);
		} catch (IOException e) {
			throw e;
		}
	}

	private void writeRootsLinesToFile(BufferedReader br) throws IOException {
		String ln = null;
		int lineNumber = 1;
		while ((ln = br.readLine()) != null) {
			Vector[] vectors;
			try {
				try {
					vectors = getVectors(ln);
				} catch (NotTheSameLengthVectors e) {
					stdOutLogger.error(e.getMessage() + " w linii:" + lineNumber);
					continue;
				}
			} catch (WrongNumberOfVectors | WrongFormatOfVector e) {
				stdOutLogger.error("Błąd danych wejściowych w linii: " + lineNumber);
				continue;
			} finally {
				lineNumber++;
			}
			Vector root = calculateRoot(vectors);
			writeToFile(root);
		}
	}

	private Vector[] getVectors(String line) throws WrongNumberOfVectors, NotTheSameLengthVectors, WrongFormatOfVector {
		String[] strVectors = line.split(",");

		if (strVectors.length != 2)
			throw new WrongNumberOfVectors();

		String[] strVec1 = strVectors[0].split(" ");
		System.out.println(strVec1.length);
		String[] strVec2 = strVectors[1].split(" ");
		System.out.println(strVec2.length);

		if (strVec1.length != strVec2.length)
			throw new NotTheSameLengthVectors();

		Vector v1 = new Vector(strVec1.length);
		Vector v2 = new Vector(strVec2.length);

		for (int i = 0; i < v1.getSize(); i++) {
			try {
				v1.setValue(i, Double.parseDouble(strVec1[i]));
				v2.setValue(i, Double.parseDouble(strVec2[i]));
			} catch (NumberFormatException e) {
				throw new WrongFormatOfVector();
			} catch (WrongIndexOfVector e) {
				e.printStackTrace();
			}
		}

		Vector[] vectors = new Vector[2];
		vectors[0] = v1;
		vectors[1] = v2;

		return vectors;
	}
	
	private Vector calculateRoot(Vector[] vectors) {
		Vector result = new Vector();
		try {
			result = vectors[0].sumOfVectors(vectors[1]);
			System.out.println(result.toString());
			System.out.println(result.toString());
		} catch (NotTheSameLengthVectors e) {
			e.printStackTrace();
		}
		return result;
	}

	private void writeToFile(Vector root) throws IOException {
		if (fileLogger == null)
			fileLogger = new DefaultLogger(new LoggerToFile(output.getAbsolutePath()));
		
		fileLogger.info(root.toString());
	}

}
