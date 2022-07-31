package nexus.demo;

import nexus.engine.Engine;
import nexus.engine.IProgram;

public class Main {

	public static void main(String[] args) {
		IProgram program = new DemoGame();
		Engine nexus = Engine.getInstance();
		nexus.init(program);
		Thread thread = new Thread(nexus, "Nexus");//TODO @dvs:: Move to engine, not the demo
		thread.run();
	}
}
