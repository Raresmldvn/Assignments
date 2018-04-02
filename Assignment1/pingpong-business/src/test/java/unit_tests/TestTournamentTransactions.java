package unit_tests;

import business.dto.TournamentDTO;
import business.modules.MatchModule;
import business.modules.PlayerModule;
import business.modules.TournamentModule;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTournamentTransactions {
	
	TournamentModule tournamentModule = new TournamentModule();
	PlayerModule playerModule = new PlayerModule();
	
	@Test
	public void testCreateNewTournament() {
		
		tournamentModule.createNewTournament("New tournament");
		TournamentDTO tournament = tournamentModule.getTournament("New tournament");
		tournamentModule.deleteTournament("New tournament");
		assertEquals(tournament.getName(), "New tournament");
	}

	@Test
	public void testDeleteTournament() {
		
		tournamentModule.createNewTournament("New tournament");
		tournamentModule.deleteTournament("New tournament");
		TournamentDTO tournament = tournamentModule.getTournament("New tournament");
		assertEquals(tournament, null);
	}
	
	@Test 
	public void tesUpdateTournament() {
		
		tournamentModule.createNewTournament("New tournament");
		tournamentModule.updateTournament("New tournament", "New");
		TournamentDTO tournament = tournamentModule.getTournament("New");
		tournamentModule.deleteTournament("New");
		assertEquals(tournament.getName(), "New");	
	}
	
	@Test
	public void testEnrollment() {
		
		tournamentModule.createNewTournament("New tournament");
		playerModule.createPlayer("A", "B", "C");
		playerModule.createPlayer("B", "C", "D");
		playerModule.createPlayer("C", "D", "E");
		tournamentModule.enrollPlayerInTournament("A", "New tournament");
		tournamentModule.enrollPlayerInTournament("B", "New tournament");
		tournamentModule.enrollPlayerInTournament("C", "New tournament");
		TournamentDTO tournament = tournamentModule.getTournament("New tournament");
		tournamentModule.deleteTournament("New tournament");
		playerModule.deletePlayer("A");
		playerModule.deletePlayer("B");
		playerModule.deletePlayer("C");;
		assertEquals(tournament.getMatches()[0].getNamePlayer1(), "A");
		assertEquals(tournament.getMatches()[0].getNamePlayer2(), "B");
		assertEquals(tournament.getMatches()[1].getNamePlayer1(), "C");
	}
	
	
	
}
