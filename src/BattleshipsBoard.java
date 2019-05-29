import java.util.ArrayList;
import java.util.Random;

public class BattleshipsBoard {
	int width, height;
	ArrayList<BattleshipsShip> ships;
	BattleshipsTile[][] board;
	String status="";
	BattleshipsBoard(int w, int h){
		width = w;
		height = h;
		ships = new ArrayList<BattleshipsShip>();
		generateEmptyBoard();
	}
	
	public void generateEmptyBoard() {
		board = new BattleshipsTile[width][height];
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				board[x][y]= new BattleshipsTile();
			}
		}
	}

	public void addNewShip(int pID, int length) {
		findAndAdd(pID, length);
	}
	
	public boolean addNewShip(int pID, Vector pos, int length, int dir) {
		if(isValid(pos, length, dir)) {
			BattleshipsShip newShip = new BattleshipsShip(this,pID,pos.copy(),length,dir);
			ships.add(newShip);
			return true;
		}
		return false;
	}
	
	public boolean findAndAdd(int pID, int l) {
		Random rand = new Random();
		int c=1;
		int nX=-1;
		int nY=-1;
		int dir=0;
		//check every position
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				//for each rotation
				for(int i=0;i<4;i++) {
					Vector p=new Vector(x,y);
					if(isValid(p,l, i)) {
						if(rand.nextInt(c)==0) {
							nX=x;
							nY=y;
							dir=i;
						}
						c++;
					}
				}
			}
		}
		if(nX==-1) {
			return false;
		}
		//must a valid position by now

		Vector p = new Vector(nX,nY);
		Vector v = new Vector(dir);

		BattleshipsShip newShip = new BattleshipsShip(this,pID,p.copy(),l,dir);
		ships.add(newShip);
		
		for(int cl=0;cl<l;cl++) {
			board[p.x][p.y].takeShip(newShip);
			
			p = p.plus(v);
		}
		
		
		return true;
	}
	
	
	public boolean isValid(Vector p,int l, int dir) {
		Vector v = new Vector(dir);
		//see if all the spaces are empty
		boolean available=true;
		for(int cl=0;cl<l;cl++) {
			if(p.x<0||p.x>=width||p.y<0||p.y>=height) {
				available=false;
				break;
			}
			if(board[p.x][p.y].occupied) {
				available=false;
				break;
			}
			p = p.plus(v);
		}
		return available;
	}
	
	
	public boolean isInRange(Vector firePosition) {
		return firePosition.x>=0 && firePosition.x<width && firePosition.y>=0 && firePosition.y<height;
		
	}
	
	public boolean isInRangeAndNotAlreadyHit(Vector firePosition) {
		return (isInRange(firePosition) && !board[firePosition.x][firePosition.y].hit); //if its in range and the position has not already been hit
	}
	
	public boolean hit(int x, int y) {
		if((x<0||x>=width||y<0||y>=height)) {
			return false;
		}
		
		boolean result = board[x][y].fire();
		
		if(result) {
			if(!board[x][y].getShip().alive) {
				//System.out.println("\"" + status + "\" : PRESET");
				status = board[x][y].getShip().type +", the " + board[x][y].getShip().name;
				//System.out.println("\"" + status + "\" : AFTER");
			}
		}
		
		//returns true when successful hit or hit is valid
		return result;
	}
	
	public String getStatus(boolean isEnemy) {
		if(status.equals("")) {
			return "";
		}
		if(isEnemy) {
			return "We sunk their " + status + ".";
		}else {
			return "We lost our " + status + ".";
		}
	}
	
	public boolean allHit() {
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				if(board[x][y].occupied && !board[x][y].hit) {
					return false;
				}
			}
		}
		return true;
	}

	public void consoleMap() { 
		System.out.print("   ");
		for(int x=0; x<width; x++) {
			System.out.print("[" + Coordinate.numToLetter(x) + "]");
		}
		for(int y=0; y<height; y++) {
			System.out.println();
			System.out.print("[" + y + "] ");
			for(int x=0; x<width; x++) {
				if(board[x][y].occupied) {
					System.out.print((board[x][y].getShip().playerID) + ", ");
				}else {
					System.out.print(0 + ", ");
				}
			}
		}
		System.out.println();
	}
//	public void debugConsole() { 
//		for(int y=0; y<height; y++) {
//			System.out.println();
//			for(int x=0; x<width; x++) {
//				if(board[x][y].occupied) {
//					System.out.print((board[x][y].getShip().playerID) + ", ");
//				}else {
//					System.out.print(0 + ", ");
//				}
//			}
//		}
//		System.out.println();
//	}
}
