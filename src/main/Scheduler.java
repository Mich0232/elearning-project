package main;


import java.io.IOException;
import java.net.*;

import handlers.Server1Handler;
import handlers.Server2Handler;

public class Scheduler {
	
	private int threadCount = 0;
	private static Server1TCP server_1;
	private static ServerTCP2 server_2;
	
	public static void main(String[] args) {
		
			int port=8013;
			ServerSocket serverSocket = null;
			
			(new Server1Handler()).start();
			(new Server2Handler()).start();
				
			try 
			{
				serverSocket = new ServerSocket(port);
				while (true) // czekanie na zgloszenie klienta
				{
					Socket socket = serverSocket.accept();
					// Sprawdz obciazenie serverow
//					(new Server1TCPThread(socket)).start(); // tworzymy watek dla polaczenia
				}
			} 
			catch (Exception e)
			{
				System.err.println(e);
			}
			finally
			{
				if(serverSocket != null)
					try {
						serverSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	

	public int getThreadCount() {
		return threadCount;
	}

}