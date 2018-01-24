package main;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Kolokwium implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1528831372011562356L;
	public String id, group;
	public String pyt;
	public String odp1, odp2, odp3, odp4;
	public String poprawnaOdp;
	
	public Kolokwium(String id, String group, String pyt, String odp1, String odp2, String odp3, String odp4, String poprawna){
		this.id = id;
		this.group = group;
		this.pyt = pyt;
		this.odp1=odp1;
		this.odp2 = odp2;
		this.odp3 = odp3;
		this.odp4 = odp4;
		poprawnaOdp=poprawna;
	}

}
