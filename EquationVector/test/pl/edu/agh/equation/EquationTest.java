package pl.edu.agh.equation;

import org.junit.Assert;
import org.junit.Test;

public class EquationTest {

	@Test
	public void setAtest() {
		Equation testEquation = new Equation();
		testEquation.setA(2);
		Assert.assertEquals(testEquation.getA(), 2, 1e-5);
	}

	@Test
	public void setBtest() {
		Equation testEquation = new Equation();
		testEquation.setB(3);
		Assert.assertEquals(testEquation.getB(), 3, 1e-5);
	}

	@Test
	public void setCtest() {
		Equation testEquation = new Equation();
		testEquation.setC(4);
		Assert.assertEquals(testEquation.getC(), 4, 1e-5);
	}

	@Test
	public void getAtest() {
		Equation testEquation = new Equation(5, 6, 7);
		Assert.assertEquals(testEquation.getA(), 5, 1e-5);
	}

	@Test
	public void getBtest() {
		Equation testEquation = new Equation(5, 6, 7);
		Assert.assertEquals(testEquation.getB(), 6, 1e-5);
	}

	@Test
	public void getCtest() {
		Equation testEquation = new Equation(5, 6, 7);
		Assert.assertEquals(testEquation.getC(), 7, 1e-5);
	}

	@Test
	public void solveEquationWithNegativeDeltaTest() {
		Equation testEquation = new Equation(1, -4, 5);
		Assert.assertArrayEquals(new Complex[] { new Complex(2.0, 1.0), new Complex(2.0, -1.0) },
				testEquation.solveEquation());
	}

	@Test
	public void solveEquationWithZeroDeltaTest() {
		Equation testEquation = new Equation(1, -6, 9);
		Assert.assertArrayEquals(new Complex[] { new Complex(3.0) }, testEquation.solveEquation());
	}

	@Test
	public void solveEquationWithPossitiveDeltaTest() {
		Equation testEquation = new Equation(1, -8, 15);
		Assert.assertArrayEquals(new Complex[] { new Complex(5.0), new Complex(3.0) }, testEquation.solveEquation());
	}
}
