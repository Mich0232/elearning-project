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
	
}
