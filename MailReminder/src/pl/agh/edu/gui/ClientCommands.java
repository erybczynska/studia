package pl.agh.edu.gui;

/**
 * Klasa reprezentująca komendy ze strony klienta
 * 
 * @author ewelinarybczynska
 *
 */
public class ClientCommands {
	public static final String FORGET_PASSWORD = "FORGET %s";
	public static final String REGISTRY = "ADDUSR %s";
	public static final String LOGIN = "LOGIN %s %s";
	public static final String CHANGE_PASSWORD = "CHPASS %s %s";
	public static final String LOGOUT = "LOGOUT";
	public static final String ADD_REMINDER = "ADDREM %s %s";
	public static final String GET_ALL_REMINDERS = "GETALL";
	public static final String DELETE_ALL_REMINDERS = "DELALL";
	public static final String DELETE_ONE_REMINDER = "DELREM %s %s";
	public static final String DELETE_USER = "DELUSR";

	private ClientCommands() {
	}
}
