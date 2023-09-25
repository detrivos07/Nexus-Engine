package artifice.game;

import artifice.engine.*;
import artifice.engine.io.Camera;
import artifice.engine.io.Window;
import nexus.engine.core.io.Mouse;
import nexus.engine.sound.*;

public class Host implements IGameLogic {
	
	public static void main(String[] args) {
		IGameLogic game = new Host();
		Engine artifice = new Engine(game, "Host");
		Thread thread = new Thread(artifice, "ArtificeEngine");
		thread.start();
	}
	
	private Window window;
	private Renderer renderer;
	
	private DummyLevel level;
	
	private SoundManager sm;
	private Mouse mouse;
	
	MainMenu menu;
	
	private boolean inLevel = false;
	
	@Override
	public void init() {
		window = Window.getInstance();
		renderer = new Renderer();
		renderer.init(window);
		mouse = Mouse.getInstance();
		
		level = new DummyLevel(AssetLoader.loadAtlas("res/MapSet1"), "res/levels/test", 32);
		if (level != null) inLevel = true;
		
		sm = SoundManager.getInstance();
		sm.init();
		SoundBuffer sb = new SoundBuffer("res/blockPlace.ogg");
		sm.addBuffer(sb);
		Source s = new Source(false, false);
		s.setBuffer(sb.getID());
		s.setGain(0.3f);
		sm.addSource("place", s);
		
		SoundBuffer song = new SoundBuffer("res/testSong.ogg");
		sm.addBuffer(song);
		Source ss = new Source(true, false);
		ss.setBuffer(song.getID());
		ss.setGain(0.2f);
		sm.addSource("song", ss);
		
		//sm.playSource("song");
		
		//menu = new MainMenu(window);
	}
	
	@Override
	public void input(Camera camera) {
		if (inLevel) {
			level.calculateView(window);
			level.input(window, camera, mouse, sm);
		} else {
			if (menu.start()) {
				level = new DummyLevel(AssetLoader.loadAtlas("res/MapSet1"), "res/levels/test", 32);
				inLevel = true;
			}
		}
	}
	
	@Override
	public void update() {
		if (inLevel) level.update();
		else menu.update();
	}
	
	@Override
	public void render(Camera camera) {
		if (inLevel) {
			camera.correctCamera(window, level);
			renderer.render(window, camera, level);
		} else menu.render(window);
	}
	
	@Override
	public void destroy() {
		if (menu != null) menu.destroy();
		sm.destroy();
		if (level != null) level.destroy();
		renderer.destroy();
	}
}
