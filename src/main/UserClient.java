package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.UUID;
import windows.LoginWindow;

public class UserClient {
	
	private static LoginWindow loginW;
	Socket socket;
	public UserClient()
	{
		int port=8013;
		try
		{
			socket = new Socket("127.0.0.1", port);
			socket.setTcpNoDelay(true);
			
			socket.close();
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	
	public void sendTask(String task){
		
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(task);
				out.flush();
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
	}
	
public void AddTest(Kolokwium test){
		
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(test);
				out.flush();
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
	}
	
	
	
	
	
	
	
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
		
		DBConnector dbConnector = new DBConnector();
		loginW = new LoginWindow();
		
				
	}
	
//	private static void readAndAnswer(BufferedReader in, PrintWriter out, UUID NIU) throws IOException, InterruptedException
//	{
//		String str;
//
//		//*** CZYTAJ PYTANIE Z SOCKETA
//		while (!(str = in.readLine()).equals("exit"))
//		{
//			//clientPanel.setQuestionOnPanel(str);
//			//int ans = clientPanel.SendAnswerToServer();
//			//out.println(ans + "#" + NIU);
//			//out.flush();
//		}
//	}

}