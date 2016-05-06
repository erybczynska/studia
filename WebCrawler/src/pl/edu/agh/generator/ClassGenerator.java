package pl.edu.agh.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/* Klasa genrująca kod w pliku */
public class ClassGenerator {
	/**
	 * Klasa generująca kod w pliku
	 * 
	 * @param newClass
	 *            nazwa pliku, w którym zostanie wygenerowany kod
	 * @param packageNames
	 *            tablica zawierająca nazwy folderów tworzących pakiet
	 * @throws FileNotFoundException
	 *             wyjątek rzucany gdy plik nie zostanie odnaleziony
	 */
	public static void generateCode(File newClass, String[] packageNames) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(newClass);
		String className = newClass.getName();
		String[] tab = className.split("\\.");
		className = tab[0];
		String packageName = getPackageName(packageNames);
		writer.println("package " + packageName + ";");
		writer.println("/**");
		writer.println("* To jest wygenerowana automatycznie klasa " + className);
		writer.println("*");
		writer.println("*/");
		writer.println("public class " + className + " " + "{");
		writer.println("\t/**");
		writer.println("\t* Domyslny konstruktor klasy " + className);
		writer.println("\t*/");
		writer.println("\tpublic " + className + "() {");
		writer.println("\t\t// metoda wygenerowana - nalezy uzupelnic implementacje");
		writer.println("\t}");
		writer.println("\t/**");
		writer.println("\t* Metoda main automatycznie wygenerowana");
		writer.println("\t* @param args tablica argumentow przekazanych do programu");
		writer.println("\t*/");
		writer.println("\tpublic static void main(String[] args) {");
		writer.println(
				"\t\tSystem.out.println(\"Metoda jeszcze nie jest zaimplementowana - tylko wygenerowany wzorzec.\");");
		writer.println("\t}");
		writer.println("}");

		writer.flush();
		writer.close();
	}

	/**
	 * Metoda tworząca nazwę pakietu
	 * 
	 * @param packageNames
	 *            tablica zawierająca nazwy folderów tworzących pakiet
	 * @return zwraca nazwę pakietu w postaci Stringa 
	 */
	private static String getPackageName(String[] packageNames) {
		StringBuilder packageName = new StringBuilder();
		for (String pkg : packageNames) {
			packageName.append(pkg + ".");
		}
		return packageName.substring(0, packageName.length() - 1);
	}
}
