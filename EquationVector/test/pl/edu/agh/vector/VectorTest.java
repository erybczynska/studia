package pl.edu.agh.vector;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	@Test
	public void getSizeOfVectorTest() {
		Assert.assertEquals(new Vector(3).getSize(), 3);
	}
	
	@Test
	public void setValueTest() throws WrongIndexOfVector {
		Vector testVector = new Vector(5);
		testVector.setValue(2, 7);
		Assert.assertEquals(testVector.getValue(2), 7, 1e-6);
	}
	
	@Test (expected = WrongIndexOfVector.class) 
	public void setValueWithWrongIndexTest() throws WrongIndexOfVector {
		Vector testVector = new Vector(3);
		testVector.setValue(6, 2);	
	}

	@Test
	public void getValueTest() throws WrongIndexOfVector {
		Vector testVector = new Vector(2);
		testVector.setValue(0, 9);
		testVector.setValue(1, 4);
		Assert.assertEquals(testVector.getValue(1), 4, 1e-6);
	}

	@Test
	public void sumOfVectorTest() throws NotTheSameLengthVectors, WrongIndexOfVector {
		Vector vectorToAdd = new Vector(2);
		Vector testVector = new Vector(2);
		Vector resultToCheck = new Vector(2);

		testVector.setValue(0, 6);
		testVector.setValue(1, 4);

		vectorToAdd.setValue(0, 2);
		vectorToAdd.setValue(1, 3);

		resultToCheck.setValue(0, 8);
		resultToCheck.setValue(1, 7);

		Assert.assertEquals(resultToCheck, testVector.sumOfVectors(vectorToAdd));

	}
	
	@Test (expected = NotTheSameLengthVectors.class)
	public void sumOfVectorWithWrongLengthOfVectorToAddTest() throws NotTheSameLengthVectors, WrongIndexOfVector {
		Vector testVector = new Vector(1);
		Vector vectorToAdd = new Vector(2);


		testVector.setValue(0, 6);

		vectorToAdd.setValue(0, 2);
		vectorToAdd.setValue(1, 3);
		
		testVector.sumOfVectors(vectorToAdd);
	}


}
