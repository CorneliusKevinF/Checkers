/*
 * Piece Class
 * @author Robert Dvorscak
 * @author Kevin Cornelius
 * @ int color
 * @ int[] location
 * @ boolean isKing
 */

public class Piece {
	private Color color;

	Piece(Color color) {
		this.color = color;
	}
	
//Movement Below * TO BE MOVED TO GAME CLASS
	/*
	private int[] location; //row, column from black team perspective	
	private enum Direction {
		FORWARDRIGHT, FORWARDLEFT, BACKWARDRIGHT, BACKWARDLEFT
	}
	 
	public int[] jump(Direction direction){
		move(direction);
		move(direction);
		
		return location;
	}
	
	public int[] move(Direction direction){
		if(color == 0){
			switch(direction){
				case FORWARDLEFT:
					location[1] = location[1]+1;//Forward
					location[2] = location[2]-1;//Left
					break;
				case FORWARDRIGHT:
					location[1] = location[1]+1;//Forward
					location[2] = location[2]+1;//Right
					break;
				case BACKWARDLEFT:
					if(isKing){
						location[1] = location[1]-1;//Backwards
						location[2] = location[2]-1;//Left
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
				case BACKWARDRIGHT:
					if(isKing){
						location[1] = location[1]-1;//Backwards
						location[2] = location[2]+1;//Right
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
			}
		}
		else if(color ==1){
			switch(direction){
				case FORWARDLEFT:
					location[1] = location[1]-1;//Forward
					location[2] = location[2]+1;//Left
					break;
				case FORWARDRIGHT:
					location[1] = location[1]-1;//Forward
					location[2] = location[2]-1;//Right
					break;
				case BACKWARDLEFT:
					if(isKing){
						location[1] = location[1]+1;//Backwards
						location[2] = location[2]+1;//Left
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
				case BACKWARDRIGHT:
					if(isKing){
						location[1] = location[1]+1;//Backwards
						location[2] = location[2]-1;//Right
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
			}
		}
		else{
			System.out.println("ERROR: Piece has no color.");
			return new int[0];
		}
		return location;
	}
	*/
	
//Getters and Setters Below ***********
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
