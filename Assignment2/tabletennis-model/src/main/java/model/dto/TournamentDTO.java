package model.dto;

import java.util.Calendar;
import java.util.Date;
import model.modules.MatchModule;
public class TournamentDTO {


	private static final int FINISHED = 0, ONGOING = 1, UPCOMING = 2;
	
	private int id;
	private String name;
	private Date date;
	private boolean isPaid;
	private MatchDTO match1;
	private MatchDTO match2;
	private MatchDTO match3;
	private MatchDTO match4;
	private MatchDTO match5;
	private MatchDTO match6;
	private MatchDTO match7;
	
	public TournamentDTO(String name, Date d, boolean is) {
		
		this.name = name;
		this.date = d;
		this.isPaid = is;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public MatchDTO getMatch1() {
		return match1;
	}

	public void setMatch1(MatchDTO match1) {
		this.match1 = match1;
	}

	public MatchDTO getMatch2() {
		return match2;
	}

	public void setMatch2(MatchDTO match2) {
		this.match2 = match2;
	}

	public MatchDTO getMatch3() {
		return match3;
	}

	public void setMatch3(MatchDTO match3) {
		this.match3 = match3;
	}

	public MatchDTO getMatch4() {
		return match4;
	}

	public void setMatch4(MatchDTO match4) {
		this.match4 = match4;
	}

	public MatchDTO getMatch5() {
		return match5;
	}

	public void setMatch5(MatchDTO match5) {
		this.match5 = match5;
	}

	public MatchDTO getMatch6() {
		return match6;
	}

	public void setMatch6(MatchDTO match6) {
		this.match6 = match6;
	}

	public MatchDTO getMatch7() {
		return match7;
	}

	public void setMatch7(MatchDTO match7) {
		this.match7 = match7;
	}
	
	public int classifyTournament() {
		
	int flag = -1;
		
		if(this.getDate().compareTo(Calendar.getInstance().getTime())>=0)
				flag = UPCOMING;
		else {
			
			if(match7==null)
				flag = ONGOING;
			else {
				int[] x = (new MatchModule()).getMatchScore(match7);
				System.out.println(name + " " + x[0] + " " + x[1]);
				if(x[0]==3||x[1]==3)
					flag = FINISHED;
				else
					flag = ONGOING;
			}
		}
		return flag;
	}
	
}
