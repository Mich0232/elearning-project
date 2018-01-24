package models;

public class TaskAnswer {

	private static final long serialVersionUID = 1917119959709579408L;
	public String answer;
	public int sender_id;
	public int task_id;
		
	public TaskAnswer(String answer, int sender_id, int task_id)
	{
		this.answer = answer;
		this.sender_id = sender_id;
		this.task_id = task_id;
	}
	
	public String toString()
	{
		return String.valueOf(this.sender_id);
	}
	
	public String getContent()
	{
		return this.answer;
	}

}
