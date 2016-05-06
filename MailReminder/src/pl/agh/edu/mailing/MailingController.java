package pl.agh.edu.mailing;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;
import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerToFile;
import pl.edu.agh.server.Server;

/**
 * Klasa kontrolująca wysyłanie maili
 * 
 * @author ewelinarybczynska
 *
 */
public class MailingController {
	private static final MailingController instance = new MailingController();
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ReminderController reminderController = ReminderController.getInstance();
	private SendMailTask lastTask = null;
	private Reminder lastReminder = null;
	private Logger logger;

	private MailingController() {
		logger = new DefaultLogger(getLoggerStrategy());
	}

	private LoggerToFile getLoggerStrategy() {
		return new LoggerToFile(Server.LOGS_DIRECTORY.resolve(getLogFileName()).toString());
	}

	private String getLogFileName() {
		return "mailingController.log";
	}

	/**
	 * Metoda zwracająca jedyną instancję MailingControllera
	 * 
	 * @return jedyna instancja mailingControllera
	 */
	public static MailingController getInstance() {
		return instance;
	}

	/**
	 * Metoda wybierająca przypomnienie do wysłania i wysyłająca je
	 * 
	 */
	public synchronized void setReminderToSend() {
		Reminder newReminder = chooseReminderToSend();
		logger.info("setReminderToSend " + newReminder);
		logger.info("setReminderToSend LAST " + lastReminder);

		if (newReminder == null)
			return;
		if (lastReminder == null) {
			scheduleSendingReminder(newReminder);
		} else {
			if (newReminder.getDateToSend() < (lastReminder.getDateToSend())) {
				scheduleSendingReminder(newReminder);
			}
		}
	}

	private Reminder chooseReminderToSend() {
		List<Reminder> reminders = reminderController.generateChronologicalListReminders();
		if (!reminders.isEmpty()) {
			logger.info("chooseReminderToSend " + reminders.get(0));
			return reminders.get(0);
		}
		return null;
	}

	private void scheduleSendingReminder(Reminder reminderToSend) {
		long duration = reminderToSend.getDateToSend() - System.currentTimeMillis();
		if (duration <= 0) {
			duration = 1;
		}
		if (lastTask != null)
			lastTask.stop();
		logger.info("scheduleSendingReminder " + duration + " reminder " + reminderToSend);
		SendMailTask task = new SendMailTask(reminderToSend, this);
		executor.schedule(task, duration, TimeUnit.MILLISECONDS);
		lastTask = task;
		lastReminder = reminderToSend;
	}

	/**
	 * Metoda ustawiająca ostanie powiadomienie jako wysłane
	 * 
	 */
	public synchronized void setLastRemindersSent() {
		lastReminder = null;
	}

	/**
	 * Metoda stopująca wysyłanie powiadomienia
	 * 
	 */
	public void stopLastTask() {
		if (lastTask != null) {
			lastTask.stop();
			logger.info("STOPPED");
		}
		lastTask = null;
		lastReminder = null;
	}

}
