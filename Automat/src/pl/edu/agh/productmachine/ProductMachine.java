package pl.edu.agh.productmachine;

/** Intrefejs uruchamiający automat */
public interface ProductMachine {
	/**
	 * Metoda zwarająca obiekt diagnostyczny danego automatu
	 * 
	 * @return obiekt diagnostyczny danego automatu
	 */
	Diagnostic getDiagnostic();

	/** Metoda uruchamiająca automat */
	void run();
}
