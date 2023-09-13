package nexus.gamma;

import nexus.engine.Engine;
import nexus.engine.IProgram;
import nexus.engine.core.io.*;

public class FacilityGamma implements IProgram {
	
	public static void main(String[] args) {
		Engine.getInstance().init(new FacilityGamma());
	}
	
	//Local references to singleton classes
	private Keyboard board;
	private Mouse mouse;

	public FacilityGamma() {
	}

	@Override
	public void init(DisplayManager display) {
		board = Keyboard.getInstance();
		mouse = Mouse.getInstance();
	}

	@Override
	public void input(DisplayManager display) {
	}

	@Override
	public void update(DisplayManager display) {
	}

	@Override
	public void render(DisplayManager display) {
	}

	@Override
	public void destroy() {
	}
}
