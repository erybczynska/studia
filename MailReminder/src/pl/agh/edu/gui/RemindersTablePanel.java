package pl.agh.edu.gui;

import java.awt.Color;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pl.edu.agh.database.Reminder;

/**
 * Klasa reprezentująca panel tabeli powiadomień
 * 
 * @author ewelinarybczynska
 *
 */
public class RemindersTablePanel extends JPanel {
	private static final String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	private static final long serialVersionUID = -4183587440577870378L;
	private static final Color BACKGROUND_COLOR = new Color(222, 222, 222);
	private DefaultTableModel tableModel;
	private JTable table;
	
	/**
	 * Konstruktor tworzący tabele
	 * 
	 */
	public RemindersTablePanel() {
		init();
		this.setBackground(BACKGROUND_COLOR);
	}

	/**
	 * Metoda dodająca listę powiadomień do tabeli
	 * 
	 * @param reminders
	 *            lista powiadomień
	 */
	public void setRemindersInTable(List<Reminder> reminders) {
		clearTable();
		int counter = 1;
		for (Reminder reminder : reminders) {
			
			String[] row = new String[] { Integer.toString(counter), reminder.getContent(),
					FORMATTER.format(new Date(reminder.getDateToSend())) };
			tableModel.addRow(row);
			counter++;
		}
	}

	/**
	 * Metoda pobierająca z danego wiersza zawartość powiadomienia
	 * 
	 * @param number
	 *            numer wiersza
	 * @return zawartość powiadomienias
	 */
	public String getContentFormTable(int number) {
		return tableModel.getValueAt(number - 1, 1).toString();

	}

	/**
	 * Metoda pobierająca z danego wiersza datę, kiedy powiadomienie ma zostać
	 * wybrane
	 * 
	 * @param number
	 *            numer wiersza
	 * @return data, kiedy powiadomienie ma zostać wysłane
	 */
	public String getTimeToSendFromTable(int number) {
		return tableModel.getValueAt(number - 1, 2).toString();
	}

	/**
	 * Metoda sprawdzająca czy tabela jest pusta
	 * 
	 * @return zwraca prawdę gdy jest pusta, w przeciwnym razie zwraca fałsz
	 */
	public boolean isEmpty() {
		if (tableModel.getRowCount() == 0)
			return true;
		return false;
	}

	/**
	 * Metoda czyszcząca tabelę
	 * 
	 */
	public void clearTable() {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
	}

	private void init() {
		String[] columnNames = { "Number", "Content", "Time to send" };

		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = -770305322592474809L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		table = new JTable(tableModel);
		table.setLocation(0, 0);
		table.setSize(getWidth(), getHeight());
		table.getTableHeader().setReorderingAllowed(false);
		table.setCellSelectionEnabled(false);

		add(table);
		add(new JScrollPane(table));
	}

}
