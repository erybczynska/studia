package pl.edu.agh.logLibrary;

import static pl.edu.agh.logLibrary.LogLevel.ERROR;
import static pl.edu.agh.logLibrary.LogLevel.INFO;
import static pl.edu.agh.logLibrary.LogLevel.WARNING;

import java.util.Date;

public class DefaultLogger implements Logger {
	private LoggerStrategy logger;
	private boolean saveInfo = true;
	private boolean saveWarning = true;
	private boolean saveError = true;

	/*
	 * konstruktor ustawiający dany typ logger
	 * 
	 * @param logger typ loggera
	 */
	public DefaultLogger(LoggerStrategy logger) {
		this.logger = logger;
	}

	/*
	 * metoda zapisująca wodomość typu warning
	 * 
	 * @param message wiadomość typu warning którą ma zapiać log
	 */
	@Override
	public void warning(String message) {
		if (saveWarning)
			logger.log(WARNING, new Date() + "  " + message);
	}

	/*
	 * metoda zapisująca wodomość typu warning
	 * 
	 * @param message wiadomość typu info którą ma zapiać log
	 */
	@Override
	public void info(String message) {
		if (saveInfo)
			logger.log(INFO, new Date() + "  " + message);
	}

	/*
	 * metoda zapisująca wodomość typu warning
	 * 
	 * @param message wiadomość typu error którą ma zapiać log
	 */
	@Override
	public void error(String message) {
		if (saveError)
			logger.log(ERROR, new Date() + "  " + message);
	}

	/*
	 * metoda sprawdzająca czy wiadomość typu info ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu info ma być zapisana, w przeciwnym
	 * przypadku zwraca wartość false
	 */
	@Override
	public boolean isSaveInfo() {
		return saveInfo;
	}

	/*
	 * metoda ustalająca czy wiadomość typu info ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu info ma być
	 * zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	@Override
	public void setSaveInfo(boolean saveInfo) {
		this.saveInfo = saveInfo;
	}

	/*
	 * metoda sprawdzająca czy wiadomość typu warning ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu warning ma być zapisana, w
	 * przeciwnym przypadku zwraca wartość false
	 */
	@Override
	public boolean isSaveWarning() {
		return saveWarning;
	}

	/*
	 * metoda ustalająca czy wiadomość typu warning ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu warning ma
	 * być zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	@Override
	public void setSaveWarning(boolean saveWarning) {
		this.saveWarning = saveWarning;
	}

	/*
	 * metoda sprawdzająca czy wiadomość typu error ma być zapisana
	 * 
	 * @return zwraca true gdy wiadomość typu error ma być zapisana, w
	 * przeciwnym przypadku zwraca wartość false
	 */
	@Override
	public boolean isSaveError() {
		return saveError;
	}

	/*
	 * metoda ustalająca czy wiadomość typu error ma być zapisana
	 * 
	 * @param saveWarning przyjmuje wartość true gdy wiadomość typu error ma być
	 * zapisana w przeciwnym przypadku przyjmuje wartość false
	 */
	@Override
	public void setSaveError(boolean saveError) {
		this.saveError = saveError;
	}

	/*
	 * metoda ustawiająca podany logger
	 * 
	 * @param log logger do ustawienia
	 */
	@Override
	public void setLogger(LoggerStrategy logger) {
		this.logger = logger;
	}
}
