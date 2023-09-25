package artifice.engine;

import static org.lwjgl.opengl.GL11.glViewport;

import artifice.engine.io.Camera;
import artifice.engine.io.Window;
import nexus.engine.core.io.Keyboard;
import nexus.engine.core.io.Mouse;

public class Engine implements Runnable {
	public static boolean running = false;
	
	private IGameLogic GAME;
	private String title;
	
	private Window window;
	private Camera camera;
	
	@Override
	public void run() {
		running = true;
		init();
		loop();
		destroy();
	}
	
	public Engine(IGameLogic game, String title) {
		GAME = game;
		this.title = title;
	}
	
	void init() {
		window = Window.getInstance();
		Keyboard.getInstance().init(window.getWindow());
		Mouse.getInstance().init(window.getWindow());
		
		camera = new Camera(window.getWidth(), window.getHeight());
		
		GAME.init();
	}
	
	void input() {
		window.update();
		
		GAME.input(camera);
	}
	
	void update() {
		checkWindowSize();
		GAME.update();
	}
	
	void render() {
		GAME.render(camera);
		window.postRender();
	}
	
	void destroy() {
		GAME.destroy();
		window.destroy();
	}
	
	void checkWindowSize() {
		if (window.hasResized()) {
			camera.setProjection(window.getWidth(), window.getHeight());
			
			glViewport(0, 0, window.getWidth(), window.getHeight());
		}
	}
	
	void loop() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int fps = 0;
		int tps = 0;
		checkWindowSize();
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
}
