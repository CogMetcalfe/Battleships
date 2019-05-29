import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class BattleshipsGame {
	Random rand;
	public BattleshipsBoard[] battleBoards;
	int width, height;
	BattleshipsGame(int w, int h){
		this.battleBoards = new BattleshipsBoard[2];
		for(int i=0;i<2;i++) {
			this.battleBoards[i] = new BattleshipsBoard(w,h);
		}
		this.width = w;
		this.height = h;
		this.rand = new Random();
	}
	
	public int winner() {
		if(battleBoards[0].allHit()) {
			return -1;
		}else if(battleBoards[1].allHit()) {
			return 1;
		}
		return 0;
	}
	
	
	void runGameConsole() {
		if(width<=0||height<=0) {
			return;
		}
		generateShips();
		Scanner s = new Scanner(System.in);
		sysout("Welcome to Battleships!");
		while(true) {

//			battleBoards[0].consoleMap();
//			battleBoards[1].consoleMap();
			sysout("Please enter a letter then a number from A0 to " + Coordinate.coordinateFromVector(width-1, height-1));
			String in = s.nextLine();
			Vector firePosition = Coordinate.coordinateStringToVector(in);
			while(!battleBoards[1].isInRangeAndNotAlreadyHit(firePosition)) {
				if(battleBoards[1].isInRange(firePosition)) {
					sysout("Admiral? The position \"" + Coordinate.coordinateFromVector(firePosition) + "\" has already been fired upon.");
				}else {
					sysout("The position: \"" + Coordinate.coordinateFromVector(firePosition) + "\" is not a valid position");
				}
				sysout("Please enter a letter then a number from A0 to " +Coordinate.coordinateFromVector(width-1, height-1));
				in = s.nextLine();
				firePosition = Coordinate.coordinateStringToVector(in);
			}
			//sysout(firePosition.toString());
			if(battleBoards[1].hit(firePosition.x, firePosition.y)) {
				switch(rand.nextInt(4)) {
				case 0:
					sysout("DIRECT HIT! SIR!");
					break;
				case 1:
					sysout("Hit, sir.");
					break;
				case 2:
					sysout("We got em");
					break;
				case 3:
					sysout("We hit something!");
					break;
				}
			}else {
				switch(rand.nextInt(4)) {
				case 0:
					sysout("We're hitting nothing, sir.");
					break;
				case 1:
					sysout("A Miss, sir.");
					break;
				case 2:
					sysout("We're firing at open ocean, sir.");
					break;
				case 3:
					sysout("Miss, sir.");
					break;
				}
			}
			
			if(battleBoards[1].allHit()) {
				sysout("We have destroyed the enemy fleet, Admiral. We are victorious.");
				break;
			}
			
			
			//computer turn
			computerTurn();
			if(battleBoards[0].allHit()) {
				sysout("Admiral, we have lost contact with the fleet. The battle is lost.");
				break;
			}
		}
	}
	
	
	
	void computerTurn() {
		Vector finalFire = pBoard.getHighestProbability(battleBoards[0]);
		
		//should have firePosition
		if(battleBoards[0].hit(finalFire.x, finalFire.y)) {
			switch(rand.nextInt(4)) {
			case 0:
				sysout("The enemy fired upon one of our vessels at " + Coordinate.coordinateFromVector(finalFire) + ",sir.");
				break;
			case 1:
				sysout("They hit us at position " + Coordinate.coordinateFromVector(finalFire) + ", sir.");
				break;
			case 2:
				sysout("WE'VE BEEN HIT AT " + Coordinate.coordinateFromVector(finalFire) + "!");
				break;
			case 3:
				sysout("TAKING FIRE, " + Coordinate.coordinateFromVector(finalFire));
				break;
			}
		}else {
			switch(rand.nextInt(4)) {
			case 0:
				sysout("An enemy shell impacted position " + Coordinate.coordinateFromVector(finalFire) + ", missing, sir.");
				break;
			case 1:
				sysout("They missed, " + Coordinate.coordinateFromVector(finalFire));
				break;
			case 2:
				sysout("They hit nothing at " + Coordinate.coordinateFromVector(finalFire));
				break;
			case 3:
				sysout("Missed us, " + Coordinate.coordinateFromVector(finalFire));
				break;
			}
		}

		pBoard.shipHit(battleBoards[0]);
		
	}

	
	void sysout(String s) {
		System.out.println(s);
	}
	
	void generateShips() {
		//Both battleboards
		
		//2 - destroyers
		//3 light cruisers
		//4 battleships
		//5 super heavy battleship
		for(int i=0;i<2;i++) {
			battleBoards[i].findAndAdd(i+1, 2); 
			battleBoards[i].findAndAdd(i+1, 2); 
			battleBoards[i].findAndAdd(i+1, 3); 
			battleBoards[i].findAndAdd(i+1, 3); 
			battleBoards[i].findAndAdd(i+1, 4); 
			battleBoards[i].findAndAdd(i+1, 5); 
		}
		
	}
	
	JButton[][] myShipsPanel;
	JLabel message;
	JLabel victoryMessage;
	void runGameWindow() {
		pBoard = new ProbabilityBoard(width,height);
		
		if(width<=0||height<=0) {
			return;
		}
		generateShips();
        JFrame f=new JFrame();
        f.setSize((2*width+2)*60,(2+height)*60);  
        f.getContentPane().setLayout(null);
        message = new JLabel("Default");
        message.setFont(new Font(message.getFont().getFontName(),Font.PLAIN, 24));
        message.setBounds(40, 30+height*60,width*60,36);
        f.add(message);
        myShipsPanel = new JButton[width][height];
        
        victoryMessage = new JLabel();
        victoryMessage.setFont(new Font(victoryMessage.getFont().getFontName(),Font.PLAIN, 24));
        victoryMessage.setBounds(width*60+80, 30+height*60,width*60,36);
    	victoryMessage.setText("Default");
    	f.add(victoryMessage);
        //myBoard
        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
        		JButton b=new JButton(Coordinate.coordinateFromVector(x, y));  
        	    b.setBounds(30+x*60,30+y*60,60,60);  
        	    if(battleBoards[0].board[x][y].occupied) {
            	    b.setBackground(Color.GREEN);
        	    }
        	    b.setEnabled(false);
        	    f.add(b);
        	    myShipsPanel[x][y] = b;
            }
        }
        //Computer board
        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {

        		JButton b=new JButton(Coordinate.coordinateFromVector(x, y));  
        	    b.setBounds(60+width*60+x*60,30+y*60,60,60);  
        	    b.addActionListener(new ButtonClickListenerEnemy(new Vector(x,y)));
        	    f.add(b);
            }
        }
        
	    
        f.setVisible(true);
//        while(true) {
//        	
//        }
	}

	
	private class ButtonClickListenerEnemy implements ActionListener{

		Vector p;
		public ButtonClickListenerEnemy(Vector p){
			this.p = p;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
            int victor = winner();
            if(victor!=0) {
            	//game already over
            	return;
            }
			Object source = e.getSource();
            battleBoards[1].hit(p.x, p.y);
            boolean enemyShip = battleBoards[1].board[p.x][p.y].occupied;
            if (source instanceof Component) {
            	((Component)source).setEnabled(false);
        		if(enemyShip) {
            		((Component)source).setBackground(Color.RED);
            	}
            }
            victor = winner();
            if(victor!=0) {
            	//player won
            	victoryMessage.setText("You won!");
            	return;
            }
            victoryMessage.setText(battleBoards[1].getStatus(true));
			windowCompTurn();
            victor = winner();
            if(victor!=0) {
            	//computer won
            	victoryMessage.setText("Game over. You lose.");
            	return;
            }
            message.setText(battleBoards[0].getStatus(false));
		}
	}
	
	private ProbabilityBoard pBoard;
	
	private void windowCompTurn() {
		Vector finalFire = pBoard.getHighestProbability(battleBoards[0]);
		
		if(battleBoards[0].hit(finalFire.x, finalFire.y)) {
			myShipsPanel[finalFire.x][finalFire.y].setBackground(new Color(128,0,0));
		}else {
			myShipsPanel[finalFire.x][finalFire.y].setBackground(new Color(0,0,128));
		}
		
		pBoard.shipHit(battleBoards[0]);
	}
	
}
