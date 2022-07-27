package com.detrivos.eng.handle;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;

import org.joml.*;

import com.detrivos.eng.io.*;
import com.detrivos.eng.render.MasterRenderer;
import com.detrivos.entity.Player;
import com.detrivos.game.level.Level;

public class Handler {

	public static final String TITLE = "Engine V01";
	
	public static Vector2d mousePos = new Vector2d();
	public static Vector2d mouseWorldPos = new Vector2d();
	public static Dimension size = new Dimension(1600, 900);
	public static boolean running = false;
	public static boolean fullscreen = false;
	public static int absScale = 24;
	

	public static Window window;
	public static Input input;
	public static Camera camera;
	
	public static MasterRenderer mr;
	
	public static Level currentLevel;
	public static Player player;
	
	/**
	 * Initialises game classes
	 */
	public static void init() {
		window = new Window(size, TITLE, fullscreen);
		input = new Input(window.getWindow());
		camera = new Camera(size);
		
		mr = new MasterRenderer();
	}
	
	/**
	 * Destroys all game classes and cleans memory usage
	 */
	public static void destroy() {
		glfwTerminate();
	}
	
	public static void setMouseWorldPos() {
		float x = (float) ((2.0f * mousePos.x) / size.width - 1.0f);
		float y = (float) (1.0f - (2.0f * mousePos.y) / size.height);
		Vector4f clip = new Vector4f(x, y, -1f, 1f);
		Matrix4f invert = camera.getProjection().invert();
		Vector4f eye = invert.transform(clip);
		mouseWorldPos.set(eye.x / 24, eye.y / 24);
		System.out.println(mouseWorldPos.x + " " + mouseWorldPos.y);
	}
	
	public static void checkWindowSize() {
		if (window.hasResized()) {
			setSize(window.getSize());
			camera.setProjection((int) window.getSize().getWidth(), (int) window.getSize().getHeight());
			currentLevel.calculateView();
			glViewport(0, 0, (int) Handler.size.getWidth(), (int) Handler.size.getHeight());
		}
	}
	//SETTERS
	public static void setAbsScale(int newScale) {
		Handler.absScale = newScale;
	}
	
	public static void setSize(Dimension d) {
		Handler.size = d;
		window.setSize(Handler.size);
	}
	
	public static void setFullscreen(boolean f) {
		Handler.fullscreen = f;
		window.setFullscreen(Handler.fullscreen);
	}
}
