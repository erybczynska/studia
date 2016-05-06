package pl.edu.agh.equation;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {
	
	@Test
	public void setRePartInConstructorTest() {
		Complex testComplex = new Complex(10, 6); 
		Assert.assertEquals(testComplex.getRe(), 10, 1e-5);
	}
	
	@Test
	public void setImPartInConstructorTest() {
		Complex testComplex = new Complex(10, 6); 
		Assert.assertEquals(testComplex.getIm(), 6, 1e-5);
	}
	
	@Test
	public void setRePartInReConstruktorTest() { 
		Complex testComplex = new Complex(8); 
		Assert.assertEquals(testComplex.getRe(), 8, 1e-5);
	}

	@Test
	public void setImPartTest() {
		Complex testComplex = new Complex(); 
		testComplex.setIm(4);
		Assert.assertEquals(testComplex.getIm(), 4, 1e-5);
	}

	@Test
	public void setRePartTest() {
		Complex testComplex = new Complex(); 
		testComplex.setRe(6);
		Assert.assertEquals(testComplex.getRe(), 6, 1e-5);
	}
	
	@Test
	public void getImPartTest() {
		Complex testComplex = new Complex(3, 7); 
		Assert.assertEquals(testComplex.getIm(), 7, 1e-5);
	}
	
	@Test
	public void getRePartTest() {
		Complex testComplex = new Complex(2, 1); 
		Assert.assertEquals(testComplex.getRe(), 2, 1e-5);
	}
	
	@Test 
	public void complexToStringWithImPartNegativeTest() { 
		Complex testComplex = new Complex(5, -9); 
		Assert.assertEquals("5.0-9.0i", testComplex.toString());
	}
	
	@Test 
	public void complexToStringWithImPartPositiveTest() { 
		Complex testComplex = new Complex(5, 9); 
		Assert.assertEquals("5.0+9.0i", testComplex.toString());
	}
	
	@Test 
	public void complexToStringWithOnlyRePartTest() { 
		Complex testComplex = new Complex(5); 
		Assert.assertEquals("5.0", testComplex.toString());
	}
	
	@Test 
	public void hashCodeComplexTest() { 
	
	}
	
}
