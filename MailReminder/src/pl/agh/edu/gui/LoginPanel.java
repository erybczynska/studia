package pl.agh.edu.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Klasa panelu logowania
 * 
 * @author ewelinarybczynska
 *
 */
public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(222, 222, 222);
	private static final Color TEXT_COLOR = new Color(94, 67, 78);

	private View view;
	private JButton loginButton;
	private JButton registry;
	private JButton forgetPassword;
	private JTextField mailField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JLabel mailReminder;
	private LoginController controller;

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param controller
	 */
	public void setController(LoginController controller) {
		this.controller = controller;
	}

	/**
	 * Metoda pobierająca hasło z pola hasła
	 * 
	 * @return hasło
	 */
	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	/**
	 * Metoda zerująca pole hasła
	 * 
	 */
	public void setPassword() {
		passwordField.setText("");
	}

	/**
	 * Metoda pobierają adres e-mail z pola adresu mailowego
	 * 
	 * @return adres e-mail
	 */
	public String getMail() {
		return mailField.getText();
	}

	/**
	 * Metoda zerująca pole adresu e-mail
	 * 
	 */
	public void setMail() {
		mailField.setText("");
	}

	/**
	 * Konstuktor tworzący panel
	 * 
	 * @param app
	 *            widok
	 */
	public LoginPanel(View app) {
		this.setBackground(BACKGROUND_COLOR);
		view = app;

		mailReminder = new JLabel("Mail Reminder");
		mailReminder.setForeground(TEXT_COLOR);
		mailReminder.setFont(mailReminder.getFont().deriveFont(Font.BOLD, 35));
		mailReminder.setHorizontalAlignment(SwingConstants.CENTER);
		mailReminder.setSize(400, 40);
		mailReminder.setLocation(200, 130);
		add(mailReminder);

		mailField = new JTextField("Mail adress", 15);
		mailField.setBounds(300, 200, 200, 20);
		add(mailField);

		passwordLabel = new JLabel("Enter your password:");
		passwordLabel.setBounds(300, 230, 200, 20);
		add(passwordLabel);

		passwordField = new JPasswordField("Password", 15);
		passwordField.setBounds(300, 260, 200, 20);
		add(passwordField);

		loginButton = new JButton("Log in");
		loginButton.setBounds(360, 300, 80, 20);
		add(loginButton);

		forgetPassword = new JButton("Forgot your password?");
		forgetPassword.setBounds(320, 360, 160, 20);
		add(forgetPassword);

		registry = new JButton("Registry");
		registry.setBounds(360, 400, 80, 20);
		add(registry);

		setSize(800, 600);
		setLayout(null);

		forgetPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel forgetPassPanel = new JPanel();
				forgetPassPanel.setLayout(new BoxLayout(forgetPassPanel, BoxLayout.Y_AXIS));
				forgetPassPanel.add(new JLabel("Enter your e-mail:"));
				JTextField mailField = new JTextField(5);
				forgetPassPanel.add(mailField);

				int result = JOptionPane.showConfirmDialog(LoginPanel.this, forgetPassPanel, "Change password",
						JOptionPane.OK_CANCEL_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}
				try {
					String mail = mailField.getText();
					checkMailFormat(mail);

				} catch (WrongFormatOfMail e) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Enter correct e-mail adress",
							"Incorrect e-mail adress", JOptionPane.WARNING_MESSAGE);
				}
				if (controller.sendForgetPassword(mailField.getText())) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Password has been send", "Forget passoword",
							JOptionPane.OK_OPTION);
				} else {
					JOptionPane.showMessageDialog(LoginPanel.this, "Enter correct e-mail adress",
							"This mail do not exist in our database", JOptionPane.WARNING_MESSAGE);
				}
			}

			private void checkMailFormat(String mail) throws WrongFormatOfMail {
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if (!matcher.find())
					throw new WrongFormatOfMail();
			}
		});

		registry.addActionListener(new ActionListener() {
			private String mail = "";

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel registryPanel = new JPanel();
				registryPanel.setLayout(new BoxLayout(registryPanel, BoxLayout.Y_AXIS));
				registryPanel.add(new JLabel("Enter your e-mail:"));
				JTextField mailField = new JTextField(mail, 5);
				registryPanel.add(mailField);

				int result = JOptionPane.showConfirmDialog(LoginPanel.this, registryPanel, "Registry",
						JOptionPane.OK_CANCEL_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}
				try {
					mail = mailField.getText();
					checkMailFormat(mail);

				} catch (WrongFormatOfMail e) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Enter correct e-mail adress",
							"Incorrect e-mail adress", JOptionPane.WARNING_MESSAGE);
				}
				if (controller.registryNewUser(mailField.getText())) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Your successfully made an account",
							"Correct Registry", JOptionPane.OK_OPTION);
				} else {
					JOptionPane.showMessageDialog(LoginPanel.this, "Enter correct e-mail adress",
							"This mail exists in our database", JOptionPane.WARNING_MESSAGE);
				}
			}

			private void checkMailFormat(String mail) throws WrongFormatOfMail {
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if (!matcher.find())
					throw new WrongFormatOfMail();
			}
		});

		loginButton.addActionListener(new ActionListener() {
			private String mail = "";

			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					mail = mailField.getText();
					checkMailFormat(mail);
				} catch (WrongFormatOfMail e) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Enter correct e-mail adress",
							"Incorrect e-mail adress", JOptionPane.WARNING_MESSAGE);
				}
				if (controller.login(mail, new String(passwordField.getPassword()))) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Your successfully log in", "Correct Log In",
							JOptionPane.OK_OPTION);
					view.setAccountPanel(mail);
				} else {
					JOptionPane.showMessageDialog(LoginPanel.this, "Wrong login data", "Enter correct login data",
							JOptionPane.OK_OPTION);
				}
			}

			private void checkMailFormat(String mail) throws WrongFormatOfMail {
				Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if (!matcher.find())
					throw new WrongFormatOfMail();
			}
		});

	}

}
