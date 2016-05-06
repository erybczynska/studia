package pl.agh.edu.h2;

import java.sql.Date;
import java.util.Objects;

public class Salary {
	private Date since; 
	private double amount;
	private int id = -1;

	public Salary(double amount, Date since) {
		this(-1, amount, since);
	}
	
	public Salary(int id, double amount, Date since) {
		this.setAmount(amount);
		this.setSince(since);
		this.setId(id);
	}


	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(since, amount);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Salary secondSalary = (Salary) obj;
		return Objects.equals(since, secondSalary.since) && Objects.equals(amount, secondSalary.amount);
	}

	@Override
	public String toString() {
		return amount+","+since;
	}
}
