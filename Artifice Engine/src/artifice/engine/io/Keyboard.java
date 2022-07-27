package artifice.engine.io;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
	
	private long window;
	private boolean[] keys;
	
	public Keyboard(long window) {
		this.window = window;
		
		keys = new boolean[GLFW_KEY_LAST];
		for (int i = 0; i < GLFW_KEY_LAST; i++) keys[i] = false;
	}
	
	public void input() {
		for (int i = 0; i < keys.length; i++) keys[i] = isKeyDown(i);
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
}
