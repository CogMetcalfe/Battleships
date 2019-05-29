

public class BattleShipsMain {

	public static void main(String[] args) {
		BattleshipsGame mainGame = new BattleshipsGame(10,10);
		mainGame.runGameWindow();
		BattleshipsGame consoleGame = new BattleshipsGame(10,10);
		mainGame.runGameConsole();
	}

}
