package pl.agh.edu.h2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class JDBCTest1 {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "";

    public static void main(String[] args) throws Exception {
        // wczytanie sterownika bazy danych (z pliku h2-[wersja].jar)
        Class.forName("org.h2.Driver");
        
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);        
        // kod aplikacji
        //insertEmployee(conn, "Jan", "Kowalski");
        // wersja 2
        Date dateOfBirth = createDate(1990, 4, 21);
        insertEmployeeV2(conn,"Anna", "Nowak", dateOfBirth);        
        // zamkniecie polaczenia z baza
        Date from = createDate(2015, 11, 10);
        insertSalary(conn, 2, 2000, from);
        Date date = createDate(1991, 12, 5);
        insertEmployeeWithSalary(conn, "Andrzej", "Byk", date, 1000);
        findEmployeeBySurname(conn, "Byk");
        
        Date from1 = createDate(2015, 6, 10);
        Date from2 = createDate(2015, 8, 10);
        Date from3 = createDate(2015, 12, 10);

        Salary Salary1 = new Salary(4000, from1);
        Salary Salary2 = new Salary(5000, from2);
        Salary Salary3 = new Salary(6000, from3);

        List<Salary> listOfSalaryTest = new ArrayList<>();
        Employee testEmployee = new Employee("Jan", "Kowalski", dateOfBirth, listOfSalaryTest);
        testEmployee.addSalary(Salary1);
        testEmployee.addSalary(Salary2);
        testEmployee.addSalary(Salary3);
        
        insertEmployeeWithManySalary(conn, testEmployee);
        findEmployeeById(conn, 2071);
        conn.close();
        System.out.println("Done.");   
    }

    private static void insertEmployee(Connection dbConnection, String firstName, String surname) {
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            // uwaga - w tej wersji nie wstawiamy daty urodzenia 
            stmt.executeUpdate("insert into pracownik (imie,nazwisko) "
                    + "values( '" + firstName + "', '" + surname + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertEmployeeV2(Connection dbConnection, String firstName, String surname, Date dateOfBirth) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConnection
                  .prepareStatement("insert into pracownik (imie,nazwisko,data_urodzenia) "
                            + "values(?,?,?)");
            stmt.setString(1, firstName);
            stmt.setString(2, surname);
            stmt.setDate(3, dateOfBirth);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    private static void insertSalary(Connection dbConnection, int id_pracownika, double kwota, Date od) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConnection
                  .prepareStatement("insert into pensja (id_pracownika,kwota,od) "
                            + "values(?,?,?)");
            stmt.setInt(1, id_pracownika);
            stmt.setDouble(2, kwota);
            stmt.setDate(3, od);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void insertEmployeeWithSalary(Connection dbConnection, String firstName, String surname, 
    		Date dateOfBirth, double kwota) {
    	
        PreparedStatement stmtEmployee = null;
        PreparedStatement stmtSalary = null;
        PreparedStatement selectLastId = null;

        try {
              stmtEmployee = dbConnection
                    .prepareStatement("insert into pracownik (imie,nazwisko,data_urodzenia) "
                              + "values(?,?,?)");
              stmtEmployee.setString(1, firstName);
              stmtEmployee.setString(2, surname);
              stmtEmployee.setDate(3, dateOfBirth);
              stmtEmployee.executeUpdate();
              
              
              selectLastId = dbConnection.prepareStatement("select id from pracownik where imie=? and nazwisko=? and data_urodzenia=?");
              selectLastId.setString(1, firstName);
              selectLastId.setString(2, surname);
              selectLastId.setDate(3, dateOfBirth);
              ResultSet result = selectLastId.executeQuery();
              result.last();
              int id_pracownika = result.getInt(1);
            
              LocalDate cDate = LocalDate.now();
              Date date = createDate(cDate.getYear(), cDate.getMonthValue() -1, cDate.getDayOfMonth());
              stmtSalary = dbConnection
                    .prepareStatement("insert into pensja (id_pracownika,kwota,od) "
                              + "values(?,?,?)");
              stmtSalary.setInt(1, id_pracownika);
              stmtSalary.setDouble(2, kwota);
              stmtSalary.setDate(3, date);
              stmtSalary.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmtEmployee != null && stmtSalary != null) {
                try {
                    stmtEmployee.close();
                    stmtSalary.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // zwraca liste wszystkich pracownikow w bazie
    private static java.util.List<Employee> getEmployeeList(
            Connection dbConnection) {
        java.util.List<Employee> employeeList = new ArrayList<Employee>();

        PreparedStatement stmt = null;
        try {
            stmt = dbConnection
                    .prepareStatement("select id,imie,nazwisko,data_urodzenia from pracownik");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                // pobranie pol po numerze kolumny
                e.setId(rs.getInt(1));
                e.setName(rs.getString(2));
                // pobranie pol po nazwie kolumny
                e.setSurname(rs.getString("nazwisko"));
                e.setDateOfBirth(rs.getDate("data_urodzenia"));
                employeeList.add(e);
                // tylko dla testu
                System.out.println("Pracownik: " + e.getName() + " "
                        + e.getSurname() + " id: " + e.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return employeeList;
    }
    
    
 // wyszukuje pracownikow w bazie po nazwisku
    private static java.util.List<Employee> findEmployeeBySurname(
            Connection dbConnection, String surname) {
        java.util.List<Employee> employeeList = new ArrayList<Employee>();

        PreparedStatement stmtPracownik = null;
        PreparedStatement stmtPensja = null;

        try {
            stmtPracownik = dbConnection
                    .prepareStatement("select id,imie,nazwisko,data_urodzenia from pracownik where nazwisko like ?");
            stmtPracownik.setString(1, surname);
            ResultSet rsPracownik = stmtPracownik.executeQuery();
            while (rsPracownik.next()) {
                // tworzenie pracownika na podstawie biezacego rekordu
                Employee e = createEmployee(rsPracownik);
                employeeList.add(e);
                // tylko dla testu
                stmtPensja = dbConnection
                        .prepareStatement("SELECT KWOTA,OD FROM PENSJA WHERE ID_PRACOWNIKA=?");
                stmtPensja.setInt(1, e.getId());
                System.out.print(e.getId() + " ");
                ResultSet rsPensja = stmtPensja.executeQuery();
                while (rsPensja.next()) {
                	Salary newSalary = new Salary(rsPensja.getDouble("KWOTA"), rsPensja.getDate("OD"));
                	e.listOfSalary.add(newSalary);
                }
                
                System.out.print("Pracownik: " + e.getName() + " "
                        + e.getSurname() + " id: " + e.getId() + " ");
                
                Iterator<Salary> it = e.iterator();
                while(it.hasNext()) {
                	System.out.print(it.next() + " ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmtPracownik != null) {
                try {
                    stmtPracownik.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return employeeList;
    }
    
    private static void insertEmployeeWithManySalary(Connection dbConnection, Employee newEmployee) {
    	
        PreparedStatement stmtEmployee = null;
        PreparedStatement stmtSalary = null;
        PreparedStatement selectLastId = null;

        try {
              stmtEmployee = dbConnection
                    .prepareStatement("insert into pracownik (imie,nazwisko,data_urodzenia) "
                              + "values(?,?,?)");
              stmtEmployee.setString(1, newEmployee.getName());
              stmtEmployee.setString(2, newEmployee.getSurname());
              stmtEmployee.setDate(3, newEmployee.getDateOfBirth());
              stmtEmployee.executeUpdate();
              
              
              selectLastId = dbConnection.prepareStatement("select id from pracownik where imie=? and nazwisko=? and data_urodzenia=?");
              selectLastId.setString(1, newEmployee.getName());
              selectLastId.setString(2, newEmployee.getSurname());
              selectLastId.setDate(3, newEmployee.getDateOfBirth());
              ResultSet result = selectLastId.executeQuery();
              result.last();
              int id_pracownika = result.getInt(1);
            
              stmtSalary = dbConnection
                    .prepareStatement("insert into pensja (id_pracownika,kwota,od) "
                              + "values(?,?,?)");
              
              Iterator<Salary> it = newEmployee.iterator();
              while(it.hasNext()) {
            	  Salary salary = it.next();
                  stmtSalary.setInt(1, id_pracownika);
                  stmtSalary.setDouble(2, salary.getAmount());
                  stmtSalary.setDate(3, salary.getSince());
                  stmtSalary.executeUpdate();

              }
              
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmtEmployee != null && stmtSalary != null) {
                try {
                    stmtEmployee.close();
                    stmtSalary.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static Employee createEmployee(ResultSet rs) throws SQLException {
		Employee e = new Employee();
		e.setId(rs.getInt("ID"));
		e.setName(rs.getString("IMIE"));
		e.setSurname(rs.getString("NAZWISKO"));
		e.setDateOfBirth(rs.getDate("DATA_URODZENIA"));
		
		
		return e;
	}

	// usuniecie pracownika o okreslonym id
    private static void removeEmployee(Connection dbConnection, int employeeId) {
        PreparedStatement stmt = null;
        try {
            stmt = dbConnection
                    .prepareStatement("delete from pracownik where id=?");
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // pobieranie liczby wszystkich pracownikow w bazie
    private static int getNoOfEmployees(Connection dbConnection) {
        int noOfEmplyees = -1;

        PreparedStatement stmt = null;
        try {
            stmt = dbConnection
                    .prepareStatement("select count(*) from pracownik");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                noOfEmplyees = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return noOfEmplyees;
    }
    
    private static Employee findEmployeeById(Connection dbConnection, int id ) {
        PreparedStatement stmtEmployee = null;
        PreparedStatement stmtSalary = null;
        Employee foundEmployee = new Employee();

        try {
			stmtEmployee = dbConnection
					.prepareStatement("select id,imie,nazwisko,data_urodzenia from pracownik where id like ?");
			stmtEmployee.setInt(1, id);
			ResultSet rsEmployee = stmtEmployee.executeQuery();
			
			if (rsEmployee.next())
				foundEmployee = createEmployee(rsEmployee);
			stmtSalary = dbConnection.prepareStatement("SELECT KWOTA,OD FROM PENSJA WHERE ID_PRACOWNIKA=?");
			stmtSalary.setInt(1, foundEmployee.getId());
			System.out.println(foundEmployee.getId());
			ResultSet rsSalary = stmtSalary.executeQuery();
			while (rsSalary.next()) {
				Salary newSalary = new Salary(rsSalary.getDouble("KWOTA"), rsSalary.getDate("OD"));
				foundEmployee.listOfSalary.add(newSalary);
			}

			System.out.println("Pracownik: " + foundEmployee.getName() + " " + foundEmployee.getSurname() + " id: "
					+ foundEmployee.getId());

			Iterator<Salary> it = foundEmployee.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmtEmployee != null) {
				try {
					stmtEmployee.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return foundEmployee;
	}

    private static Date createDate(int year, int month, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day);
        Date date = new Date(calendar.getTime().getTime());
        return date;
    }   
    
}