package pl.edu.agh.Server;

public class Main {
	
	public static void main(String[] args) {
		short port = 0;
		if (!correctArgs(args)) {
			exit();
		} else {
			try {
				port = Short.parseShort(args[0]);
			} catch (NumberFormatException e) {
				exit();
			}
		}
		new Thread(new Server(port)).start();
	}

	private static boolean correctArgs(String[] args) {
		if (args.length != 1) {
			return false;
		}
		return true;
	}
	
	private static void exit() {
		System.out.println("Użycie: java Main port");
		System.exit(1);
	}

}
