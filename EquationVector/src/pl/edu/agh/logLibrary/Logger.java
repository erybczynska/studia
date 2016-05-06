package pl.edu.agh.logLibrary;

/* Interfejs Logger służący do zarządznia logam */
public interface Logger {
	/*
	 * metoda zapisująca wiadomość typu warning
	 * 
	 * @param message wiadomość typu warning którą ma zapiać log
	 */
	void warning(String message);

	/*
	 * metoda zapisująca wiadomość typu warning
	 * 
	 * @param message wiadomość typu info którą ma zapiać log
	 */
	void info(String message);

	/*
	 * metoda zapisująca wiadomość typu warning
	 * 
	 * @param message wiadomość typu error którą ma zapiać log
	 */
	void error(String message);

	/*
	 * metoda sprawdzająca czy wiadomość typu info ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu info ma być zapisana, w przeciwnym
	 * przypadku zwraca wartość false
	 */
	boolean isSaveInfo();

	/*
	 * metoda ustalająca czy wiadomość typu info ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu info ma być
	 * zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	void setSaveInfo(boolean saveInfo);

	/*
	 * metoda sprawdzająca czy wiadomość typu warning ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu warning ma być zapisana, w
	 * przeciwnym przypadku zwraca wartość false
	 */
	boolean isSaveWarning();

	/*
	 * metoda ustalająca czy wiadomość typu warning ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu warning ma
	 * być zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	void setSaveWarning(boolean saveWarning);

	/*
	 * metoda sprawdzająca czy wiadomość typu error ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu error ma być zapisana, w
	 * przeciwnym przypadku zwraca wartość false
	 */
	boolean isSaveError();

	/*
	 * metoda ustalająca czy wiadomość typu error ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu error ma być
	 * zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	void setSaveError(boolean saveError);

	/*
	 * metoda ustawiająca podany logger
	 * 
	 * @param log logger do ustawienia
	 */
	void setLogger(LoggerStrategy log);
}
