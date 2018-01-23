package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
					
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	

									
				server1_threads = Server1TCPThread.currentThreads;
				server2_threads = Server2TCPThread.currentThreads;
//				System.out.println(server1_threads);
//				System.out.println(server2_threads);
					
				if(server1_threads <= server2_threads)
					out.println("8014");
				else
					out.println("8015");
				
				out.flush();
										
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