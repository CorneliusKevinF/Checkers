
public class Player {
	String name;
	PlayerType type;
	Color color;

	public Player(String name, PlayerType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public PlayerType getType() {
		return this.type;
	}
}
