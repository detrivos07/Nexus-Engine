package artifice;

import artifice.engine.*;
import artifice.game.DummyGame;

public class Main {
	public static void main(String[] args) {
		String title = "Dummy Game";
		IGameLogic game = new DummyGame();
		Engine artifice = new Engine(game, title);
		Thread thread = new Thread(artifice, "ArtificeEngine");
		thread.start();
	}
}
