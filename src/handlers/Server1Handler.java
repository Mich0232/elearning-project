package handlers;

import main.Server1TCP;

public class Server1Handler extends Thread {

	private static Server1TCP server_1;
		
	// Thread
	@Override
	public void run() {
		Server1Handler.setServer_1(new Server1TCP());
	}

	
	// Get / Set
	public static Server1TCP getServer_1() {
		return server_1;
	}

	private static void setServer_1(Server1TCP server_1) {
		Server1Handler.server_1 = server_1;
	}

}
