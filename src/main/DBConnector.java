package main;

import models.Task;
import models.User;
import models.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		
		User loggedUser = new User(user_id, name, surname, accountType, group, subject);		
		
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
	
	public static boolean addTask(Task task)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO `elf_task`(`Content`, `ID_Teacher`, `Group`) VALUES ('"+task.content+"','"+task.UID+"','"+task.group+"')");
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	
	// Return list of Task's for given group
	public static List<Task> getTasks(String group_id)
	{
		List<Task> taskList = new ArrayList<Task>();
		
		Statement s = createStatement(connection);
		String selection = String.format("call getTasks('%s');", group_id);
		ResultSet r; 
		
		try {
		r = executeQuery(s, selection);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		
		try {
			while(r.next())
			{
				taskList.add(new Task((String)r.getObject(1), r.getInt(2), (String)r.getObject(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return taskList;
	}
	
	public static List<Task> getTeacherTasks(String idTeacher)
	{
		List<Task> taskList = new ArrayList<Task>();
		
		Statement s = createStatement(connection);
		ResultSet r = executeQuery(s, "Select elf_task.ID_Task, elf_task_ans.Content, elf_task.Group "
				+ "from elf_task_ans JOIN elf_task ON elf_task.ID_Task=elf_task_ans.ID_Task where elf_task.ID_Teacher='"+idTeacher+"';");
		
		try {
			while(r.next())
			{
				taskList.add(new Task((String)r.getObject(2), Integer.parseInt(idTeacher), (String)r.getObject(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<taskList.size(); i++)
			System.out.println(i+") "+taskList.get(i));
		return taskList;
	}
	
	public static ArrayList<String> getGrades(int idStudent)
	{
		ArrayList<String> grades = new ArrayList<>();
		Statement s = createStatement(connection);
		ResultSet r = executeQuery(s, "SELECT ID_Test, Grade from elf_test_ans where ID_Student='"+idStudent+"';");
		
		try {
			while(r.next())
			{
				grades.add("Test#"+r.getObject(1).toString()+" "+(String)r.getObject(2).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s = createStatement(connection);
		r = executeQuery(s, "SELECT ID_Task, Grade from elf_task_ans where ID_Student='"+idStudent+"';");
		
		try {
			while(r.next())
			{
				grades.add("Zadanie#"+r.getObject(1)+" "+r.getObject(2).toString()+"\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grades;
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
	
	
	public static ArrayList<Kolokwium> getTest(int idTest)
	{
		ArrayList<Kolokwium> questions = new ArrayList<>();
		System.out.println("ZGARNIAM PYTANKA");
		Statement s = createStatement(connection);
		ResultSet rpyt = executeQuery(s, "SELECT ID_Question, Content from elf_questions where ID_Test="+idTest+";");
		//
		
		String pytanie;
		String idPytania;
		String[] odp = new String[5];
		String[] odpcorrect = new String[5];
		String correct;
		
		
		
		try {
			while(rpyt.next())
			{
				idPytania = rpyt.getString(1);
				pytanie = rpyt.getString(2);
				Statement s2 = createStatement(connection);
				ResultSet rodp = executeQuery(s2, "SELECT Content, IsCorrect from elf_answers where ID_Question="+idPytania+";");
				int i = 0;
				correct = null;
				while(rodp.next()){
					i++;
					odp[i] = rodp.getString(1);
					odpcorrect[i] = String.valueOf(rodp.getInt(2)); 
					if(Integer.parseInt(odpcorrect[i]) == 1)
						correct = String.valueOf(i);
				}
				questions.add(new Kolokwium(idPytania, null, pytanie, odp[1], odp[2], odp[3], odp[4], correct));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return questions;
	}
	
	public static boolean addMessage(int receiverID, int senderID, String subject, String content)
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
	
	public static boolean sendTaskAns(int idStudent, String question, String ans)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO `elf_task_ans`(`ID_Student`, `ID_Task`, `Content`, `Grade`) "
					+ "VALUES ('"+idStudent+"', (Select ID_Task from elf_task where Content='"+question+"')  , '"+ans+"','')");
		}
		catch (Exception e)
		{
			success=false;
		}
		
		return success;
	}
	
	public static boolean sendTestAns(int idStudent, String question, String ans)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT INTO `elf_test_ans`(`ID_Student`, `ID_Test`, `Grade`) "
					+ "VALUES ('"+idStudent+"',[value-2],[value-3])");
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