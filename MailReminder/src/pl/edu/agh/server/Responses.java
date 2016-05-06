package pl.edu.agh.server;

/** Klasa reprezentująca odpowiedzi serwera
 * 
 * @author ewelinarybczynska
 *
 */
public class Responses {
	public static final String COMMAND_SUCCESSFUL_200 = "200 Command succesful";
	public static final String BYE_221 = "221 Bye";
	public static final String USER_LOGGED_IN_230 = "230 User logged in";
	public static final String LOGOUT_SUCCESSFUL_240 = "240 Logout Succesful";
	public static final String WRONG_MAIL_OR_PASSWORD_430 = "430 Invalid username or password";
	public static final String ALREADY_LOGGED_IN_503 = "503 User already logged in";
	public static final String USER_EXIST_505 = "505 User already exists";
	public static final String NOT_LOGGED_IN_530 = "530 Not logged in";
	public static final String WRONG_SYNTAX_540 = "540 Wrong Syntax";
	public static final String USER_NOT_LOGGED_IN_550 = "550 User not logged in";

	
	private Responses() {}
}
