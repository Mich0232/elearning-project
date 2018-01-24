package models;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public int UID;
	public String name;
	public String surname;
	public String accountType;
	public String group;
	public String subject;
	
	
	
	public User(String name, String surname, String accountType, String group, String subject) {
		//this.UID = UID;
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
