import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleshipsShip {
	static ArrayList<String> shipNames = loadShipNames();
	static Random rand = new Random();
	static String[] defaultNames = {"ERROR"};
	
	private static ArrayList<String> loadShipNames(){
		ArrayList<String> fileStrs = new ArrayList<String>();
		try {
			Scanner readShipFile = new Scanner(new File("shipnames.txt"));
			while(readShipFile.hasNextLine()) {
				fileStrs.add(readShipFile.nextLine());
				//System.out.println(fileStrs.get(fileStrs.size()-1));
			}
			readShipFile.close();
			return fileStrs;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileStrs.add("ERROR - File Missing \"shipnames.txt\"");
		return fileStrs;
	}
	
	
	boolean alive;
	int playerID;
	int length;
	int remainingHP;
	Vector position;
	Vector dir;
	BattleshipsBoard parentBoard;
	String name;
	String type;
	BattleshipsShip(BattleshipsBoard parentBoard, int pID,  Vector pos,int l, int dir){
		alive=true;
		playerID = pID;
		length = l;
		remainingHP=l;
		this.position = pos;
		this.parentBoard = parentBoard;
		this.dir = new Vector(dir);
		setType();
		setName();
	}
	
	void setName() {
		int i = rand.nextInt(shipNames.size());
		name=shipNames.get(i);
	}
	
	void setType() {
		switch(length) {
		case 0:
			type = "Nothing";
			break;
		case 1: 
			type = "Corvette";
			break;
		case 2:
			type = "Destroyer";
			break;
		case 3:
			type = "Cruiser";
			break;
		case 4:
			type = "Battleship";
			break;
		case 5:
			type = "Aircraft Carrier";
			break;
		default:
			type = "Unknown";
			break;
				
		}
	}
	
	public boolean hit() {
		remainingHP--;
		if(remainingHP==0) {
			alive=false;
		}
		return true;
	}
}
