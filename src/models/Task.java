package models;

import java.io.Serializable;

public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6871346322661614931L;
	public String content;
	public int UID; // Teacher id
	public String group;
	
	public Task(String cont, int UID, String group){
		this.content = cont;
		this.UID = UID;
		this.group = group;
	}
	
	public String toString()
	{
		return String.format("%s ...", this.content.substring(0, (this.content.length() < 8 ? this.content.length() : 8)));
	}
	
	public String getContent()
	{
		return this.content;
	}
	
}
