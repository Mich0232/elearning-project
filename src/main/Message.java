package main;

import java.io.Serializable;

public class Message implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3130248087232374200L;
	int idReceiver;
	int idSender;
	String subject;
	String content;
	
	public Message(int ir, int is, String sub, String con){
		this.idReceiver = ir;
		this.idSender = is;
		this.subject = sub;
		this.content = con;
		
	}
	
	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

	public int getIdSender() {
		return idSender;
	}

	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
