package main;


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
	
	public static String checkLogin(String login, String password)
	{
		System.out.println("Sprawdzam dane do logowania");
		String accountType="";
		Statement s = createStatement(connection);
		ResultSet r = executeQuery(s, "Select type from elf_users where Login='"+login+"' AND Password='"+password+"';");
		
		try {
			if(r.next())
			{
				accountType = (String)r.getObject(1);
			}
		}
		catch (SQLException e) {
			System.out.println("Nie ma takiego konta albo zle wprowadzono dane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		
		return accountType;
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
	
	public static boolean addTest(String teacherID, String kolokwiumID, String group, String question, String ans1, String ans2, String ans3, String ans4)
	{
		boolean success = true;
		Statement s = createStatement(connection);
		try
		{
			executeUpdate(s, "INSERT IGNORE INTO `elf_test`(`ID_Test`, `ID_Teacher`, `Group`) VALUES ('"+kolokwiumID+"','"+teacherID+"','"+group+"')");
			
			executeUpdate(s, "INSERT INTO `elf_questions`(`ID_Question`, `ID_Test`, `Content`) "
					+ "VALUES ((SELECT count(*) from elf_questions where ID_Test='"+kolokwiumID+"',[value-2],[value-3])");
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