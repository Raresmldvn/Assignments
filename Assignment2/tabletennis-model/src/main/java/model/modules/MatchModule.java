package model.modules;

import java.util.Observable;

import dal.factory.DaoFactory;
import dal.interfaces.MatchDAOInterface;
import dal.interfaces.PaidTournamentDAOInterface;
import dal.interfaces.PlayerDAOInterface;
import dal.interfaces.SetDAOInterface;
import dal.interfaces.TournamentDAOInterface;
import dal.model.Match;
import dal.model.PaidTournament;
import dal.model.Set;
import dal.model.Tournament;
import dal.model.Player;
import model.dto.MatchDTO;
import model.dto.PlayerDTO;
import model.dto.SetDTO;

public class MatchModule extends Observable{

	private static final int NOT_ENDED = 0;
	private static final int PLAYER1_WON = 1;
	private static final int PLAYER2_WON = 2;
	
	private MatchDAOInterface matchDAO;
	private SetDAOInterface setDAO;
	private TournamentDAOInterface tournamentDAO;
	private PlayerDAOInterface playerDAO;
	private MatchDTO currentMatch;
	private Match currentMatchEntity;
	private PaidTournamentDAOInterface paidTournamentDAO;
	
	private static MatchModule INSTANCE;
	public MatchModule() {
		
		DaoConfiguration config = new DaoConfiguration();
		config.setFactory();
		matchDAO = config.getFactory().getMatchDao();
		setDAO =config.getFactory().getSetDao();
		tournamentDAO = config.getFactory().getTournamentDao();
		playerDAO =  config.getFactory().getPlayerDao();
		paidTournamentDAO = config.getFactory().getPaidTournamentDao();
		
	}
	
	public MatchDTO convertRepresentation(Match match) {
		
		MatchDTO mD = null;
		if(match!=null) {
			PlayerDTO P1, P2;
			if(match.getPlayer1()!=null) {
				P1 = new PlayerDTO(match.getPlayer1().getName(), match.getPlayer1().getEmail(), match.getPlayer1().getPassword(),match.getPlayer1().getMoney(), match.getPlayer1().getIsAdmin());
			} else {
				P1 = null;
			}
			if(match.getPlayer2()!=null) {
				P2 = new PlayerDTO(match.getPlayer2().getName(), match.getPlayer2().getEmail(), match.getPlayer2().getPassword(),match.getPlayer2().getMoney(), match.getPlayer2().getIsAdmin());
			} else {
				P2 = null;
			}
			
			SetDTO S1 = convertSetRepresentation(match.getSet1());
			SetDTO S2 = convertSetRepresentation(match.getSet2());
			SetDTO S3 = convertSetRepresentation(match.getSet3());
			SetDTO S4 = convertSetRepresentation(match.getSet4());
			SetDTO S5 = convertSetRepresentation(match.getSet5());
			mD = new MatchDTO(P1,P2,S1,S2,S3,S4,S5);
			mD.setId(match.getId());
		}
		return mD;
	}
	
	public void setCurrentMatch(MatchDTO m) {
		
		currentMatch = m;
	}
	
	public MatchDTO getCurrentMatch() {
		
		return currentMatch;
	}
	public SetDTO convertSetRepresentation(Set set) {
		
		SetDTO sD = new SetDTO(set.getId(), set.getScore1(), set.getScore2());
		sD.setId(set.getId());
		return sD;
	}
	
	public int checkForSetWinner(int score1, int score2) {
		
		if(score1==11) {
			
			if(score2<=9)
				return PLAYER1_WON;
		} else {
			
			if(score2==11) {
				
				if(score1<=9)
					return PLAYER2_WON;
			} else {
				
					if(score1>11&score1-score2==2)
						return PLAYER1_WON;
					else if(score2>11&&score2-score1==2)
						return PLAYER2_WON;
			}
		}
		return NOT_ENDED;
	}
	
	public int[] getMatchScore(MatchDTO m) {
		
		SetDTO[] sets = new SetDTO[5];
		sets[0] = m.getSet1();
		sets[1] = m.getSet2();
		sets[2] = m.getSet3();
		sets[3] = m.getSet4();
		sets[4] = m.getSet5();
		int[] numberOfWins = new int[2];
		numberOfWins[0] = 0;
		numberOfWins[1] = 0;
		int i,status = 0;
		for(i=0; i<5; i++) {
			
			status = checkForSetWinner(sets[i].getScore1(), sets[i].getScore2());
			if(status==PLAYER1_WON)
				numberOfWins[0]++;
			else if(status==PLAYER2_WON)
				numberOfWins[1]++;
		}
		return numberOfWins;
	}

	public int[] getCurrentMatchScore() {
		
		return getMatchScore(currentMatch);
	}

	public int isOver(MatchDTO match) {
		
		int[] nr = getMatchScore(match);
		if(nr[0]==3)
			return 1;
		if(nr[1]==3)
			return 2;
		return -1;
	}
	public boolean isOver() {
		
		int[] nr = getMatchScore(currentMatch);
		if(nr[0]==3||nr[1]==3)
			return true;
		return false;
	}
	
	public int increase(boolean player) {
		
		SetDTO[] sets = new SetDTO[5];
		sets[0] = currentMatch.getSet1();
		sets[1] = currentMatch.getSet2();
		sets[2] = currentMatch.getSet3();
		sets[3] = currentMatch.getSet4();
		sets[4] = currentMatch.getSet5();
		int check, i;
		for(i=0; i<5; i++) {
			check = checkForSetWinner(sets[i].getScore1(), sets[i].getScore2());
			if(check==NOT_ENDED)
				break;
		}
		if(isOver()) 
			return -1;
		Set set = new Set();
		set.setId(sets[i].getId());
		if(player==true) {
			sets[i].setScore1(sets[i].getScore1()+1);
			set.setScore1(sets[i].getScore1());
			set.setScore2(sets[i].getScore2());
		}
		else {
			sets[i].setScore2(sets[i].getScore2()+1);
			set.setScore2(sets[i].getScore2());
			set.setScore1(sets[i].getScore1());
		}
		setDAO.updateSet(set);
		return (checkForSetWinner(sets[i].getScore1(), sets[i].getScore2()))*10+(i+1);
	}

	private int findIndex(Match[] matches, int id) {
		
		for(int i=0; i<7;i++)
			if(matches[i]!=null&&matches[i].getId()==id)
				return i;
		return -1;
	}

	public void insertIntoNextMatch(boolean whichOne, String tournament) {
		
		Tournament t = tournamentDAO.findByAnything("name", tournament);
		int currentMatchIndex;
		Match[] matches = new Match[7];
		matches[0] = t.getM1();
		matches[1] = t.getM2();
		matches[2] = t.getM3();
		matches[3] = t.getM4();
		matches[4] = t.getM5();
		matches[5] = t.getM6();
		matches[6] = t.getM7();
		currentMatchIndex = findIndex(matches, currentMatch.getId());
		Player whichPlayer;
		if(whichOne==true)
				whichPlayer = playerDAO.findByAnything("name", currentMatch.getPlayer1().getName());
		else
				whichPlayer = playerDAO.findByAnything("name", currentMatch.getPlayer2().getName());
		switch(currentMatchIndex) {
		
		case 0: {
			this.updateOrInsertMatch(t, 4, true, whichPlayer);
			break;
		}
		case 1: {
			this.updateOrInsertMatch(t, 4, false, whichPlayer);
			break;
			
		}
		case 2: {
			
			this.updateOrInsertMatch(t, 5, true, whichPlayer);
			break;
		}
		case 3: {
			
			this.updateOrInsertMatch(t, 5, false, whichPlayer);
			break;
		}
		case 4: {
			
			this.updateOrInsertMatch(t, 6, true, whichPlayer);
			break;
		}
		case 5: {
			
			this.updateOrInsertMatch(t, 6, false, whichPlayer);
			break;
		}
		case 6: {
			if(t.getIsPaid()==true) {
				Player P;
				PaidTournament pT = paidTournamentDAO.findById(t.getId());
				int[] score = getCurrentMatchScore();
				if(score[0]>score[1]) {
					P = playerDAO.findByAnything("name", currentMatch.getPlayer1().getName());
					P.setMoney(P.getMoney() + pT.getFee()*7);
					playerDAO.updatePlayer(P);
				}
				else {
					P = playerDAO.findByAnything("name", currentMatch.getPlayer2().getName());
					P.setMoney(P.getMoney() + pT.getFee()*7);
					playerDAO.updatePlayer(P);
				}
			}
		}
		default : break;
		}
		TournamentModule.getInstance().setCurrentTournament(TournamentModule.getInstance().getCurrentTournament().getT().getName());
		this.setChanged();
		this.notifyObservers();
	}
	private void  updateOrInsertMatch(Tournament t, int index, boolean up_down, Player player) {
		
		Match[] matches = new Match[7];
		matches[0] = t.getM1();
		matches[1] = t.getM2();
		matches[2] = t.getM3();
		matches[3] = t.getM4();
		matches[4] = t.getM5();
		matches[5] = t.getM6();
		matches[6] = t.getM7();
		Match match = matches[index];
		int m;
		
		if(match==null) {
			if(up_down==true) {
				Match newMatch = new Match();
				newMatch.setPlayer1(player);
				newMatch.setPlayer2((Player)null);
				m = matchDAO.insertMatch(newMatch);
				setMatch(t, matchDAO.finById(m), index);
				tournamentDAO.update(t);
			}
				
			else {
				Match newMatch = new Match();
				newMatch.setPlayer2(player);
				newMatch.setPlayer1((Player)null);
				m = matchDAO.insertMatch(newMatch);
				setMatch(t, matchDAO.finById(m), index);
				tournamentDAO.update(t);
			}
		} else {
			if(up_down==true) {
				match.setPlayer1(player);
				 matchDAO.updateMatch(match);
			}
			else
				match.setPlayer2(player);
				matchDAO.updateMatch(match);
		}
		
	}

	public void setMatch(Tournament t, Match M, int index ) {
		
		switch(index) {
		
			case 0: t.setM1(M); break;
			case 1: t.setM2(M); break;
			case 2: t.setM3(M); break;
			case 3: t.setM4(M); break;
			case 4: t.setM5(M); break;
			case 5: t.setM6(M); break;
			case 6: t.setM7(M); break;
			default: System.out.println("Default"); break;
		}
	}
	public static MatchModule getInstance() {
		
		if(INSTANCE==null) INSTANCE = new MatchModule();
		return INSTANCE;
	}
	
	public Player withdraw(Player p, float amount) {
		
		if(p.getMoney()>=amount)
			p.setMoney(p.getMoney() - amount);
		return p;
	}
	public Player deposit(Player p, float amount) {
		
		p.setMoney(p.getMoney() + amount);
		return p;
	}
}
