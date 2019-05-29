import java.util.ArrayList;
import java.util.Random;

public class ProbabilityBoard {
	static Random rand = new Random();
	double[][] probs;
	
	public ProbabilityBoard(int w, int h) {
		probs = new double[w][h];
	}
	
	public Vector getHighestProbability(BattleshipsBoard board) {
		Vector out = new Vector();
		double best=0;
		int c=1;
		for(int x=0;x<probs.length;x++) {
			for(int y=0;y<probs[0].length;y++) {
				if(board.board[x][y].hit) {continue;}
				if(probs[x][y]>best) {
					c=1;
					best = probs[x][y];
					out.x = x;
					out.y = y;
				}else if(probs[x][y]==best) {
					if(rand.nextInt(c)==0) {
						out.x=x;
						out.y=y;
					}
					c++;
				}
			}
		}
		probs[out.x][out.y]=-1;
		return out;
	}
	
	private void clearProbs() {
		for(int x=0;x<probs.length;x++) {
			for(int y=0;y<probs[0].length;y++) {
				probs[x][y]=0;
			}
		}
	}
	
	public void shipHit(BattleshipsBoard bBoard) {
		clearProbs();
		
		//for every spot

		for(int x=0;x<probs.length;x++) {
			for(int y=0;y<probs[0].length;y++) {
				BattleshipsShip currentShip = bBoard.board[x][y].getShip();
				if(currentShip==null || !currentShip.alive||!bBoard.board[x][y].hit) {
					//if we killed it we dont care or if it doesnt exist we dont care
					continue;
				}
				boolean knowVertical=false;
				boolean knowHorizontal=false;
				Vector up, down, left, right;
				
				Vector curPos = new Vector(x,y);
				
				up = curPos.plus(new Vector(0,1));
				down = curPos.plus(new Vector(0,-1));
				left = curPos.plus(new Vector(1,0));
				right = curPos.plus(new Vector(-1,0));
				
				//check horizontal
				if(isValid(up)) {
					BattleshipsTile tile = bBoard.board[up.x][up.y];
					if(tile.hit && tile.getShip()==currentShip) {
						knowVertical=true;
					}
				}
				if(isValid(down)) {
					BattleshipsTile tile = bBoard.board[down.x][down.y];
					if(tile.hit && tile.getShip()==currentShip) {
						knowVertical=true;
					}
				}
				//check vertical
				if(isValid(left)) {
					BattleshipsTile tile = bBoard.board[left.x][left.y];
					if(tile.hit && tile.getShip()==currentShip) {
						knowHorizontal=true;
					}
				}

				if(isValid(right)) {
					BattleshipsTile tile = bBoard.board[right.x][right.y];
					if(tile.hit && tile.getShip()==currentShip) {
						knowHorizontal=true;
					}
				}
				
				if(knowVertical) {
					if(isValid(up)&&!bBoard.board[up.x][up.y].hit) {
						probs[up.x][up.y]+=2;
					}
					if(isValid(down)&&!bBoard.board[down.x][down.y].hit) {
						probs[down.x][down.y]+=2;
					}
				}else if(knowHorizontal){
					if(isValid(left)&&!bBoard.board[left.x][left.y].hit) {
						probs[left.x][left.y]+=2;
					}
					if(isValid(right)&&!bBoard.board[right.x][right.y].hit) {
						probs[right.x][right.y]+=2;
					}
				}else {
					//We don't know
					if(isValid(up)&&!bBoard.board[up.x][up.y].hit) {
						probs[up.x][up.y]+=1;
					}
					if(isValid(down)&&!bBoard.board[down.x][down.y].hit) {
						probs[down.x][down.y]+=1;
					}
					if(isValid(left)&&!bBoard.board[left.x][left.y].hit) {
						probs[left.x][left.y]+=1;
					}
					if(isValid(right)&&!bBoard.board[right.x][right.y].hit) {
						probs[right.x][right.y]+=1;
					}
				}
				
			}
		}
		
		
		
		
		
		
//		if(!bBoard.board[p.x][p.y].shipInTile.alive) {
//			return;
//		}
//		ArrayList<Vector> newPoints = new ArrayList<Vector>();
//		newPoints.add(new Vector(1, 0));
//		newPoints.add(new Vector(-1, 0));
//		newPoints.add(new Vector(0, 1));
//		newPoints.add(new Vector(0, -1));
//		
//		for(Vector pos:newPoints) {
//			if(isValid(pos)) {
//				
//			}
//		}
	}
	
	private boolean isValid(Vector p) {
		return (p.x>=0&&p.x<probs.length&&p.y>=0&&p.y<probs[0].length);
	}
}
