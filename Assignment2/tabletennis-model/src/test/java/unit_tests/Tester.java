package unit_tests;

import model.dto.MatchDTO;
import model.dto.SetDTO;
import model.modules.MatchModule;


import org.junit.Test;

import dal.model.Match;
import dal.model.Player;
import dal.model.Set;

import static org.junit.Assert.assertEquals;


public class Tester {

	MatchModule matchModule = new MatchModule();
	
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
		
		SetDTO set = new SetDTO(0,0,0);
		MatchDTO match = new MatchDTO(null, null, set, set, set, set, set);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 0);
		assertEquals(matchScore[1], 0);
	}
	
	@Test
	public void testMatchScore2() {
		
		SetDTO set = new SetDTO(0,0,0);
		SetDTO set1 = new SetDTO(0,11,0);
		SetDTO set2 = new SetDTO(0,3,11);
		MatchDTO match = new MatchDTO(null, null, set1, set2,set, set, set);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 1);
		assertEquals(matchScore[1], 1);
	}
	
	@Test
	public void testMatchScore3() {
		
		SetDTO set1 = new SetDTO(1,14,12);
		SetDTO set2 = new SetDTO(2,0,11);
		SetDTO set3 = new SetDTO(3,5,11);
		SetDTO set4 = new SetDTO(4,20,18);
		SetDTO set5 = new SetDTO(5,11,3);
		MatchDTO match = new MatchDTO(null, null, set1, set2,set3, set4, set5);
		int[] matchScore = matchModule.getMatchScore(match);
		assertEquals(matchScore[0], 3);
		assertEquals(matchScore[1], 2);
	}
	
	public void testDeposit() {
		
		Player P = new Player();
		P.setMoney(100);
		Player result = matchModule.deposit(P, 50);
		assertEquals(result.getMoney(), 150.0);
	}
	
	public void testWithdraw1() {
		
		Player P = new Player();
		P.setMoney(100);
		Player result = matchModule.withdraw(P, 50);
		assertEquals(result.getMoney(), 50);
	}
	
	public void testWithdraw2() {
		
		Player P = new Player();
		P.setMoney(100);
		Player result = matchModule.withdraw(P, 150);
		assertEquals(result.getMoney(), 100);
	}
}
