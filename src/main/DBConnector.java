package main;

import models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	
	static java.sql.Connection connection;
	
	public static boolean checkDriver(String driver) {
		// LADOWANIE STEROWNIKA
		System.out.print("Sprawdzanie sterownika:");
		try {
			Class.forName(driver).newInstance();
			return true;
		} catch (Exception e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			return false;
		}
	}
	
	public static Connection connectToDatabase(String kindOfDatabase, String adress,
			String dataBaseName, String userName, String password) {
		String baza = kindOfDatabase + adress + "/" + dataBaseName;
		// objasnienie opisu bazy:
		// jdbc: - mechanizm laczenia z baza (moze byc inny, np. odbc)
		// mysql: - rodzaj bazy
		// adress - adres serwera z baza (moze byc tez z nazwy)
		// dataBaseName - nazwa bazy
		java.sql.Connection connection = null;
		try {
			connection = DriverManager.getConnection(baza, userName, password);
		} catch (SQLException e) {
			System.out.println("Blad przy połączeniu z bazą danych!");
			System.exit(1);
		}
		return connection;
	}
	
	private static Statement createStatement(Connection connection) {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Błąd createStatement! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(3);
		}
		return null;
	}

	private static void closeConnection(Connection connection, Statement s) {
		try {
			s.close();
			connection.close();
		} catch (SQLException e) {
			System.out
					.println("Bląd przy zamykaniu polączenia z bazą! " + e.getMessage() + ": " + e.getErrorCode());;
			System.exit(4);
		}
		//System.out.print(" zamknięcie OK");
	}

	private static ResultSet executeQuery(Statement s, String sql) {
		try {
			return s.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return null;
	}
	private static int executeUpdate(Statement s, String sql) {
		try {
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return -1;
	}
	
	// Get User data ; null is password is not correct
	public static User checkLogin(String login, String password)
	{
		System.out.println("Sprawdzam dane do logowania");
		String accountType="";
		int user_id = -1;
		String name = null;
		String surname = null;
		String group = null;
		String subject = null;	
		
		Statement s = createStatement(connection);
		ResultSet r = executeQuery(s, "Select type, user_id from elf_users where Login='"+login+"' AND Password='"+password+"';");
		
		try {
			if(r.next())
			{
				accountType = (String)r.getObject(1);
				user_id = r.getInt(2);
			}
		}
		catch (SQLException e) {
			System.out.println("Nie ma takiego konta albo zle wprowadzono dane! " + e.getMessage() + ": " + e.getErrorCode());
			return null;
		}
		
		System.out.println("User id: " + user_id);
		// Get user data from DB
		if(accountType.equals("Student"))
		{
			String selection = String.format("call getStudentData(%s)", user_id);
			r = executeQuery(s, selection);
			try {
				if(r.next())
				{
					name = (String)r.getObject(1);
					surname = (String)r.getObject(2);
					group = (String)r.getObject(3);
					subject = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else
		{
			String selection = String.format("call getTeacherData(%s)", user_id);
			r = executeQuery(s, selection);
			try {
				if(r.next())
				{
					name = (String)r.getObject(1);
					surname = (String)r.getObject(2);
					group = null;
					subject = (String)r.getObject(3);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		User loggedUser = new User(name, surname, accountType, group, subject);		
		
		return loggedUser;
	}
	
	public static boolean addUser(String login, String password, String name, String surname, String group, String type)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO elf_users (Login, Password, Type) VALUES ('"+login+"','"+password+"','"+type+"');");
			if(type=="Student")
			{
				executeUpdate(s, "INSERT INTO `elf_student`(`ID_User`, `Name`, `Surname`, `StudentGroup`) "
						+ "VALUES ((SELECT User_ID from elf_users where Login ='"+login+"'),'"+name+"','"+surname+"','"+group+"');");
			}
			else if(type=="Teacher")
			{
				executeUpdate(s, "INSERT INTO elf_teacher (Login, Password, Type) VALUES ('"+login+"','"+password+"','"+type+"');");
				
				executeUpdate(s, "INSERT INTO `elf_teacher`(`ID_User`, `Name`, `Surname`, `Subject`) "
						+ "VALUES ((SELECT User_ID from elf_users where Login ='"+login+"'),'"+name+"','"+surname+"','"+group+"');");
			}
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	
	public static boolean addTask(String teacherID, String content, String group)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO `elf_task`(`Content`, `ID_Teacher`, `Group`) VALUES ('"+content+"','"+teacherID+"','"+group+"')");
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	

	public static boolean addTest(String teacherID, String kolokwiumID, String group, String question, String ans1, String ans2, String ans3, String ans4, String correct)

	{
		System.out.println("DODAJE TEST");
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			// DODAJ TEST JESLI GO NIE MA
			executeUpdate(s, "INSERT IGNORE INTO `elf_test`(`ID_Test`, `ID_Teacher`, `Group`) VALUES ('"+kolokwiumID+"','"+teacherID+"','"+group+"')");
		
			// DODAJ PYTANIE
			executeUpdate(s, "INSERT INTO `elf_questions`(`ID_Test`, `Content`) "
					+ "VALUES ('"+kolokwiumID+"','"+question+"')");
			
			int[] ans = new int[4];
			ans[Integer.parseInt(correct)-1]=1;
			
			// DODAJ ODPOWIEDZI DO PYTANIA
			executeUpdate(s, "INSERT INTO `elf_answers`(`ID_Question`, `Content`, `IsCorrect`) "
					+ "VALUES ((SELECT ID_Question from elf_questions where Content ='"+question+"'),'"+ans1+"','"+ans[0]+"')");
			
			executeUpdate(s, "INSERT INTO `elf_answers`(`ID_Question`, `Content`, `IsCorrect`) "
					+ "VALUES ((SELECT ID_Question from elf_questions where Content ='"+question+"'),'"+ans2+"','"+ans[1]+"')");
			
			executeUpdate(s, "INSERT INTO `elf_answers`(`ID_Question`, `Content`, `IsCorrect`) "
					+ "VALUES ((SELECT ID_Question from elf_questions where Content ='"+question+"'),'"+ans3+"','"+ans[2]+"')");
			
			executeUpdate(s, "INSERT INTO `elf_answers`(`ID_Question`, `Content`, `IsCorrect`) "
					+ "VALUES ((SELECT ID_Question from elf_questions where Content ='"+question+"'),'"+ans4+"','"+ans[3]+"')");
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	
	public static boolean addMessge(String receiverID, String senderID, String subject, String content)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO `elf_message`(`ID_receiver`, `ID_sender`, `subject`, `content`) "
					+ "VALUES ('"+receiverID+"','"+senderID+"','"+subject+"','"+content+"')");
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	
	
	public DBConnector()
	{
		try {
			Initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Initialize() throws SQLException {
		
		//*** sprawdzanie sterownika
		if (checkDriver("com.mysql.jdbc.Driver"))
			System.out.println(" ... OK");
		else
			System.exit(1);
		connection = connectToDatabase("jdbc:mysql://", "limitlessgames.pl", "limitlessgames", "limitlessgames", "toniehaslo765");
		if (connection != null)
			System.out.print(" polaczenie OK\n");
	}
}