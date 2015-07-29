
public class Player {
	String name;
	Type type;
	
	private enum Type {
		HUMAN, ROBOT;
	}
	
	public Player(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
}
