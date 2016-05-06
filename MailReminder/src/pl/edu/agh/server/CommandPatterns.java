package pl.edu.agh.server;

import java.util.regex.Pattern;

/**
 * Klasa reprezentująca wzory komend
 * 
 * @author ewelinarybczynska
 *
 */
public class CommandPatterns {
	
	//TODO mail pattern tu 
	public static final Pattern LOGIN = Pattern
			.compile("LOGIN (\\S+) (\\S+)"); /* login hash_hasla */
	public static final Pattern CHANGE_PASSWORD = Pattern
			.compile("CHPASS (\\S+) (\\S+)"); /* stare nowe */
	public static final Pattern ADD_REMINDER = Pattern
			.compile("ADDREM (\\S+) (.+)");/* data tresc */
	public static final Pattern DELETE_ALL_REMINDERS = Pattern.compile("DELALL");
	public static final Pattern DELETE_ONE_REMINDER = Pattern
			.compile("DELREM (\\S+) (.+)"/* data tresc */);
	public static final Pattern GET_REMINDERS = Pattern.compile("GETALL");
	public static final Pattern ADD_USER = Pattern.compile("ADDUSR (\\S+)"/* mail */);
	public static final Pattern QUIT = Pattern.compile("QUIT");
	public static final Pattern FORGET_PASSWORD = Pattern.compile("FORGET (\\S+)"/* mail */);
	public static final Pattern LOGOUT = Pattern.compile("LOGOUT");
	public static final Pattern DELUSR = Pattern.compile("DELUSR");
	
	private CommandPatterns() {
	}
}
