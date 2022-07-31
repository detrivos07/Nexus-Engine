package nexus.engine.core.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {
	private static Keyboard keyboard;
	
	private int[] keys;
	
	private GLFWKeyCallback kcb;
	
	private Keyboard() {
		keys = new int[348];
		for (int i = 0; i < 348; i++) keys[i] = 0;
	}
	
	public void init(long window) {
		kcb = new GLFWKeyCallback() {
			@Override
			public void invoke(long argWindow, int key, int scancode, int action, int mods) {
				keys[key] = action;
			}
		};
		
		glfwSetKeyCallback(window, kcb);
	}
	
	//GETTERS **************************************************
	public boolean check(int key) {
		return keys[key] != 0 ? true : false;
	}
	
	public static Keyboard getInstance() {
		if (keyboard == null) keyboard = new Keyboard();
		return keyboard;
	}
}
