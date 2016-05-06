
public class Vehicle {
	private static int idCounter = 0;
	private final int id = idCounter++;
	private final VehicleType type;
	
	public Vehicle(VehicleType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public VehicleType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type + ", id=" + id;
	}
	
}
