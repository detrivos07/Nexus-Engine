package nexus.engine;

import com.google.common.flogger.FluentLogger;

public class Engine implements Runnable {
	public static final FluentLogger logger = FluentLogger.forEnclosingClass();
	
	public static boolean running = false;
	
	private IProgram PROGRAM;

	@Override
	public void run() {
	}
	
	public Engine(IProgram program) { 
		this.PROGRAM = program;
	}
	
	/**
	 * Initializes the engine
	 * Primary try/catch loop
	 */
	void init() {
		PROGRAM.init();
	}
	
	/**
	 * Updates all input devices
	 */
	void input() {
		PROGRAM.input();
	}
	
	/**
	 * Updates all engine devices
	 */
	void update() {
		PROGRAM.update();
	}
	
	/**
	 * Clears viewport renderer, renders current scene, buffer swapper
	 */
	void render() {
		PROGRAM.render();
	}
	
	/**
	 * Destroys all engine objects
	 */
	void destroy() {
		PROGRAM.destroy();
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
