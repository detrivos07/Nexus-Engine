package artifice.engine;

import artifice.engine.io.Camera;
import artifice.engine.io.Window;
import nexus.engine.IProgram;
import nexus.engine.core.io.Keyboard;
import nexus.engine.core.io.Mouse;

public class Engine implements Runnable {
	private static Engine nexus;
	public static boolean running = false;
	
	private IProgram GAME;
	private Thread main;
	
	private Window window;
	private Camera camera;
	
	@Override
	public void run() {
		running = true;
		loop();
		destroy();
	}
	
	public Engine() {
		window = Window.getInstance();
	}
	
	public void init(IProgram program) {
		this.GAME = program;
		camera = Camera.getInstance();
		Keyboard.getInstance().init(window.getWindow());
		Mouse.getInstance().init(window.getWindow());
		GAME.init();
		
		main = new Thread(nexus, "Nexus");
		main.run();
	}
	
	void input() {
		window.update();
		GAME.input();
	}
	
	void update() {
		GAME.update();
	}
	
	void render() {
		GAME.render();
		window.postRender();
	}
	
	void destroy() {
		GAME.destroy();
		window.destroy();
	}
	
	void loop() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int fps = 0;
		int tps = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				input();
				update();
				tps++;
				delta--;
			}
			
			render();
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + fps + " TPS: " + tps);
				fps = 0;
				tps = 0;
			}
		}
	}
	
	public static Engine getInstance() {
		if (nexus == null) nexus = new Engine();
		return nexus;
	}
}
