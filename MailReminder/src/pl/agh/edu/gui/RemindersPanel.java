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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Klasa reprezentująca panel powiadomień
 * 
 * @author ewelinarybczynska
 *
 */
public class RemindersPanel extends JPanel {

	private static final long serialVersionUID = -3951608277515726841L;
	private static final Color TEXT_COLOR = new Color(94, 67, 78);
	private RemindersController controller;
	private JLabel reminders;
	private JButton addReminder;
	private JButton deleteAll;
	private JButton deleteOne;
	private JButton account;
	private JButton refresh;
	private AccountPanel accountPanel;
	private RemindersTablePanel tablePanel;

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param remindersController
	 */
	public void setController(RemindersController remindersController) {
		this.controller = remindersController;
	}

	/**
	 * Kontruktor tworzący panel
	 * 
	 * @param accountPanel
	 */
	public RemindersPanel(AccountPanel accountPanel) {
		this.accountPanel = accountPanel;
		setLayout(null);
		setLocation(20, 20);
		setSize(800, 600);

		this.setBackground(accountPanel.getBackground());

		reminders = new JLabel("Your reminders");
		reminders.setForeground(TEXT_COLOR);
		reminders.setFont(reminders.getFont().deriveFont(Font.BOLD, 20));
		reminders.setHorizontalAlignment(SwingConstants.CENTER);
		reminders.setSize(200, 20);
		reminders.setLocation(300, 2);
		add(reminders);

		addReminder = new JButton("Add new reminder");
		addReminder.setBounds(300, 40, 200, 40);
		add(addReminder);

		account = new JButton("Return to account manager");
		account.setBounds(300, 90, 200, 40);
		add(account);

		deleteOne = new JButton("Delete one reminder");
		deleteOne.setBounds(300, 140, 200, 40);
		add(deleteOne);

		deleteAll = new JButton("Delete all reminders");
		deleteAll.setBounds(300, 190, 200, 40);
		add(deleteAll);

		refresh = new JButton("Refresh reminders");
		refresh.setBounds(300, 240, 200, 40);
		add(refresh);

		tablePanel = new RemindersTablePanel();
		tablePanel.setBounds(50, 300, 650, 140);
		add(tablePanel);

		addReminder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel addNewReminderPanel = new JPanel();
				addNewReminderPanel.setLayout(new BoxLayout(addNewReminderPanel, BoxLayout.Y_AXIS));
				addNewReminderPanel.add(new JLabel("Content:"));
				JTextField contentField = new JTextField(5);
				addNewReminderPanel.add(contentField);
				addNewReminderPanel.add(new JLabel("Time to send (format - yyyy-MM-dd HH:mm:ss)"));
				addNewReminderPanel.add(new JLabel("For example 2007-12-03 10:15:30"));
				JTextField dateField = new JTextField(5);
				addNewReminderPanel.add(new JLabel("If the date of sending will be passed reminder will be sent immediately."));
				addNewReminderPanel.add(dateField);
				

				int result = JOptionPane.showConfirmDialog(RemindersPanel.this, addNewReminderPanel, "Add New Reminder",
						JOptionPane.OK_CANCEL_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}

				String content = contentField.getText();
				String timeToSend = dateField.getText();
				
				Result resultAnswer = controller.addNewReminder(timeToSend, content);
				if (resultAnswer == Result.OK) {
					JOptionPane.showMessageDialog(RemindersPanel.this, "Reminder has been saccessfully added",
							"Add new reminder", JOptionPane.INFORMATION_MESSAGE);
					refresh();
				} else if (resultAnswer == Result.WRONG_DATE) {
					JOptionPane.showMessageDialog(RemindersPanel.this, "Reminder hasn't been saccessfully added, date of sending was be passed.",
							"Add new reminder", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(RemindersPanel.this, "Reminder hasn't been saccessfully added",
							"Add new reminder", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		deleteAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				int result = JOptionPane.showConfirmDialog(RemindersPanel.this,
						"Are you sure you want to delete all your reminders?", "Delete all reminders",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					if (!tablePanel.isEmpty()) {
						if (controller.deleteAllReminders()) {
							refresh();
							JOptionPane.showMessageDialog(RemindersPanel.this,
									"Reminders have been saccessfully deleted", "Delete all reminders",
									JOptionPane.OK_OPTION);
						} else {
							JOptionPane.showMessageDialog(RemindersPanel.this,
									"Reminders haven't been saccessfully deleted", "Delete all reminders",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});

		deleteOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JPanel deleteReminderPanel = new JPanel();
				deleteReminderPanel.setLayout(new BoxLayout(deleteReminderPanel, BoxLayout.Y_AXIS));
				deleteReminderPanel.add(new JLabel("Number of reminder:"));
				JTextField numberField = new JTextField(5);
				deleteReminderPanel.add(numberField);

				int result = JOptionPane.showConfirmDialog(RemindersPanel.this, deleteReminderPanel, "Delete single reminder",
						JOptionPane.OK_CANCEL_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}

				int number = Integer.parseInt(numberField.getText());
				String content = tablePanel.getContentFormTable(number);
				String timeToSend = tablePanel.getTimeToSendFromTable(number);

				if (controller.deleteOneReminder(timeToSend, content)) {
					refresh();
					JOptionPane.showMessageDialog(RemindersPanel.this, "Reminder has been saccessfully deleted",
							"Delete one reminder", JOptionPane.OK_OPTION);
				} else {
					JOptionPane.showMessageDialog(RemindersPanel.this, "Reminder hasn't been saccessfully deleted",
							"Delete one reminder", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		account.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				returnAccountPanel();
			}
		});

		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				refresh();
			}
		});

	}

	private void returnAccountPanel() {
		setVisible(false);
		accountPanel.showButtons();
	}

	/**
	 * Metoda odświeżająca tabelę powiadomień
	 * 
	 */
	public void refresh() {
		tablePanel.setRemindersInTable(controller.makeRemindersList());
	}

}
