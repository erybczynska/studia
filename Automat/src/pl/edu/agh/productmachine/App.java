package pl.edu.agh.productmachine;
/** Klasa służąca do uruchomienia autmatu */
class App {
	
	private App(){
		
	}
	/** W klasie main zostaje stworzony i uruchomiony automat */
	public static void main(String[] args) {
		ProductMachine p = new ProductMachineSimulator();
		p.run();
	}
}
