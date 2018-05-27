package model.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import dal.factory.DaoFactory;
import dal.interfaces.MatchDAOInterface;
import dal.interfaces.PaidTournamentDAOInterface;
import dal.interfaces.PlayerDAOInterface;
import dal.interfaces.TournamentDAOInterface;
import dal.model.Match;
import dal.model.PaidTournament;
import dal.model.Player;
import dal.model.Tournament;
import model.dto.PaidTournamentDTO;
import model.dto.TournamentDTO;

public class TournamentModule extends Observable {

	
	private static final int FINISHED = 0, ONGOING = 1, UPCOMING = 2;
	private static final int ENROLLED = 0, NOT_ENROLLED = 1;
	
	
	private TournamentDAOInterface tournamentDAO;
	private PlayerDAOInterface playerDAO;
	private MatchDAOInterface matchDAO;
	private PaidTournamentDAOInterface pTournamentDAO;
	private PaidTournamentDTO currentTournament;
	
	
	private static TournamentModule INSTANCE;
	
	public TournamentModule() {
		
		DaoConfiguration config = new DaoConfiguration();
		config.setFactory();
		tournamentDAO = config.getFactory().getTournamentDao();
		playerDAO =  config.getFactory().getPlayerDao();
		matchDAO =  config.getFactory().getMatchDao();
		pTournamentDAO =  config.getFactory().getPaidTournamentDao();
	}

	public TournamentDTO convertRepresentation(Tournament t) {
		
		MatchModule matchHelper = new MatchModule();
		TournamentDTO tD = new TournamentDTO(t.getName(), t.getStartDate(), t.getIsPaid());
		if(t.getM1()!=null) {
			
			tD.setMatch1(matchHelper.convertRepresentation(t.getM1()));
		}
		if(t.getM2()!=null) {
			
			tD.setMatch2(matchHelper.convertRepresentation(t.getM2()));
		}
		if(t.getM3()!=null) {
			
			tD.setMatch3(matchHelper.convertRepresentation(t.getM3()));
		}
		if(t.getM4()!=null) {
			
			tD.setMatch4(matchHelper.convertRepresentation(t.getM4()));
		}
		if(t.getM5()!=null) {
			
			tD.setMatch5(matchHelper.convertRepresentation(t.getM5()));
		}
		if(t.getM6()!=null) {
			
			tD.setMatch6(matchHelper.convertRepresentation(t.getM6()));
		}
		if(t.getM7()!=null) {
			
			tD.setMatch7(matchHelper.convertRepresentation(t.getM7()));
		}
		
		return tD;
	}
	public void createNewTournament(String tournamentName, String day, String month, String year, boolean isPaid, float fee) {
		
		Tournament t = new Tournament();
		t.setName(tournamentName);
		t.setIsPaid(isPaid);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateInString = day + "-" + month + "-" + year;
		Date date;
		try {
			date = sdf.parse(dateInString);
			t.setStartDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tournamentDAO.insert(t);
		if(isPaid==true) {
			PaidTournament p = new PaidTournament();
			p.setFee(fee);
			Tournament nt = tournamentDAO.findByAnything("name", tournamentName);
			p.setTournament(nt);
			pTournamentDAO.insert(p);
			
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void deleteTournament(String name) {
		
		Tournament t = tournamentDAO.findByAnything("name", name);
		if(t.getIsPaid()==true) {
			PaidTournament p = pTournamentDAO.findById(t.getId());
			pTournamentDAO.delete(p.getId());
		}
		tournamentDAO.delete(t.getId());
		this.setChanged();
		this.notifyObservers();
	}
	
		public void updateTournament(String newName, String newday, String newmonth, String newyear, String fee) {
		
			Tournament t = tournamentDAO.findByAnything("name",currentTournament.getT().getName());
			t.setName(newName);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString = newday + "-" + newmonth + "-" + newyear;
			Date date = null;
			try {
				date = sdf.parse(dateInString);
				t.setStartDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println(date);
			t.setStartDate(date);
			System.out.println(t.getStartDate());
			tournamentDAO.update(t);
			if(currentTournament.getT().isPaid()==true) {
				
				PaidTournament p = pTournamentDAO.findById(t.getId());
				p.setTournament(t);
				p.setFee(Float.parseFloat(fee));
				pTournamentDAO.update(p);
			} else {
				
				if(fee.equals("0")==false) {
					
					PaidTournament p = new PaidTournament();
					t.setIsPaid(true);
					p.setFee(Float.parseFloat(fee));
					p.setTournament(t);
					pTournamentDAO.insert(p);
				}
			}
			this.setChanged();
			this.notifyObservers();
		}
	public ArrayList<PaidTournamentDTO> getAllTournaments() {
		
		ArrayList<Tournament> listOfTournaments = tournamentDAO.getAllTournaments();
		ArrayList<PaidTournament> listOfPaid = pTournamentDAO.getAllTournaments();
		ArrayList<PaidTournamentDTO> transferredList = new ArrayList<PaidTournamentDTO>();
		for(Tournament tournament:listOfTournaments) {
			
			if(tournament.getIsPaid()==false) {
			TournamentDTO t = convertRepresentation(tournament);
			PaidTournamentDTO p = new PaidTournamentDTO(0);
			p.setT(t);
			transferredList.add(p);
			}
		}
		for(PaidTournament tournament: listOfPaid) {
			
			PaidTournamentDTO p = new PaidTournamentDTO(tournament.getFee());
			TournamentDTO t = convertRepresentation(tournament.getTournament());
			p.setT(t);
			transferredList.add(p);
		}
		return transferredList;
	}
	
	public void setCurrentTournament(String name) {
		
		Tournament tournament = tournamentDAO.findByAnything("name", name);
		if(tournament.getIsPaid()==false) {
			TournamentDTO t = convertRepresentation(tournament);
			PaidTournamentDTO p = new PaidTournamentDTO(0);
			p.setT(t);
			currentTournament = p;
		} else {
			PaidTournament t = pTournamentDAO.findById(tournament.getId());
			PaidTournamentDTO p = new PaidTournamentDTO(t.getFee());
			TournamentDTO tr = convertRepresentation(tournament);
			p.setT(tr);
			currentTournament = p;
		}
	}
	
	public void setTournamentNull() {
		
		currentTournament = null;
	}
	
	public PaidTournamentDTO getCurrentTournament() {
		
		return currentTournament;
	}
	
	private void sortAlphabetical(ArrayList<PaidTournamentDTO> array) {
		
		Collections.sort(array, new AComparator());
	}
	
	private void sortByType(ArrayList<PaidTournamentDTO> array) {
		
		Collections.sort(array, new TComparator());
	}
	
	public ArrayList<PaidTournamentDTO> seeBy(int CHOICE_CODE) {
		
		ArrayList<PaidTournamentDTO> all = this.getAllTournaments();
		if(CHOICE_CODE==0) {
			
			sortAlphabetical(all);
		}
		if(CHOICE_CODE==1) {
			
			sortByType(all);
		}
		return all;
	}
	
	private int getNextPlayerPosition(Tournament t) {
		
		Match[] matches = new Match[7];
		matches[0] = t.getM1();
		matches[1] = t.getM2();
		matches[2] = t.getM3();
		matches[3] = t.getM4();
		matches[4] = t.getM5();
		matches[5] = t.getM6();
		matches[6] = t.getM7();
		int i;
		for(i=0; i<4; i++) {
			
			if(matches[i] == null || matches[i].getPlayer2()==null||matches[i].getPlayer2().getId() ==0)
				break;
		}
		if(matches[i]==null)
			return 2*i;
		else
			return 2*i+1;
	}
	
	private float canPayFee(Player P, Tournament t) {
		
		PaidTournament pT = pTournamentDAO.findById(t.getId());
		if(P.getMoney()< pT.getFee())
			return 0;
		return P.getMoney() - pT.getFee();
	}
	
	public ArrayList<PaidTournamentDTO> searchTournament(String name, boolean isPaid) {
		
		ArrayList<Tournament> listOfTournaments = tournamentDAO.getAllTournaments();
		ArrayList<PaidTournament> listOfPaid = pTournamentDAO.getAllTournaments();
		ArrayList<PaidTournamentDTO> transferredList = new ArrayList<PaidTournamentDTO>();
		if(isPaid==false) {
		for(Tournament tournament:listOfTournaments) {
			
			if(tournament.getIsPaid()==false && tournament.getName().contains(name)) {
			TournamentDTO t = convertRepresentation(tournament);
			PaidTournamentDTO p = new PaidTournamentDTO(0);
			p.setT(t);
			transferredList.add(p);
			}
		}
		}
		for(PaidTournament tournament: listOfPaid) {
			
			if(tournament.getTournament().getName().contains(name)) {
				PaidTournamentDTO p = new PaidTournamentDTO(tournament.getFee());
				TournamentDTO t = convertRepresentation(tournament.getTournament());
				p.setT(t);
				transferredList.add(p);
			}
		}
		return transferredList;
	}
	public boolean enrollPlayerInTournament(String playerName, String tournamentName) {
		
		
		Tournament t = tournamentDAO.findByAnything("name", tournamentName);
		int i = getNextPlayerPosition(t);
		Match[] matches = new Match[7];
		matches[0] = t.getM1();
		matches[1] = t.getM2();
		matches[2] = t.getM3();
		matches[3] = t.getM4();
		matches[4] = t.getM5();
		matches[5] = t.getM6();
		matches[6] = t.getM7();
		Player toBeEnrolled = playerDAO.findByAnything("name", playerName);
		if(t.getIsPaid()==true) {
		float feePayment = canPayFee(toBeEnrolled,t);
		if(feePayment==0)
			return false;
		toBeEnrolled.setMoney(feePayment);
		playerDAO.updatePlayer(toBeEnrolled);
		}
		for(int j=0; j<7; j++) {
			
			if(matches[j]==null)
				break;
			if(matches[j].getPlayer1()==null||matches[j].getPlayer1().getId()==toBeEnrolled.getId())
				return false;
			if(matches[j].getPlayer2()!=null && matches[j].getPlayer2().getId() == toBeEnrolled.getId()) {
				return false;
			}
			if(matches[j].getPlayer2()!=null&&matches[j].getPlayer2().getId()==toBeEnrolled.getId())
				return false;
		}
		if(i>=8)
			return false;
		if(i%2==0) {
			
			Match m = new Match();
			m.setPlayer1(toBeEnrolled);
			m.setPlayer2((Player)null);
			int inserted = matchDAO.insertMatch(m);
			matches[i/2] = matchDAO.finById(inserted);
			addMatch(i/2, t, matches[i/2]);
			tournamentDAO.update(t);
		} else {
			Match existentMatch = getMatch(i/2, t);
			existentMatch.setPlayer2(playerDAO.findByAnything("name", playerName));
			
			matchDAO.updateMatch(existentMatch);
		}
		//this.setCurrentTournament(currentTournament.getT().getName());
		this.setChanged();
		this.notifyObservers();
		return true;
		
	}

	private Match getMatch(int mNumber, Tournament t) {
		
		switch(mNumber) {
		
		case 0 : return t.getM1(); 
		case 1 : return t.getM2(); 
		case 2 : return t.getM3(); 
		case 3 : return t.getM4(); 
		case 4 : return t.getM5(); 
		case 5 : return t.getM6(); 
		case 6 : return t.getM7(); 
		default : return null;
		}
	}
	private void addMatch(int mNumber, Tournament t, Match m) {
		
		switch(mNumber) {
		
		case 0 : t.setM1(m); break;
		case 1 : t.setM2(m); break;
		case 2 : t.setM3(m); break;
		case 3 : t.setM4(m); break;
		case 4 : t.setM5(m); break;
		case 5 : t.setM6(m); break;
		case 6 : t.setM7(m); break;
		default : break;
		}
	}

	
	private boolean playerInTournament(String playerName, Tournament t) {
		
		Match[] matches = new Match[7];
		matches[0] = t.getM1();
		matches[1] = t.getM2();
		matches[2] = t.getM3();
		matches[3] = t.getM4();
		matches[4] = t.getM5();
		matches[5] = t.getM6();
		matches[6] = t.getM7();
		for(int i=0; i<4;i++) {
			if(matches[i]!=null&&matches[i].getPlayer1()!=null&&matches[i].getPlayer1().getName().equals(playerName))
				return true;
			if(matches[i]!=null&&matches[i].getPlayer2()!=null&&matches[i].getPlayer2().getName().equals(playerName))
				return true;
		}
		return false;
	}
	public ArrayList<PaidTournamentDTO> getEnrolled(String playerName) {
		
		ArrayList<Tournament> listOfTournaments = tournamentDAO.getAllTournaments();
		ArrayList<PaidTournament> listOfPaid = pTournamentDAO.getAllTournaments();
		ArrayList<PaidTournamentDTO> transferredList = new ArrayList<PaidTournamentDTO>();
		for(Tournament tournament:listOfTournaments) {
			
			if(tournament.getIsPaid()==false && playerInTournament(playerName, tournament)) {
			TournamentDTO t = convertRepresentation(tournament);
			PaidTournamentDTO p = new PaidTournamentDTO(0);
			p.setT(t);
			transferredList.add(p);
			}
		}
		for(PaidTournament tournament: listOfPaid) {
			
			if(playerInTournament(playerName, tournament.getTournament())) {
				PaidTournamentDTO p = new PaidTournamentDTO(tournament.getFee());
				TournamentDTO t = convertRepresentation(tournament.getTournament());
				p.setT(t);
				transferredList.add(p);
			}
		}
		return transferredList;
		
	}
	public static TournamentModule getInstance() {
		
		if(INSTANCE==null) INSTANCE =  new TournamentModule();
		return INSTANCE;
	}

	
}

class AComparator implements Comparator<PaidTournamentDTO>{
	 
    public int compare(PaidTournamentDTO e1, PaidTournamentDTO e2) {
       return e1.getT().getName().compareTo(e2.getT().getName());
    }
}

class TComparator implements Comparator<PaidTournamentDTO>{
	 
    public int compare(PaidTournamentDTO e1, PaidTournamentDTO e2) {
       if(e1.getT().classifyTournament()<e2.getT().classifyTournament())
    	   return -1;
       else
    	   return 1;
    }
    
    
 
}