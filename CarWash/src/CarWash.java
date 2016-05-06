import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CarWash {
	private static final int PRODUCERS = 3;
	private static final int CONSUMERS = 2;

	public static void main(String[] args) {
		VehicleQueue vehicleQueue = new VehicleQueue();
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<?>> futures = new ArrayList<>();

		for (int i = 0; i < PRODUCERS; ++i) {
			futures.add(executor.submit(new CarProvider(vehicleQueue)));
		}
		for (int i = 0; i < CONSUMERS; ++i) {
			futures.add(executor.submit(new Cleaner(vehicleQueue)));
		}

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		futures.forEach(future -> future.cancel(true));
		executor.shutdownNow();
		
		System.out.println();
		System.out.println("END");
	}
}
