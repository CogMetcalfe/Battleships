
public class BattleshipsTile {
	boolean hit;
	boolean occupied;
	BattleshipsShip shipInTile;
	BattleshipsTile(){
		occupied=false;
		hit=false;
	}
	
	void takeShip(BattleshipsShip ship) {
		if(ship!=null) {
			occupied=true;
			shipInTile = ship;
		}
	}
	
	BattleshipsShip getShip() {
		return shipInTile;
	}
	
	public boolean fire() {
		if(!hit) {
			hit=true;
			if(shipInTile!=null) {
				return shipInTile.hit();
			}
		}
		return false;
	}
}