package pl.edu.agh.kis;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilTest {
	
	AnnouncementGenerator myLang = new AnnouncementGenerator() {
		@Override
		public String getInfoWrongLengthOfSides() {
			return "length";
		}
		@Override
		public String getInfoScalene() {
			return "scalene";
		}
		@Override
		public String getInfoIsosceles() {
			return "isosceles";
		}
		@Override
		public String getInfoEquilateral() {
			return "equilateral";
		}
	};

	@Test
	public void specifyTypeOfTriangleWrongData() {
		String result = MathUtil.specifyTypeOfTriangle(1, 2, 3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
	@Test
	public void specifyTypeOfTriangleRightData() {
		String result = MathUtil.specifyTypeOfTriangle(3, 4, 5, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoScalene(), result);
	}
	
	@Test
	public void EquilateralTriangleRightData() {
		String result = MathUtil.specifyTypeOfTriangle(5, 5, 5, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoEquilateral(), result);
	}
	
	@Test
	public void IsoscelesTriangleRightData() {
		String result = MathUtil.specifyTypeOfTriangle(6, 5, 6, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoIsosceles(), result);
	}
	
	@Test
	public void FirstPermutationOfSides() {
		String result = MathUtil.specifyTypeOfTriangle(3, 4, 3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoIsosceles(), result);
	}
	
	@Test
	public void SecondPermutationOfSides() {
		String result = MathUtil.specifyTypeOfTriangle(3, 4, 3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoIsosceles(), result);
	}
	
	@Test
	public void ThirdPermutationOfSides() {
		String result = MathUtil.specifyTypeOfTriangle(3, 3, 4, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoIsosceles(), result);
	}
	
	@Test
	public void TriangleWithZero() {
		String result = MathUtil.specifyTypeOfTriangle(4, 0, 3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
	@Test
	public void TriangleWithZeros() {
		String result = MathUtil.specifyTypeOfTriangle(0, 0, 0, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
	@Test
	public void TriangleWithNiegative() {
		String result = MathUtil.specifyTypeOfTriangle(4, 6, -3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
	@Test
	public void TriangleWithSideWhichIsSumOfAnother() {
		String result = MathUtil.specifyTypeOfTriangle(1, 2, 3, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
	@Test
	public void TriangleWithWrongData() {
		String result = MathUtil.specifyTypeOfTriangle(12, 15, 30, myLang.getInfoEquilateral(),
				myLang.getInfoIsosceles(), myLang.getInfoScalene(), myLang.getInfoWrongLengthOfSides());
		Assert.assertEquals(myLang.getInfoWrongLengthOfSides(), result);
	}
	
}
