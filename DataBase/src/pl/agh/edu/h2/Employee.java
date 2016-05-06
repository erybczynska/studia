package pl.agh.edu.h2;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Employee {
    protected int id;
    protected String name;
	protected String surname;
    protected Date dateOfBirth;
    protected List<Salary> listOfSalary = new ArrayList<>();
   
    public Employee() {
    	
    }
    
    public Employee(String firstName, String surname, Date dateOfBirth, List<Salary> listOfSalary) {
    	this.name = firstName;
    	this.surname = surname; 
    	this.dateOfBirth = dateOfBirth;
    	this.listOfSalary = listOfSalary;
    }
    
    public void setListOfSalary(List<Salary> listOfSalary) {
		this.listOfSalary = listOfSalary;
	}
    
    public Iterator<Salary> iterator() {
    	return listOfSalary.iterator();
    }
    
    public void addSalary(Salary newSalary) {
    	if (listOfSalary == null)
    		listOfSalary = new ArrayList<>();
    	listOfSalary.add(newSalary);
    }
    
	public int getId() {
        return id;
    }
	
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }    
    
    @Override 
    public String toString() {
    	return name + " " + surname + " " + dateOfBirth + " " + listOfSalary.toString();
    }
    
	@Override
	public int hashCode() {
		return Objects.hash(name, surname, dateOfBirth, listOfSalary);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Employee secondEmployee = (Employee) obj;
		return Objects.equals(name, secondEmployee.name) && Objects.equals(surname, secondEmployee.surname)
				&& Objects.equals(dateOfBirth, secondEmployee.dateOfBirth)
				&& Objects.equals(listOfSalary, secondEmployee.listOfSalary);
	}
}
