package business.dto;

public class TournamentDTO {

	private int id;
	private String name;
	private MatchDTO[] matches;
	
	public TournamentDTO(int id, String name, MatchDTO[] m) {
		
		this.id = id;
		this.name = name;
		this.matches = m;
	}
	
	public int getId() {
		
		return id;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public MatchDTO[] getMatches() {
		
		return matches;
	}
}
