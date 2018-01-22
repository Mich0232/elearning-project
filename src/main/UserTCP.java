package main;


import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.UUID;
import windows.LoginWindow;

public class UserTCP {
	
	private static LoginWindow loginW;
	
	public static void main(String args[]) 
	{
		int port=8013;
		//clientPanel = new TeacherPanel(NIU.toString());
		
		//loginW = new LoginWindow();
		try
		{
			Socket socket = new Socket("127.0.0.1", port);
			socket.setTcpNoDelay(true);
			
			socket.close();
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	private static void readAndAnswer(BufferedReader in, PrintWriter out, UUID NIU) throws IOException, InterruptedException
	{
		String str;

		//*** CZYTAJ PYTANIE Z SOCKETA
		while (!(str = in.readLine()).equals("exit"))
		{
			//clientPanel.setQuestionOnPanel(str);
			//int ans = clientPanel.SendAnswerToServer();
			//out.println(ans + "#" + NIU);
			//out.flush();
		}
	}

}