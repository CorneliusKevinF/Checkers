
public class Piece {
	private int color; //0=Black 1=Red
	private int[] location; //row, column from black team perspective
	private boolean isKing; 
	
	Piece(int color, int[] location) {
		this.color = color;
		this.location = location;
		this.isKing = false;
		if(location.length>2){
			System.out.println("ERROR: Invalid location.");
		}
	}
	//Movement Below **********************
	
	public int[] jump(String direction){ //FL, FR, BL, BR
		int[] location = new int[2];
		
		//TODO Write body of jump method.
		return location;
	}
	
	public int[] move(String direction){ //FL, FR, BL, BR
		int[] location = new int[2];
		if(color == 0){
			switch(direction){
				case "FL":
					location[1] = location[1]+1;//Forward
					location[2] = location[2]-1;//Left
					break;
				case "FR":
					location[1] = location[1]+1;//Forward
					location[2] = location[2]+1;//Right
					break;
				case "BL":
					if(isKing){
						location[1] = location[1]-1;//Backwards
						location[2] = location[2]-1;//Left
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
				case "BR":
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
				case "FL":
					location[1] = location[1]-1;//Forward
					location[2] = location[2]+1;//Left
					break;
				case "FR":
					location[1] = location[1]-1;//Forward
					location[2] = location[2]-1;//Right
					break;
				case "BL":
					if(isKing){
						location[1] = location[1]+1;//Backwards
						location[2] = location[2]+1;//Left
						break;
					}
					else{
						System.out.println("ERROR: Piece is not a king.");
					}
					break;
				case "BR":
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
	
	
	//Getters and Setters Below ***********

	public boolean isKing() {
		return isKing;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
