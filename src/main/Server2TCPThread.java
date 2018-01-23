package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import windows.LoginWindow;

public class Server2TCPThread extends Thread {
	
	public static int currentThreads = 0;
	
	Socket mySocket;
	LoginWindow loginWindow;
	
	public Server2TCPThread(Socket socket) {
		super();
		mySocket = socket;
	}

	@Override
	public void run()
	{
		Server2TCPThread.currentThreads++;
		try
		{
			Scanner sc = new Scanner(System.in);
//			DBConnector dbConnector = new DBConnector();
			
			System.out.println("Utworzono watek na Serwerze nr.2");
			
			while(true) {
				if (Server1TCPThread.currentThreads > 5)
					break;
			}
			
			mySocket.close();
			Server2TCPThread.currentThreads++;
		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	private static void askAndWaitForAnswer(PrintWriter out, BufferedReader in) throws IOException
	{
    	/*int i=0;
		while(i<questions.size())
		{
			String str = questions.get(i);
			out.println(str);
			out.flush();
			while (!(str = in.readLine()).equals("exit")) // czekaj na odpowiedz i zapisz wynik do bazy
			{
					String[] parts = str.split("#");
			    	DBConnector.sendAnswer((i+1), Integer.parseInt(parts[0]), parts[1]);
			    	break;
			}
			i++;
		}
		out.println("exit");
		out.flush();*/
	}
	
   
}
