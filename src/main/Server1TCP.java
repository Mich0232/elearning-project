package main;


import java.io.IOException;
import java.net.*;

public class Server1TCP {
	
	private int threadCount = 0;
	
	public Server1TCP() {
		
			int port=8014;
			ServerSocket serverSocket = null;
			try 
			{
				serverSocket = new ServerSocket(port);
				while (true) // czekanie na zgloszenie klienta
				{
					Socket socket = serverSocket.accept();
					(new Server1TCPThread(socket)).start(); // tworzymy watek dla polaczenia
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