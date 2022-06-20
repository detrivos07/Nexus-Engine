package nexus.engine;

public class Engine implements Runnable {
	public static boolean running = false;
	
	private IProgram PROGRAM;

	@Override
	public void run() {
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
			//input here
			while (delta >= 1) {
				//update here
				
				ups++;
				delta--;
			}
			//render here
			
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
