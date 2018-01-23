package main;


import java.io.IOException;
import java.net.*;

public class ServerTCP2 {
	
	public ServerTCP2() {
		
			int port=8015;
			ServerSocket serverSocket = null;
			try
			{
				serverSocket = new ServerSocket(port);
				System.out.println("Server 2 running...");
				while (true) // czekanie na zgloszenie klienta
				{
					Socket socket = serverSocket.accept();
					(new Server2TCPThread(socket)).start(); // tworzymy watek dla polaczenia
				}
			}
			catch (Exception e) 
			{
				System.out.println("Server 2 - ERROR");
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
}