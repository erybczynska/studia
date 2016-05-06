package pl.edu.agh.kis;

public class Test {
	
	public static String foo(String[] tab) {
		if (tab.length >2)
			return (tab[0]+tab[tab.length-1]);
		else 
			return "błąd danych";
	}

	public static void main(String[] args) {
		String s = Test.foo(new String[] {"Ala", "ma", "kota"});
		System.out.println(s);
	}

}
