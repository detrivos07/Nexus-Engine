package com.detrivos;

import static com.detrivos.eng.handle.Handler.*;
import static com.detrivos.eng.handle.CameraHandler.*;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.Dimension;
import java.io.File;

import com.detrivos.game.level.*;

public class Game {

	public Game() {
		running = true;
		System.getProperty("org.lwjgl.librarypath", new File("lib/natives/windows").getAbsolutePath());
		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		init();
//		currentLevel = new Level(64, 64);
//		currentLevel = new Level("test");
		LevelGenerator lg = new LevelGenerator(new Dimension(64, 64), LevelType.TEST);
		currentLevel = lg.generateLevel();
		currentLevel.calculateView();
		
		loop();
		destroy();
	}
	
	public void loop() {
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
				tick();
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
	
	private void tick() {
		setMouseWorldPos();
		checkWindowSize();
		currentLevel.tick();
		correctCamera();
		window.tick();
	}
	
	private void render() {
		mr.render();
	}
}
