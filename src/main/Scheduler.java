package main;


import java.io.IOException;
import java.net.*;

import handlers.Server1Handler;
import handlers.Server2Handler;

public class Scheduler {
	
	static int server1_threads, server2_threads;
	
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
					server1_threads = Server1TCPThread.currentThreads;
					server2_threads = Server2TCPThread.currentThreads;
					
					if(server1_threads <= server2_threads)
						(new Server1TCPThread(socket)).start(); 
					else
						(new Server2TCPThread(socket)).start(); 
					
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