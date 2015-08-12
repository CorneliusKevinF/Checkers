package model;

public class Move {
	boolean isJump = false;
	private Position startingPosition, endingPosition, jumpedPosition;
	
	public Move(Position startingPosition, Position endingPosition) {
		this(startingPosition, endingPosition, null);
	}
	
	public Move(Position startingPosition, Position endingPosition, Position jumpedPosition) {
		this.startingPosition = startingPosition;
		this.endingPosition = endingPosition;
		this.jumpedPosition = jumpedPosition;
		
		if(jumpedPosition != null) isJump = true;
	}
	
	public boolean isJump() {
		return isJump;
	}
	
	public Position getStartingPosition() {
		return startingPosition;
	}
	
	public Position getEndingPosition() {
		return endingPosition;
	}
	
	public Position getJumpedPosition() {
		return jumpedPosition;
	}
}
