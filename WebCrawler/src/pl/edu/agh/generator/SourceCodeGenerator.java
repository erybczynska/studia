package pl.edu.agh.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/* klasa do generowania */
public class SourceCodeGenerator {
	private File inputFile;
	private String outputRoot;
	private final static String sep = File.separator;

	/**
	 * Konstruktor parametrowy sprawdzający czy plik podany w argumencie
	 * istnieje
	 * 
	 * @param inputFileName
	 *            nazwa pliku, w którym zapisana jest nazwa klasy lub ścieżka
	 *            pakietu do wygenerowania
	 * @param outputRoot
	 *            ścieżka w której zostanie stworzony pakiet/klasa
	 * @throws IOException
	 *             wyjątek rzucany gdy plik nie istnieje
	 *
	 */
	public SourceCodeGenerator(String inputFileName, String outputRoot) throws IOException {
		inputFile = new File(inputFileName);
		this.outputRoot = outputRoot;
		if (!inputFile.exists())
			throw new IOException("Plik nie istnieje!");
	}

	/**
	 * metoda generująca klasę/pakiet oraz ściżkę do niej o
	 * 
	 * @throws IOException
	 *             wyjątek rzucany gdy plik wejściowy nie istnieje
	 */
	public void generate() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String ln = null;
			while ((ln = br.readLine()) != null) {
				String[] files = ln.split("\\.");
				if (files.length > 0) {
					String directoriesPath = getDirectoriesPath(outputRoot, Arrays.copyOf(files, files.length - 1));
					generateDirectories(directoriesPath);
					File newClass = createFile(directoriesPath, files[files.length - 1]);
					ClassGenerator.generateCode(newClass, Arrays.copyOf(files, files.length - 1));
				}
			}
		}
	}

	private String getDirectoriesPath(String rootPath, String[] directories) {
		String path = rootPath;
		for (String dir : directories)
			path = path + sep + dir;

		return path;
	}

	private void generateDirectories(String directoriesPath) {
		File deepestDirectory = new File(directoriesPath);
		if (!deepestDirectory.exists())
			deepestDirectory.mkdirs();
	}

	private File createFile(String directoriesPath, String fileName) throws IOException {
		File classFile = new File(directoriesPath, fileName + ".java");
		if (!classFile.exists())
			classFile.createNewFile();
		return classFile;
	}

	/**
	 * Metoda main do tesotowania programu
	 * 
	 * @param args
	 *            tablica z nazwami plików wejściowych
	 * @throws IOException
	 *             wyjątek rzucany gdy plik nie istnieje
	 */
	public static void main(String[] args) throws IOException {
		SourceCodeGenerator test = new SourceCodeGenerator("test.txt", "/Users/ewelinarybczynska");
		test.generate();
	}
}
