package dao.entities;

public class Tournament {

	private int id;
	private String name;
	private Match[] match_list;
		
	public Tournament(int tournamentId, String tournamentName) {
			
		this.id = tournamentId;
		this.name = tournamentName;
		this.match_list = new Match[7];
	}
		
	public int getId() {
			
		return this.id;
	}
		
	public String getName() {
			
		return this.name;
	}
		
	public Match[] getMatches() {
			
		return this.match_list;
	}
	
	public void setMatches(Match[] m) {
		
		this.match_list = m;
	}
	
	public void addMatch(Match toBeAdded, int matchNumber) {
		
		match_list[matchNumber] = toBeAdded;
	}
	
	@Override
	public String toString() {
		
		String result = Integer.toString(this.id) + " " + this.name;
		for(int i=0; i<7; i++) {
			
			result = result + this.match_list[i].toString() + "\n" ;
		}
		return result;
	}
}