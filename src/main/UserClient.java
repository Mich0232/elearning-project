package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.UUID;

import models.Task;
import models.User;
import windows.LoginWindow;

public class UserClient {
	
	private static LoginWindow loginW;
	Socket socket;
	User currentUser;
	ArrayList<Kolokwium> kolos;
	
	public UserClient()
	{
		int port=8013;
		boolean stop = false;
		try
		{
			socket = new Socket("127.0.0.1", port);
			socket.setTcpNoDelay(true);
			
			BufferedReader serverAnswer;
			serverAnswer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String portNum;
			portNum = serverAnswer.readLine();
			System.out.println(portNum);
					
			socket.close();
			
			socket = new Socket("127.0.0.1", Integer.parseInt(portNum));
			socket.setTcpNoDelay(true);
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}
	}
	
	public void closeConnection()
	{

			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	public void sendTask(Task task){
		String control = "sendTask";
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(control);
				out.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ObjectOutputStream sendtask;
			sendtask = new ObjectOutputStream(socket.getOutputStream());
			sendtask.writeObject(task);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void addTest(Kolokwium test){
		
		String control = "addTest";
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(control);
				out.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ObjectOutputStream sendtest;
			sendtest = new ObjectOutputStream(socket.getOutputStream());
			sendtest.writeObject(test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ArrayList<Kolokwium> getTest(String TestId){
		ArrayList<Kolokwium> test = new ArrayList<>();
		
		String control = "getTest";
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(control);
				out.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			PrintWriter testid = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				testid.println(TestId);
				testid.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ObjectInputStream gettest = new ObjectInputStream(socket.getInputStream());
			test = (ArrayList<Kolokwium>)gettest.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Kolokwium proba = test.get(1);
		System.out.println(proba.pyt);
		
		return test;
	}
	
		
	public void sendUserData(User curuser){
		this.currentUser = curuser;
		
		String control = "sendUserData";
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(control);
				out.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			ObjectOutputStream userdata;
			userdata = new ObjectOutputStream(socket.getOutputStream());
			userdata.writeObject(currentUser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
	}
	
	public void sendMessage(Message msg){
		String control = "sendMessage";
		try{
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
				out.println(control);
				out.flush();
				
			}catch(IOException e) {
			 // TODO Auto-generated catch block
				System.err.println(e);
			}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ObjectOutputStream sendmsg;
			sendmsg = new ObjectOutputStream(socket.getOutputStream());
			sendmsg.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	public static void main(String args[]) 
//	{
//		int port=8013;
		//clientPanel = new TeacherPanel(NIU.toString());
		
		//loginW = new LoginWindow();
//		try
//		{
//			Socket socket = new Socket("127.0.0.1", port);
//			socket.setTcpNoDelay(true);
//			
//			socket.close();
//		}
//		catch (Exception e) 
//		{
//			System.err.println(e);
//		}
		
//		DBConnector dbConnector = new DBConnector();
//		loginW = new LoginWindow();
//		
//				
//	}
	

}