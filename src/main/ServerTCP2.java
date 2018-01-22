package main;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ServerTCP2 {
	
	public ServerTCP2() {
		
			int port=8013;
			ServerSocket serverSocket = null;
			try
			{
				serverSocket = new ServerSocket(port);
				while (true) // czekanie na zgloszenie klienta
				{
					Socket socket = serverSocket.accept();
					(new Server2TCPThread(socket)).start(); // tworzymy watek dla polaczenia
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
}