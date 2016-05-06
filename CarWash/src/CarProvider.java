import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CarProvider implements Runnable {
	private static int idCounter = 0;
	private final int id = idCounter++;
	private VehicleQueue vehicleQueue;
	private Random random = new Random();

	public CarProvider(VehicleQueue vehicleQueue) {
		this.vehicleQueue = vehicleQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(1000 + random.nextInt(1000));
				int randomIndex = random.nextInt(VehicleType.values().length);
				Vehicle vehicle = new Vehicle(VehicleType.values()[randomIndex]);
				vehicleQueue.put(vehicle);
				System.out.println(this + " has provided " + vehicle);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " finished work.");
		}
	}

	@Override
	public String toString() {
		return "Car provider #" + id;
	}

}
