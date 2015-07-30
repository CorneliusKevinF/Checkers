/**
 * 
 * @author Kevin
 *
 */
public class Player {
	String name;
	PlayerType type;
	Color color;
	int ID;
	static int nextID = 1;

	public Player() {
		this("", PlayerType.ROBOT, Color.RED);
	}
	
	public Player(Color color) {
		this("", PlayerType.ROBOT, color);
	}
	
	public Player(String name, PlayerType type, Color color) {
		this.name = name;
		this.type = type;
		this.color = color;
		this.ID = nextID++;
	}
	
	public int getID() {
		return this.ID;
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
