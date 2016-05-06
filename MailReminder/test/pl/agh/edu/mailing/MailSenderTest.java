package pl.agh.edu.mailing;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.mail.imap.IMAPFolder;

public class MailSenderTest {
	private static final String testMail = "testmailreminder@gmail.com";
	private static final String password = "testmail";
	private static final String reminderMail = "mailreminderprojekt@gmail.com";
	private static final String testContent = "Wyjazd";
	private static final String newPassword = "newPass1";

	
	private static final TextMessage testReminderMessage = new TextMessage("Wyjazd", reminderMail,
			testMail, "Hello,<br>Reminder - <b>Wyjazd</b><br><br>Regards,<br>Mail Reminder");
	private static final TextMessage testReminderMessageWithParameters = new TextMessage("Wyjazd", reminderMail,
			testMail, "Hello,<br>Reminder - <b>Wyjazd</b><br><br>Regards,<br>Mail Reminder");

	private static final TextMessage testNewPasswordMessage = new TextMessage("New Password Mail Reminder", reminderMail, 
			testMail, "Hello,<br>Your new password is  - " + newPassword + "<br><br>Regards,<br>Mail Reminder");
	
	private static final TextMessage testRegistryMessage = new TextMessage("Welcome Mail Reminder", reminderMail, 
			testMail, "Hello,<br>Thank you for making an account.<br>Your passoword is - " + password + " <br>On your profile you can change password. Now you'll never miss any event!<br><br>Regards,<br>Mail Reminder");
	
	private MailSender sender = MailSender.getInstance();
	
	@Before
	public void clearInbox() throws MessagingException {
		IMAPFolder folder = null;
		Store store = null;
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");

			Session session = Session.getDefaultInstance(props, null);

			store = session.getStore("imaps");
			store.connect("imap.googlemail.com", testMail, password);

			folder = (IMAPFolder) store.getFolder("[Gmail]/All Mail");

			if (!folder.isOpen())
				folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();

			Folder trash = (IMAPFolder) store.getFolder("[Gmail]/Bin");
			for (Message m : messages) {
				folder.copyMessages(new Message[] { m }, trash);
			}
		} finally {
			if (folder != null && folder.isOpen()) {
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
		}
	}

	private static TextMessage checkInbox() throws MessagingException, IOException {
		IMAPFolder folder = null;
		Store store = null;
		TextMessage message = null;

		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");

			Session session = Session.getDefaultInstance(props, null);

			store = session.getStore("imaps");
			store.connect("imap.googlemail.com", testMail, password);

			folder = (IMAPFolder) store.getFolder("[Gmail]/All Mail");

			if (!folder.isOpen())
				folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();

			if (messages.length > 0) {
			Message msg = messages[0];
			message = new TextMessage(msg.getSubject(), msg.getFrom()[0].toString(),
					msg.getAllRecipients()[0].toString(),
					msg.getContent().toString().substring(0, msg.getContent().toString().length() - 2));
			}
		} finally {
			if (folder != null && folder.isOpen()) {
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
		}
		return message;
	}

	@Test
	public void sendingReminderMailTest() throws MessagingException, IOException {
		sender.sendReminderMail(testMail, testContent);
		TextMessage messageToCheck = checkInbox();
		System.out.println(testReminderMessage);
		System.out.println(messageToCheck);
		Assert.assertEquals(testReminderMessage, messageToCheck);
	}

	@Test
	public void sendingMailReminderTestWithParameters() throws MessagingException, IOException {
		sender.sendReminderMail(testMail, testContent);
		TextMessage messageToCheck = checkInbox();
		System.out.println(testReminderMessageWithParameters);
		System.out.println(messageToCheck);
		Assert.assertEquals(testReminderMessageWithParameters, messageToCheck);
	}

	@Test
	public void sendingRegistryMailTest() throws MessagingException, IOException {
		sender.sendRegistryMail(testMail, password);
		TextMessage messageToCheck = checkInbox();
		Assert.assertEquals(testRegistryMessage, messageToCheck);
	}
	
	@Test
	public void sendingNewPasswordMailTest() throws MessagingException, IOException {
		sender.sendNewPassowordMail(testMail, newPassword);
		TextMessage messageToCheck = checkInbox();
		Assert.assertEquals(testNewPasswordMessage, messageToCheck);
	}

}
