package unit_tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import business.dto.PlayerDTO;
import business.modules.PlayerModule;
public class TestPlayerTransactions {

	PlayerModule playerModule = new PlayerModule();
	
	@Test
	public void testInsertPlayer() {
		
		playerModule.createPlayer("A", "B", "C");
		PlayerDTO found = playerModule.getPlayerBy("name", "A");
		playerModule.deletePlayer("A");
		assertEquals(found.getName(), "A");
		assertEquals(found.getEmail(), "B");
		assertEquals(found.getPassword(), "C");
	   }
	
	@Test
	public void testDeletePlayer() {
		
		playerModule.createPlayer("A", "B", "C");
		playerModule.deletePlayer("A");
		PlayerDTO found = playerModule.getPlayerBy("name", "A");
		assertNull(found);
	}
	
	@Test 
	public void testUpdatePlayer() {
		
		playerModule.createPlayer("A", "B", "C");
		playerModule.updatePlayer(new PlayerDTO("AB", "B", "C"));
		PlayerDTO newPlayer = playerModule.getPlayerBy("name", "AB");
		playerModule.deletePlayer("A");
		assertEquals(newPlayer.getName(), "AB");
	}
	
	@Test
	public void testLogInRegular() {
	
		playerModule.createPlayer("A", "B", "C");
		int result = playerModule.logIn("B", "C");
		assertEquals(result, 1);
	}
	
	@Test
	public void testLogInAdmin() {
		
		int result = playerModule.logIn("admin@pp.com", "pass");
		assertEquals(result, 0);
	}
	
	@Test
	public void testLogInWrongUsername() {
		
		int result = playerModule.logIn("XYWKGDL", "EFE");
		assertEquals(result, -1);
	}
	
	@Test
	public void testLogInWrongPassword() {
		
		int result = playerModule.logIn("admin@pp.com", "x");
		assertEquals(result, -2);
	}
}
