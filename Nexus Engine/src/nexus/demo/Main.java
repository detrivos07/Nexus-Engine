package nexus.demo;

import nexus.engine.Engine;
import nexus.engine.IProgram;

public class Main {

	public static void main(String[] args) {
		IProgram program = new DemoGame();
		Engine nexus = new Engine(program);
		nexus.run();
	}
}
