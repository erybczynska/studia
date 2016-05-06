package pl.edu.agh.equation;

import static java.lang.Math.sqrt;

public class Equation {
	private double a, b, c;

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
	public Equation() {
		
	}

	public Equation(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public double calculateDelta() {
		return b * b - 4 * a * c;
	}

	public Complex[] solveEquation() {
		double delta = calculateDelta();
		Complex[] results;
		if (delta == 0) {
			results = new Complex[1];
			results[0] = new Complex(-b / (2 * a));
		} else if (delta > 0) {
			results = new Complex[2];
			results[0] = new Complex((-b + sqrt(delta)) / (2 * a));
			results[1] = new Complex((-b - sqrt(delta)) / (2 * a));
		} else {
			results = new Complex[2];
			results[0] = new Complex(-b / (2 * a), sqrt(-delta) / (2 * a));
			results[1] = new Complex(-b / (2 * a), -sqrt(-delta) / (2 * a));
		}
		return results;
	}
	
}