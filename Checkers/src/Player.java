
public class Player {
	String name;
	Type type;
	
	private enum Type {
		HUMAN, ROBOT;
	}
	
	Player(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	String getName() {
		return this.name;
	}
	
	Type getType() {
		return this.type;
	}
}
