package dal.model;

import java.util.Date;

public class Tournament {

	private int id;
	private String name;
	private Date startDate;
	private boolean isPaid;
	private Match m1;
	private Match m2;
	private Match m3;
	private Match m4;
	private Match m5;
	private Match m6;
	private Match m7;
	
	public int getId() {
		
		return this.id;
	}
	
	public void setId(int toBeSet) {
		
		this.id = toBeSet;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public void setName(String N) {
		
		this.name = N;
	}
	public Date getStartDate() {
		
		return this.startDate;
	}
	
	public void setStartDate(Date D) {
		
		this.startDate = D;
	}
	
	public boolean getIsPaid() {
		
		return this.isPaid;
	}
	
	public void setIsPaid(boolean i) {
		
		this.isPaid = i;
	}
	public Match getM1() {
		
		return this.m1;
	}
	
	public void setM1(Match M) {
		
		this.m1 = M;
	}
	
	public Match getM2() {
		
		return this.m2;
	}
	
	public void setM2(Match M) {
		
		this.m2 = M;
	}
	
	public Match getM3() {
		
		return this.m3;
	}
	
	public void setM3(Match M) {
		
		this.m3 = M;
	}
	
	public Match getM4() {
		
		return this.m4;
	}
	
	public void setM4(Match M) {
		
		this.m4 = M;
	}
	
	public Match getM5() {
		
		return this.m5;
	}
	
	public void setM5(Match M) {
		
		this.m5 = M;
	}
	
	public Match getM6() {
		
		return this.m6;
	}
	
	public void setM6(Match M) {
		
		this.m6 = M;
	}
	
	public Match getM7() {
		
		return this.m7;
	}
	
	public void setM7(Match M) {
		
		this.m7 = M;
	}
	
	public String toString() {
		
		String result = Integer.toString(id) + ": " + name + " " + Boolean.toString(isPaid);
		if(m1!=null)
			result = result + "\n" + m1.toString();
		if(m2!=null)
			result = result + "\n" + m2.toString();
		if(m3!=null)
			result = result + "\n" + m3.toString();
		if(m4!=null)
			result = result + "\n" + m4.toString();
		if(m5!=null)
			result = result + "\n" + m5.toString();
		if(m6!=null)
			result = result + "\n" + m6.toString();
		if(m7!=null)
			result = result + "\n" + m7.toString();
		return result;
	}
}
