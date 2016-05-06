package pl.agh.edu.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

/**
 * Klasa panelu użytkownika
 * 
 * @author ewelinarybczynska
 *
 */
public class AccountPanel extends JPanel {
	private static final String HELLO_STRING = "Hello, ";
	private static final long serialVersionUID = 8877698739498970189L;
	private static final Color BACKGROUND_COLOR = new Color(222, 222, 222);
	private static final Color TEXT_COLOR = new Color(94, 67, 78);
	private static final int labelWidth = 400;
	private static final int labelHeight = 45;
	private static final int buttonWidth = 200;
	private static final int buttonHeight = 55;
	private JLabel userMailLabel;
	private JButton changePassword;
	private JButton logout;
	private JButton manageReminders;
	private JButton deleteAccount;
	private AccountController controller;
	private RemindersPanel remindersPanel;

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param accountController
	 */
	public void setController(AccountController accountController) {
		this.controller = accountController;
		remindersPanel.setController(accountController.getRemindersController());
	}

	/**
	 * Konstruktor tworzący panel
	 * 
	 * @param view
	 *            widok
	 */
	public AccountPanel(final View view) {
		setLayout(null);
		this.setBackground(BACKGROUND_COLOR);

		int height = 140;

		userMailLabel = new JLabel();
		userMailLabel.setForeground(TEXT_COLOR);
		userMailLabel.setFont(userMailLabel.getFont().deriveFont(Font.BOLD, 20));
		userMailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userMailLabel.setSize(labelWidth, labelHeight);
		userMailLabel.setLocation(getLeftMarginLabel(userMailLabel), height);
		add(userMailLabel);

		changePassword = new JButton("Change your password");
		changePassword.setSize(buttonWidth, buttonHeight);
		changePassword.setLocation(getLeftMarginButton(changePassword), height + 80);
		add(changePassword);

		manageReminders = new JButton("Manage your reminders");
		manageReminders.setSize(buttonWidth, buttonHeight);
		manageReminders.setLocation(getLeftMarginButton(manageReminders), height + 150);
		add(manageReminders);

		logout = new JButton("Logout");
		logout.setSize(buttonWidth, buttonHeight);
		logout.setLocation(getLeftMarginButton(logout), height + 250);
		add(logout);

		deleteAccount = new JButton("Delete account");
		deleteAccount.setSize(buttonWidth, buttonHeight);
		deleteAccount.setLocation(getLeftMarginButton(deleteAccount), height + 330);
		add(deleteAccount);

		remindersPanel = new RemindersPanel(this);
		remindersPanel.setVisible(false);
		add(remindersPanel);

		changePassword.addActionListener(new ActionListener() {
			private String currentPass = "";
			private String newPass = "";

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel changePassPanel = new JPanel();
				changePassPanel.setLayout(new BoxLayout(changePassPanel, BoxLayout.Y_AXIS));
				changePassPanel.add(new JLabel("Current:"));
				JPasswordField currentPassField = new JPasswordField(5);
				changePassPanel.add(currentPassField);
				changePassPanel.add(new JLabel("Password must be at least 8 characters"));
				changePassPanel.add(new JLabel("New:"));
				JPasswordField newPassField = new JPasswordField(5);
				changePassPanel.add(newPassField);
				changePassPanel.add(new JLabel("Retype new:"));
				JPasswordField retypeNewPassField = new JPasswordField(5);
				changePassPanel.add(retypeNewPassField);

				int result = JOptionPane.showConfirmDialog(AccountPanel.this, changePassPanel, "Change password",
						JOptionPane.OK_CANCEL_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}
				try {
					currentPass = new String(currentPassField.getPassword());
					newPass = new String(newPassField.getPassword());
					String retypeNewPass = new String(retypeNewPassField.getPassword());

					checkPassowrd(newPass);
					checkNewPasswords(newPass, retypeNewPass);

				} catch (WrongFormatOfPassword e) {
					JOptionPane.showMessageDialog(AccountPanel.this, "Enter correct new password (at least 8 signs)",
							"Incorrect length of new password", JOptionPane.WARNING_MESSAGE);
				} catch (NotTheSameNewaPasswords e) {
					JOptionPane.showMessageDialog(AccountPanel.this,
							"You must enter the same password twice in order to confirm it.", "Passwords do not match",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (controller.changePassword(currentPass, newPass)) {
					JOptionPane.showMessageDialog(AccountPanel.this, "You successfully changed password",
							"Change Password", JOptionPane.OK_OPTION);
				} else {
					JOptionPane.showMessageDialog(AccountPanel.this, "You have to enter correct current password.",
							"Wrong current password", JOptionPane.WARNING_MESSAGE);
				}
			}

			private void checkPassowrd(String password) throws WrongFormatOfPassword {
				if (password.length() < 8)
					throw new WrongFormatOfPassword();
			}

			private void checkNewPasswords(String newPass, String retypeNewPass) throws NotTheSameNewaPasswords {
				if (!newPass.equals(retypeNewPass)) {
					throw new NotTheSameNewaPasswords();
				}
			}
		});

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if (controller.logout()) {
					view.logout();
				}
			}
		});

		manageReminders.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				showRemindersPanel();
			}
		});

		deleteAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(AccountPanel.this,
						"Are you sure you want to delete your account?", "Delete account",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					if (controller.deleteUser()) {
						JOptionPane.showMessageDialog(AccountPanel.this, "Account have been saccessfully deleted",
								"Delete account", JOptionPane.OK_OPTION);
						view.logout();
					} else {
						JOptionPane.showMessageDialog(AccountPanel.this, "Account havn't been saccessfully deleted",
								"Delete account", JOptionPane.WARNING_MESSAGE);
					}
				}
			}

		});
	}

	private int getLeftMarginButton(JButton button) {
		return 400 - (button.getWidth() / 2);
	}

	private int getLeftMarginLabel(JLabel label) {
		return 400 - (label.getWidth() / 2);
	}

	public void setMail(String mail) {
		userMailLabel.setText(HELLO_STRING + " " + mail);
	}

	private void showRemindersPanel() {
		setAllButtonsVisible(false);
		remindersPanel.setVisible(true);
		remindersPanel.refresh();
	}

	public void showButtons() {
		setAllButtonsVisible(true);
	}

	private void setAllButtonsVisible(boolean visible) {
		userMailLabel.setVisible(visible);
		changePassword.setVisible(visible);
		logout.setVisible(visible);
		manageReminders.setVisible(visible);
		deleteAccount.setVisible(visible);
	}

}
