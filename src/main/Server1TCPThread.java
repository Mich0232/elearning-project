package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

import models.Task;
import models.User;
import windows.LoginWindow;

public class Server1TCPThread extends Thread {
	public static int currentThreads = 0;
	
	Socket mySocket;
	String control;
	User currentUser;
	String work;
//	LoginWindow loginWindow;
	
	public Server1TCPThread(Socket socket) {
		super();
		mySocket = socket;
		DBConnector dbConnector = new DBConnector();
	}

	@Override
	public void run()
	{
		Server1TCPThread.currentThreads++;
		try
		{
			Scanner sc = new Scanner(System.in);
//			DBConnector 	
			
			System.out.println("Utworzono watek na Serwerze nr.1");
		
		//*********************TRE��**************************************
		
		control = "work";
						
		while(!control.equals("exit")){	
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));						
			control = in.readLine();
			
			switch(control){
			case "sendTask":
				ObjectInputStream gettask = new ObjectInputStream(mySocket.getInputStream());						
				Task task = (Task)gettask.readObject();
				DBConnector.addTask(task);			
				break;
			case "AddTest":
				
				break;
			case "sendUserData":
				try {

					ObjectInputStream userdata = new ObjectInputStream(mySocket.getInputStream());						
					currentUser = (User)userdata.readObject();
					
					System.out.println(currentUser.name);
					} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println(e);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;
			case "sendMessage":
				ObjectInputStream getmsg = new ObjectInputStream(mySocket.getInputStream());						
				Message msg = (Message)getmsg.readObject();
				DBConnector.addMessage(msg.idReceiver, msg.idSender, msg.subject, msg.content);			
				break;
				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
			
			
			
			
		//****************************************************************	
			mySocket.close();
			Server1TCPThread.currentThreads--;
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
