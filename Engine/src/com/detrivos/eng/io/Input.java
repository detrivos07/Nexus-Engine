package com.detrivos.eng.io;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	private long window;
	private boolean[] keys, buttons;
	
	public Input(long window) {
		this.window = window;
		keys = new boolean[GLFW_KEY_LAST];
		buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
		
		for (int i = 0; i < GLFW_KEY_LAST; i++) keys[i] = false;
		for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) buttons[i] = false;
	}
	
	public void tick() {
		for (int i = 0; i < keys.length; i++) keys[i] = isKeyDown(i);
		for (int i = 0; i < buttons.length; i++) buttons[i] = isButtonDown(i);
	}
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isKeysDown(int... keys) {
		for (int key : keys) {
			if (isKeyDown(key)) return true;
		}
		return false;
	}
	
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) && keys[key]);
	}
	
	public boolean isButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public boolean isButtonPressed(int button) {
		return (isButtonDown(button) && !buttons[button]);
	}
	
	public boolean isButtonReleased(int button) {
		return (!isButtonDown(button) && buttons[button]);
	}
}
