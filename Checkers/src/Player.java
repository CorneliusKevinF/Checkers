
public class Player {
	String name;
	PlayerType type;
	Color color;

	public Player(String name, PlayerType type, Color color) {
		this.name = name;
		this.type = type;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public PlayerType getType() {
		return this.type;
	}
	
	public Color getColor() {
		return this.color;
	}
}
