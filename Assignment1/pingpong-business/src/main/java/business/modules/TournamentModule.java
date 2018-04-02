package business.modules;

import java.util.ArrayList;

import business.dto.TournamentDTO;
import business.dto.MatchDTO;
import business.dto.SetDTO;
import dao.access.MatchDAO;
import dao.access.PlayerDAO;
import dao.access.SetDAO;
import dao.access.TournamentDAO;
import dao.entities.Match;
import dao.entities.Player;
import dao.entities.Set;
import dao.entities.Stage;
import dao.entities.Tournament;
import dao.interfaces.MatchDAOInterface;
import dao.interfaces.PlayerDAOInterface;
import dao.interfaces.SetDAOInterface;
import dao.interfaces.TournamentDAOInterface;

public class TournamentModule {
	
	private TournamentDAOInterface tournamentDAO;
	private PlayerDAOInterface playerDAO;
	private MatchDAOInterface matchDAO;
	private Tournament currentTournament;
	
	public TournamentModule() {
		
		tournamentDAO = new TournamentDAO();
		playerDAO = new PlayerDAO();
		matchDAO = new MatchDAO();
	}
	public ArrayList<TournamentDTO> getAllTournaments() {
		
		ArrayList<Tournament> listOfTournaments = tournamentDAO.getAllTournaments();
		ArrayList<TournamentDTO> transferredList = new ArrayList<TournamentDTO>();
		for(Tournament tournament:listOfTournaments) {
			
			transferredList.add(new TournamentDTO(tournament.getId(), tournament.getName(),(MatchDTO[])null));
		}
		return transferredList;
	}
	
	public void createNewTournament(String tournamentName) {
		
		tournamentDAO.insert(new Tournament(1, tournamentName));
		
	}
	
	public void deleteTournament(String tournamentName) {
		
		Tournament t = tournamentDAO.findByAnything("name", tournamentName);
		SetDAOInterface setDAO = new SetDAO();
		for(int i=0; i<7;i++)
			if(t.getMatches()[i]!=null) {
				matchDAO.deleteMatch(t.getMatches()[i].getId());
				for(int j=0; j<5; j++)
					setDAO.deleteSet(t.getMatches()[i].getSetList()[j].getId());
			}
		tournamentDAO.delete(tournamentDAO.findByAnything("name", tournamentName).getId());
	}
	
	private int getNextPlayerPosition(Tournament t) {
		
		Match[] matches = t.getMatches();
		int i;
		for(i=0; i<4; i++) {
			
			if(matches[i] == null || matches[i].getSecondPlayer()==null||matches[i].getSecondPlayer().getId() ==0)
				break;
		}
		if(matches[i]==null)
			return 2*i;
		else
			return 2*i+1;
	}
	
	public boolean enrollPlayerInTournament(String playerName, String tournamentName) {
		
		Tournament theTournament = tournamentDAO.findByAnything("name", tournamentName);
		int i = getNextPlayerPosition(theTournament);
		Match[] matches = theTournament.getMatches();
		Player toBeEnrolled = playerDAO.findByAnything("name", playerName);
		for(int j=0; j<7; j++) {
			
			if(matches[j]==null)
				break;
			if(matches[j].getFirstPlayer()==null)
				return false;
			if(matches[j].getFirstPlayer().getId() == toBeEnrolled.getId()) {
				return false;
			}
			if(matches[j].getSecondPlayer()!=null&&matches[j].getSecondPlayer().getId()==toBeEnrolled.getId())
				return false;
		}
		if(i>=8)
			return false;
		if(i%2==0) {
			
			int inserted = matchDAO.insertMatch(new Match(1, toBeEnrolled, (Player)null, Stage.QF));
			matches[i/2] = matchDAO.finById(inserted);
			Tournament newTournament = new Tournament(theTournament.getId(), theTournament.getName());
			for(int j=0; j<7; j++)
				newTournament.addMatch(matches[j], j);
			tournamentDAO.update(newTournament);
		} else {
			Match existentMatch = theTournament.getMatches()[i/2];
			matchDAO.updateMatch(new Match(existentMatch.getId(), existentMatch.getFirstPlayer(), playerDAO.findByAnything("name", playerName), existentMatch.getStage()));
		}
		return true;
		
	}
	
	public void updateTournament(String name, String newName) {
		
		Tournament oldTournament = tournamentDAO.findByAnything("name", name);
		Tournament newTournament = new Tournament(oldTournament.getId(), newName);
		for(int i=0; i<7; i++)
			newTournament.addMatch(oldTournament.getMatches()[i], i);
		tournamentDAO.update(newTournament);
	}
	
	public TournamentDTO getTournament(String name) {
		
		TournamentDTO result = null;
		Tournament tournamentObject = tournamentDAO.findByAnything("name", name);
		if(tournamentObject ==null)
			return null;
		this.currentTournament = tournamentObject;
		Match[] matchObjects = tournamentObject.getMatches();
		MatchDTO[] matchDataObjects  = new MatchDTO[7];
		for(int i=0; i<7; i++) {
			SetDTO[] setDataObjects = new SetDTO[5];
			Set[] setObjects;
			if(matchObjects[i]==null) {
				matchDataObjects[i] = null;
			} else {
			setObjects = matchObjects[i].getSetList();
			for(int j=0; j<5; j++) {
				setDataObjects[j] = new SetDTO(setObjects[j].getId(), setObjects[j].getScore(true), setObjects[j].getScore(false));
			}
			String second;
			if(matchObjects[i].getSecondPlayer()==null)
				second = null;
			else
				second = matchObjects[i].getSecondPlayer().getName();
			
			String first;
			if(matchObjects[i].getFirstPlayer()==null)
				first = null;
			else 
				first = matchObjects[i].getFirstPlayer().getName();
			int stage = 1;
			if(matchObjects[i].getStage() == Stage.SF)
				stage = 2;
			else if(matchObjects[i].getStage() == Stage.F)
				stage = 3;
			matchDataObjects[i] = new MatchDTO(matchObjects[i].getId(), first, second, setDataObjects, stage);
			}
		}
		result = new TournamentDTO(tournamentObject.getId(), tournamentObject.getName(), matchDataObjects);
		return result;
	}
}

