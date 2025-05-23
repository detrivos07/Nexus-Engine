package nexus.gamma;

import nexus.engine.Engine;
import nexus.engine.IProgram;
import nexus.engine.core.io.Keyboard;
import nexus.engine.core.io.Mouse;
import nexus.engine.core.render.opengl.TextureManager;

public class FacilityGamma implements IProgram {
	
	public static void main(String[] args) {
		Engine.getInstance().init(new FacilityGamma());
	}
	
	//Local references to singleton classes
	private Keyboard board;
	private Mouse mouse;
	
	private TextureManager texManager;

	public FacilityGamma() {
	}

	@Override
	public void init() {
		board = Keyboard.getInstance();
		mouse = Mouse.getInstance();
	}

	@Override
	public void input() {
	}

	@Override
	public void update() {
	}

	@Override
	public void render() {
	}

	@Override
	public void destroy() {
	}
}
