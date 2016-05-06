package pl.edu.agh.database;

import java.util.Objects;

/**
 * Klasa repezentująca pojedynczne przypomnienie 
 * @author ewelinarybczynska
 *
 */
public class Reminder {
	private final String mail;
	private String content;
	private long dateToSend;

	/**
	 * Konstruktor parametrowy, ustawiający adres e-mail, zawartość oraz czas wysłania powiadomienia
	 * @param mail adres e-mail na który ma zostać wysłane powiadomienie
	 * @param content zawartość powiadomienia
	 * @param date data, kiedy ma zostać wysłane powiadomienie
	 */
	public Reminder(String mail, String content, long date) {
		this.mail = mail;
		this.content = content;
		this.dateToSend = date;
	}

	/**
	 * Metoda zwracająca adres e-mail na który ma zostać wysłane powiadomienie 
	 * @return adres e-mail na który ma zostać wysłane powiadomienie
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Metoda zwracająca zawartość powiadomienia
	 * @return zawartość powiadomienia
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Metoda ustawiająca treść powiadomienia
	 * @param content treść powiadomienia
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Metoda zwracająca datę kiedy ma zostać wysłane powiadomienia
	 * @return data, kiedy ma zostać wysłane powiadomienie 
	 */
	public long getDateToSend() {
		return dateToSend;
	}

	/**
	 * Metoda ustawiająca datę kiedy ma zostać wysłane powiadomienia
	 * @param dateToSend data, kiedy ma zostać wysłane powiadomienie 
	 */
	public void setDateToSend(long dateToSend) {
		this.dateToSend = dateToSend;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return mail + "_" + dateToSend + "_" + content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(mail, content, dateToSend);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Reminder secondSingleReminder = (Reminder) obj;
		return Objects.equals(mail, secondSingleReminder.mail) && Objects.equals(content, secondSingleReminder.content)
				&& Objects.equals(dateToSend, secondSingleReminder.dateToSend);
	}

}
