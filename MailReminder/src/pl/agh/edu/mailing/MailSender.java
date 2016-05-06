package pl.agh .edu.mailing;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Klasa wysyłająca maile do użytkowników
 * 
 * @author ewelinarybczynska
 *
 */
public class MailSender {
	private static final String REGISTRY_SUBJECT = "Welcome Mail Reminder";
	private static final String PASSWORD_SUBJECT = "New Password Mail Reminder";
	private static MailSender instance = new MailSender();
	private Properties mailServerProperties = System.getProperties();
	private Session session;

	private MimeMessage generatedMailMessage;
	private String reminderMail;
	private String reminderPassword;

	private BodyMailGenerator bodyMailGenerator = new BodyMailGenerator();

	/**
	 * Metoda zwracająca jedyną instancję MailSendera
	 * 
	 * @return jedyna instancja MailSendera
	 */
	public static MailSender getInstance() {
		return instance;
	}

	private MailSender() {
		this.reminderMail = "mailreminderprojekt@gmail.com";
		this.reminderPassword = "mailreminder";
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		session = Session.getDefaultInstance(mailServerProperties, null);
	}

	/**
	 * Metoda wysyłająca maila z przypomnieniem
	 * 
	 * @param mail
	 *            adres e-mail, na który ma zostać wysłane powiadomienie
	 * @param content
	 *            zawartość powiadomienia
	 */
	public synchronized void sendReminderMail(String mail, String content) {
		generateMail(mail, bodyMailGenerator.makeReminderEmailBody(content), content);
		sendMail();
	}

	/**
	 * Metoda wysyłająca maila rejestracyjnego
	 * 
	 * @param mail
	 *            adres e-mail, na który ma zostać wysłane powiadomienie
	 * @param password
	 *            hasła dla nowego użytkownika
	 */
	public synchronized void sendRegistryMail(String mail, String password) {
		generateMail(mail, bodyMailGenerator.makeRegistryEmailBody(password), REGISTRY_SUBJECT);
		sendMail();
	}

	/**
	 * Metoda wysyłająca maila z nowych hasłem
	 * 
	 * @param mail
	 *            adres e-mail, na który ma zostać wysłane powiadomienie
	 * @param newPassword
	 *            nowe hasło dla użytkownika
	 */
	public synchronized void sendNewPassowordMail(String mail, String newPassword) {
		generateMail(mail, bodyMailGenerator.makeNewPasswordEmailBody(newPassword), PASSWORD_SUBJECT);
		sendMail();
	}

	private void sendMail() {
		Transport transport;
		try {
			transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", reminderMail, reminderPassword);
			transport.sendMessage(generatedMailMessage, generatedMailMessage.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void generateMail(String mail, String emailBody, String emailSubject) {
		generatedMailMessage = new MimeMessage(session);
		try {
			generatedMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
			generatedMailMessage.setSubject(emailSubject);
			generatedMailMessage.setContent(emailBody, "text/html");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}