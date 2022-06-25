package nexus.engine;

import java.io.IOException;

import com.google.common.flogger.FluentLogger;

import nexus.engine.core.io.DisplayManager;

public class Engine implements Runnable {
	
	public static boolean running = false;
	
	private IProgram PROGRAM;
	
	private DisplayManager display;

	@Override
	public void run() {
		running = true;
		init();
		loop();
		destroy();
	}
	
	public Engine(IProgram program) { 
		this.PROGRAM = program;
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
	void init() {
		display.init();
		PROGRAM.init(display);
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
}
