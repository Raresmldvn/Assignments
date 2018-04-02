package unit_tests;

import business.modules.MatchModule;
import dao.entities.Match;
import dao.entities.Player;
import dao.entities.Set;
import dao.entities.Stage;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestMatchTransactions {

	MatchModule matchModule = new MatchModule(1);
	
	@Test
	public void testSetWinerPlayer1() {
		
		int score1 = 11;
		int score2 = 5;
		int resultCode = matchModule.checkForSetWinner(score1, score2);
		assertEquals(resultCode, 1);
		
	}
	
	@Test
	public void testSetWinerPlayer2() {
		
		int score1 = 1;
		int score2 = 11;
		int resultCode = matchModule.checkForSetWinner(score1, score2);
		assertEquals(resultCode, 2);
		
	}
	
	@Test
	public void testSetWinnerNotEnded() {
		
		int score1 = 1;
		int score2 = 1;
		int resultCode = matchModule.checkForSetWinner(score1, score2);
		assertEquals(resultCode, 0);
		
	}
	
	@Test
	public void testSetWinnerNotEndedOver11() {
		
		int score1 = 12;
		int score2 = 13;
		int resultCode = matchModule.checkForSetWinner(score1, score2);
		assertEquals(resultCode, 0);
		
	}
	
	@Test
	public void testSetWinnerEndedOver11() {
		
		int score1 = 24;
		int score2 = 22;
		int resultCode = matchModule.checkForSetWinner(score1, score2);
		assertEquals(resultCode, 1);
	}
	
	@Test
	public void testMatchScore1() {
		
		Set[] sets = {new Set(1,0,0), new Set(2,0,0), new Set(3,0,0), new Set(4,0,0), new Set(5,0,0)};
		Match match = new Match(1, new Player(1, "A", "B", "C", false), new Player(2, "A", "B", "C", false), sets, Stage.QF);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 0);
		assertEquals(matchScore[1], 0);
	}
	
	@Test
	public void testMatchScore2() {
		
		Set[] sets = {new Set(1,11,3), new Set(2,1,11), new Set(3,0,0), new Set(4,0,0), new Set(5,0,0)};
		Match match = new Match(1, new Player(1, "A", "B", "C", false), new Player(2, "A", "B", "C", false), sets, Stage.QF);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 1);
		assertEquals(matchScore[1], 1);
	}
	
	@Test
	public void testMatchScore3() {
		
		Set[] sets = {new Set(1,14,12), new Set(2,0,11), new Set(3,5,11), new Set(4,20,18), new Set(5,11,3)};
		Match match = new Match(1, new Player(1, "A", "B", "C", false), new Player(2, "A", "B", "C", false), sets, Stage.QF);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 3);
		assertEquals(matchScore[1], 2);
	}
}
