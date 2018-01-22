package main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import windows.LoginWindow;

public class Server2TCPThread extends Thread {
	Socket mySocket;
	LoginWindow loginWindow;
	
	public Server2TCPThread(Socket socket) {
		super();
		mySocket = socket;
	}

	public void run()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			DBConnector dbConnector = new DBConnector();
			
			loginWindow = new LoginWindow();
			
			System.out.println("Utworzono watek na Serwerze nr.2");
			mySocket.close();
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
