package models;

public class User {

	public String name;
	public String surname;
	public String accountType;
	public String group;
	public String subject;
	
	
	public User(String name, String surname, String accountType, String group, String subject) {
		this.name = name;
		this.surname = surname;
		this.accountType = accountType;
		this.group = group;
		this.subject = subject;
	}
	
	public String toString()
	{
		return name + " " + surname;
	}
	
}
