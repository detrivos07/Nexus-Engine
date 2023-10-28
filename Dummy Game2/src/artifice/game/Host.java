package artifice.game;

import artifice.engine.io.Camera;
import artifice.engine.render.Renderer;
import artifice.engine.render.texture.TextureAtlas;
import nexus.engine.Engine;
import nexus.engine.IProgram;
import nexus.engine.core.io.DisplayManager;
import nexus.engine.core.io.Mouse;
import nexus.engine.core.sound.*;

public class Host implements IProgram {
	
	public static void main(String[] args) {
		Engine.getInstance().init(new Host());
	}
	
	private DisplayManager display;
	private Renderer renderer;
	private Camera camera;
	
	private DummyLevel level;
	
	private SoundManager sm;
	
	private Mouse mouse;
	
	MainMenu menu;
	
	private boolean inLevel = false;
	
	@Override
	public void init() {
		display = DisplayManager.getInstance();
		camera = Camera.getInstance();
		mouse = Mouse.getInstance();
		
		renderer = new Renderer();
		renderer.init();
		
		level = new DummyLevel(TextureAtlas.loadAtlas("res/MapSet1"), "res/levels/test", 32);
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
		
		menu = new MainMenu(display.getWindow());
	}
	
	@Override
	public void input() {//Updates as fast as renderer
		if (menu != null && !inLevel) menu.input();
	}
	
	@Override
	public void update() {
		if (inLevel) {
			level.calculateView(display.getWindow());
			level.input(display.getWindow(), camera, mouse, sm);
		} else {
			if (menu.start()) {
				level = new DummyLevel(TextureAtlas.loadAtlas("res/MapSet1"), "res/levels/test", 32);
				inLevel = true;
			}
		}
		
		if (inLevel) level.update();
		else menu.update();
	}
	
	@Override
	public void render() {
		if (inLevel) {
			camera.correctCamera(level);
			renderer.render(camera, level);
		} else menu.render(display.getWindow());
	}
	
	@Override
	public void destroy() {
		if (menu != null) menu.destroy();
		sm.destroy();
		if (level != null) level.destroy();
		renderer.destroy();
	}
}
