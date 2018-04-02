package business.modules;

import dao.access.MatchDAO;
import dao.access.SetDAO;
import dao.access.TournamentDAO;
import dao.entities.Match;
import dao.entities.Player;
import dao.entities.Set;
import dao.entities.Stage;
import dao.entities.Tournament;
import dao.interfaces.MatchDAOInterface;
import dao.interfaces.SetDAOInterface;
import dao.interfaces.TournamentDAOInterface;

public class MatchModule {
	
	private Match currentMatch;
	
	private MatchDAOInterface matchDAO;
	private TournamentDAOInterface tournamentDAO;
	private SetDAOInterface setDAO;
	
	private static final int NOT_ENDED = 0;
	private static final int PLAYER1_WON = 1;
	private static final int PLAYER2_WON = 2;

	
	public MatchModule(int matchId) {
		
		this.matchDAO = new MatchDAO();
		this.setDAO = new SetDAO();
		this.currentMatch = matchDAO.finById(matchId);
		this.tournamentDAO = new TournamentDAO();
	}
	
	
	//Method that checks if a winner 
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
	
	public int[] getMatchScore(Match m) {
		
		Set[] sets = m.getSetList();
		int[] numberOfWins = new int[2];
		numberOfWins[0] = 0;
		numberOfWins[1] = 0;
		int i,status = 0;
		for(i=0; i<5; i++) {
			
			status = checkForSetWinner(sets[i].getScore(true), sets[i].getScore(false));
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
	public boolean isOver() {
		
		int[] nr = getMatchScore(currentMatch);
		if(nr[0]==3||nr[1]==3)
			return true;
		return false;
	}
	
	public int increase(boolean player) {
		
		Set[] sets = currentMatch.getSetList();
		int check, i;
		for(i=0; i<5; i++) {
			check = checkForSetWinner(sets[i].getScore(true), sets[i].getScore(false));
			if(check==NOT_ENDED)
				break;
		}
		if(isOver()) 
			return -1;
		sets[i].increaseScore(player);
		setDAO.updateSet(sets[i]);
		return (checkForSetWinner(sets[i].getScore(true), sets[i].getScore(false)))*10+(i+1);
	}
	
	//Method that finds the index of a match in a tournament match list
	private int findIndex(Match[] matches, int id) {
		
		for(int i=0; i<7;i++)
			if(matches[i].getId()==id)
				return i;
		return -1;
	}
	
	//Method that gets a boolean variable which selects player number(1 or 2) and inserts in the corresponding match that follows, the winner
	public void insertIntoNextMatch(boolean whichOne, String tournament) {
		
		Tournament currentTournament = tournamentDAO.findByAnything("name", tournament);
		int currentMatchIndex;
		Match[] matches = currentTournament.getMatches();
		currentMatchIndex = findIndex(matches, currentMatch.getId());
		Player whichPlayer;
		if(whichOne==true)
				whichPlayer = currentMatch.getFirstPlayer();
		else
				whichPlayer = currentMatch.getSecondPlayer();
		switch(currentMatchIndex) {
		
		case 0: {
			this.updateOrInsertMatch(currentTournament, 4, true, whichPlayer, Stage.SF);
			break;
		}
		case 1: {
			this.updateOrInsertMatch(currentTournament, 4, false, whichPlayer, Stage.SF);
			break;
			
		}
		case 2: {
			
			this.updateOrInsertMatch(currentTournament, 5, true, whichPlayer, Stage.SF);
			break;
		}
		case 3: {
			
			this.updateOrInsertMatch(currentTournament, 5, false, whichPlayer, Stage.SF);
			break;
		}
		case 4: {
			
			this.updateOrInsertMatch(currentTournament, 6, true, whichPlayer, Stage.F);
			break;
		}
		case 5: {
			
			this.updateOrInsertMatch(currentTournament, 6, false, whichPlayer, Stage.F);
			break;
		}
		default : {
			
			break;
		}
		}
	}
	
	private void  updateOrInsertMatch(Tournament t, int index, boolean up_down, Player player,Stage stage) {
		
		Match[] matches = t.getMatches();
		Match match = matches[index];
		int m;
		
		if(match==null) {
			if(up_down==true) {
				m = matchDAO.insertMatch(new Match(1, player, (Player)null, stage));
				matches[index] = new Match(m, player, (Player)null, stage);
				Tournament result = new Tournament(t.getId(), t.getName());
				result.setMatches(matches);
				tournamentDAO.update(result);
			}
				
			else {
				m = matchDAO.insertMatch(new Match(1, (Player)null, player, stage));
				matches[index] = new Match(m, player, (Player)null, stage);
				Tournament result = new Tournament(t.getId(), t.getName());
				result.setMatches(matches);
				tournamentDAO.update(result);
			}
		} else {
			if(up_down==true)
				 matchDAO.updateMatch(new Match(match.getId(), player, match.getSecondPlayer(), match.getStage()));
			else
				matchDAO.updateMatch(new Match(match.getId(), match.getFirstPlayer(), player, match.getStage()));
		}
	}
}
