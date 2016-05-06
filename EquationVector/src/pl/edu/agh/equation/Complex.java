package pl.edu.agh.equation;

import java.util.Objects;

public class Complex {
	private double re, im;

	public Complex() {
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public Complex(double re) {
		this.re = re;
	}

	public double getIm() {
		return im;
	}

	public void setIm(double im) {
		this.im = im;
	}

	public double getRe() {
		return re;
	}

	public void setRe(double re) {
		this.re = re;
	}

	public String toString() {
		if (im == 0) {
			return re + "";
		} else {
			if (im >= 0)
				return re + "+" + im + "i";
			else
				return re + "" + im + "i";
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(im, re);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Complex secondComplex = (Complex) obj;
		return Objects.equals(re, secondComplex.re) && Objects.equals(im, secondComplex.im);

	}
}
