package artifice.engine;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import artifice.engine.io.*;

public class Engine implements Runnable {
	public static boolean running = false;
	
	private IGameLogic GAME;
	private String title;
	
	private Window window;
	private Keyboard board;
	private Cursor cursor;
	private Camera camera;
	
	@Override
	public void run() {
		running = true;
		System.getProperty("org.lwjgl.librarypath", new File("lib/natives/win64").getAbsolutePath());
		init();
		loop();
		destroy();
	}
	
	public Engine(IGameLogic game, String title) {
		GAME = game;
		this.title = title;
	}
	
	void init() {
		window = new Window(1600, 900, title);
		board = new Keyboard(window.getWindow());
		cursor = new Cursor(window);
		cursor.init();
		
		camera = new Camera(window.getWidth(), window.getHeight());
		
		GAME.init(window);
	}
	
	void input() {
		window.update();
		board.input();
		cursor.input();
		
		GAME.input(window, camera, board, cursor);
	}
	
	void update() {
		checkWindowSize();
		GAME.update(window);
	}
	
	void render() {
		GAME.render(window, camera);
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
