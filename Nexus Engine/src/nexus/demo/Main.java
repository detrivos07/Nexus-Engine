package nexus.demo;

import nexus.engine.Engine;

public class Main {

	public static void main(String[] args) {
		Engine.getInstance().init(new DemoGame());
	}
}
