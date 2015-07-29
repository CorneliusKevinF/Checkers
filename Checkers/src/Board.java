
public class Board {
	int[][] squares;
	
	public Board() {
		squares = new int[8][8];
		
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[0].length; i++) {
				
			}
		}
	}
	
	public void Start(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(i==0 && j%2==0){
					squares[i][j]=0;
				}
				else if(i==1 && j%2==1){
					squares[i][j]=0;
				}
				else if(i==6 && j%2==0){
					squares[i][j]=1;
				}
				else if(i==7 && j%2==1){
					squares[i][j]=1;
				}
				else{
					squares[i][j]=-1;
				}
				System.out.print("[" + squares[i][j] + "]"); //Don't worry, this is for testing only.
			}
			System.out.println(""); //I will remove as soon as I'm sure the board has correctly created.
		}
	}

}
