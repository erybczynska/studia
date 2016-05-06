import java.util.HashSet;
import java.util.Set;

public class Base {
	public static void main(String[] args) {
		
		String name = "I can do it"; 
		name.replace("can", "cannot");
		name.replaceAll("can.*", "will");
		System.out.println(name);
	}

	
}
