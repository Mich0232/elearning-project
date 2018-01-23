package handlers;

import main.ServerTCP2;

public class Server2Handler extends Thread {

	private static ServerTCP2 server_2;
	
	
	// Thread
	@Override
	public void run() {
		Server2Handler.setServer_2(new ServerTCP2());
	}

	// Get / Set
	public static ServerTCP2 getServer_2() {
		return server_2;
	}


	public static void setServer_2(ServerTCP2 server_2) {
		Server2Handler.server_2 = server_2;
	}

}
