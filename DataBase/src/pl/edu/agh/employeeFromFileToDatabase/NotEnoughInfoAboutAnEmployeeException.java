package pl.edu.agh.employeeFromFileToDatabase;

public class NotEnoughInfoAboutAnEmployeeException extends Exception {

	private static final long serialVersionUID = 2952732102316494605L;

	public NotEnoughInfoAboutAnEmployeeException() {
		super("Za mało informacji o pracowniku!");
	}
}
