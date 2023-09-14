package nexus.engine;

import java.io.IOException;

import nexus.engine.core.io.*;

public class Engine implements Runnable {
	private static Engine nexus;
	
	public static boolean running = false;
	
	private IProgram PROGRAM;
	private Thread main;
	
	private DisplayManager display;

	@Override
	public void run() {
		running = true;
		loop();
		destroy();
	}
	
	private Engine() {
		try {
			this.display = new DisplayManager();
		} catch (IOException e) {
			System.out.println("NO LOAD DISPLAY");
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the engine
	 * Primary try/catch loop
	 */
	public void init(IProgram program) {
		this.PROGRAM = program;
		display.init();
		Keyboard.getInstance().init(display.getWindow().getWindow());
		Mouse.getInstance().init(display.getWindow().getWindow());
		PROGRAM.init(display);
		
		main = new Thread(nexus, "Nexus");
		main.run();
	}
	
	/**
	 * Updates all input devices
	 */
	void input() {
		display.update();
		PROGRAM.input(display);
	}
	
	/**
	 * Updates all engine devices
	 */
	void update() {
		PROGRAM.update(display);
	}
	
	/**
	 * Clears viewport renderer, renders current scene, buffer swapper
	 */
	void render() {
		display.preRender();
		PROGRAM.render(display);
		display.postRender();
	}
	
	/**
	 * Destroys all engine objects
	 */
	void destroy() {
		PROGRAM.destroy();
		display.destroy();
	}
	
	/**
	 * primary program loop
	 */
	void loop() {
		long last = System.nanoTime();
		long curr = System.currentTimeMillis();
		double nano = 1000000000.0 / 60.0;
		double delta = 0.0;
		int fps = 0;
		int ups = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - last) / nano;
			last = now;
			input();
			while (delta >= 1) {
				//update here
				update();
				ups++;
				delta--;
			}
			//render here
			render();
			fps++;
			if (System.currentTimeMillis() - curr > 1000) {
				System.out.println("FPS: " + fps + " | UPS: " + ups);
				curr += 1000;
				fps = 0;
				ups = 0;
			}
		}
	}
	
	public static Engine getInstance() {
		if (nexus == null) nexus = new Engine();
		return nexus;
	}
}
