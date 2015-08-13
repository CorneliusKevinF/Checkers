package model;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Update extends ArrayList<Object> {
	
	public Update(Position p1, Position p2) {
		this();
		this.add(p1);
		this.add(p2);
	}
	 
	
	public Update(Position p, String action) { 
		this();
		this.add(p);
		this.add(action);
	}
	
	public Update() {
		// TODO Auto-generated constructor stub
	}
	
	public Update(int initialCapacity) {
		super();
		// TODO fix these
	}

	public Update(Collection<Object> c) {
		super();
		// TODO fix these
		}
}
