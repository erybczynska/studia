import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cleaner implements Runnable {
	private static int idCounter = 0;
	private final int id = idCounter++;
	private Random random = new Random();
	private VehicleQueue vehicleQueue;

	public Cleaner(VehicleQueue vehicleQueue) {
		this.vehicleQueue = vehicleQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Vehicle vehicle = vehicleQueue.take();
				System.out.println(this + " has started washing " + vehicle);
				TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(500));
				System.out.println(this + " has washed " + vehicle);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " finished work.");
		}
	}

	@Override
	public String toString() {
		return "Cleaner #" + id;
	}

}
