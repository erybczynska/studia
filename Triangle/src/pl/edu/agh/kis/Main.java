package pl.edu.agh.kis;

public class Main {
	
	public static void main(String[] args) {
		AnnouncementGenerator gen = new PolishAnnouncementGenerator();
		MathUtil.specifyTypeOfTriangle(1, 2, 3, gen.getInfoEquilateral(), gen.getInfoIsosceles(), 
				gen.getInfoScalene(), gen.getInfoWrongLengthOfSides());
		gen = new EnglishAnnouncementGenerator();
		MathUtil.specifyTypeOfTriangle(1, 2, 3, gen.getInfoEquilateral(), gen.getInfoIsosceles(), 
				gen.getInfoScalene(), gen.getInfoWrongLengthOfSides());
	}
}
