package artifice.game;

import artifice.engine.*;
import artifice.engine.io.*;
import artifice.engine.sound.*;

public class DummyGame implements IGameLogic {
	
	public static void main(String[] args) {
		String title = "Dummy Game";
		IGameLogic game = new DummyGame();
		Engine artifice = new Engine(game, title);
		Thread thread = new Thread(artifice, "ArtificeEngine");
		thread.start();
	}
	
	private Renderer renderer;
	
	private DummyLevel level;
	
	SoundManager sm;
	
	MainMenu menu;
	
	private boolean inLevel = false;
	
	@Override
	public void init(Window window) {
		renderer = new Renderer();
		renderer.init(window);
		
		level = new DummyLevel(AssetLoader.loadAtlas("res/MapSet1"), "res/levels/test", 32);
		if (level != null) inLevel = true;
		
		sm = new SoundManager();
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
	public void input(Window window, Camera camera, Keyboard keyboard, Cursor cursor) {
		if (inLevel) {
			level.calculateView(window);
			level.input(window, camera, keyboard, cursor, sm);
		} else {
			menu.input(keyboard, cursor);
			if (menu.start()) {
				level = new DummyLevel(AssetLoader.loadAtlas("res/MapSet1"), "res/levels/test", 32);
				inLevel = true;
			}
		}
	}
	
	@Override
	public void update(Window window) {
		if (inLevel) level.update();
		else menu.update();
	}
	
	@Override
	public void render(Window window, Camera camera) {
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
