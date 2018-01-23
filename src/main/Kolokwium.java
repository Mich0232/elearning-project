package main;

public class Kolokwium {

	private String pyt;
	private String odp1, odp2, odp3, odp4;
	
	public Kolokwium(String pyt, String odp1, String odp2, String odp3, String odp4){
		this.setPyt(pyt);
		this.setOdp1(odp1);
		this.setOdp2(odp2);
		this.setOdp3(odp3);
		this.setOdp4(odp4);
	}

	public String getPyt() {
		return pyt;
	}

	public void setPyt(String pyt) {
		this.pyt = pyt;
	}

	public String getOdp1() {
		return odp1;
	}

	public void setOdp1(String odp1) {
		this.odp1 = odp1;
	}
	
	public String getOdp2() {
		return odp2;
	}

	public void setOdp2(String odp2) {
		this.odp2 = odp2;
	}

	public String getOdp3() {
		return odp3;
	}

	public void setOdp3(String odp3) {
		this.odp3 = odp3;
	}


	public String getOdp4() {
		return odp4;
	}

	public void setOdp4(String odp4) {
		this.odp4 = odp4;
	}
	
}
