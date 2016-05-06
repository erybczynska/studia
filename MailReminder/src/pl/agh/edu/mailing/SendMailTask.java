package pl.agh.edu.mailing;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa wysyłająca maile o odpowiedniej porze
 * 
 * @author ewelinarybczynska
 *
 */
public class SendMailTask implements Runnable {
	private static MailSender sender = MailSender.getInstance();
	private static ReminderController reminderController = ReminderController.getInstance();
	private Reminder reminderToSend;
	private MailingController mailingController;
	private boolean stopped = false;

	/**
	 * Konstruktor parametrowy ustawiający przypomnienie do wysłania oraz jedyną
	 * instancję mailingControllera
	 * 
	 * @param reminderToSend
	 *            przypomnienie do wysłania
	 * @param mailingController
	 *            jedyna instancja mailingControllera
	 */
	public SendMailTask(Reminder reminderToSend, MailingController mailingController) {
		this.reminderToSend = reminderToSend;
		this.mailingController = mailingController;
	}

	/**
	 * Metoda wysyłająca maile, implementująca interfejs Runnable
	 * 
	 */
	@Override
	public void run() {
		if (stopped)
			return;
		sender.sendReminderMail(reminderToSend.getMail(), reminderToSend.getContent());
		reminderController.removeReminder(reminderToSend);
		mailingController.setLastRemindersSent();
		mailingController.setReminderToSend();
	}

	/**
	 * Metoda stopująca wysyłanie
	 * 
	 */
	public void stop() {
		stopped = true;
	}

}
