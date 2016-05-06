package pl.edu.agh.vector;

import java.util.Arrays;

public class Vector {
	private double[] tab; 
	
	public Vector() { 
		
	}
	
	public Vector (int size) {
		tab = new double[size];
	}
	
	public int getSize() {
		return this.tab.length;
	}
	
	public void setValue(int index, double value) throws WrongIndexOfVector {
		if (index >= getSize()) 
			throw new WrongIndexOfVector(); 
		this.tab[index] = value; 
	}
	
	public double getValue(int index) {
		return this.tab[index]; 
	}
	
	public Vector sumOfVectors (Vector v) throws NotTheSameLengthVectors {
		if (this.tab.length != v.tab.length) 
			throw new NotTheSameLengthVectors();
		
		Vector result = new Vector(this.tab.length);
		for (int i = 0; i < this.tab.length; i++) {
			result.tab[i] = this.tab[i] + v.tab[i]; 
		}
		return result; 
	}
	
	@Override
	public String toString() {
		return Arrays.toString(tab);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(tab); 
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector secondVector = (Vector) obj;
		return Arrays.equals(tab, secondVector.tab);

	}

}
