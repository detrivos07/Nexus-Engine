package nexus.engine.core.io;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.*;

public class Mouse {
	private static Mouse mouse;
	
	public static final float MOUSE_SENS = 0.2f;
	
	private final Vector2d pos, lastPos, scroll;
	private final Vector2f displayVec;
	private boolean inWindow = false;
	private int[] buttons;
	
	private GLFWMouseButtonCallback mbc;
	private GLFWCursorPosCallback cpc;
	private GLFWScrollCallback swc;
	private GLFWCursorEnterCallback cec;
	
	private Mouse() {
		pos = new Vector2d();
		lastPos = new Vector2d(-1);
		scroll = new Vector2d();
		displayVec = new Vector2f();
		
		buttons = new int[8];
		for (int i = 0 ; i < buttons.length; i++) buttons[i] = -1;
	}
	
	public void init(long window) {
		mbc = new GLFWMouseButtonCallback() {
			public void invoke(long argWindow, int button, int action, int mods) {
				setButtonState(button, action);
			}
		};
		
		cpc = new GLFWCursorPosCallback() {
			public void invoke(long argWindow, double x, double y) {
				setPos(x, y);
				displayVec.zero();
				if (lastPos.x > 0 && lastPos.y > 0 && inWindow) {
					double dx = y - lastPos.y;
					double dy = x - lastPos.x;
					if (dx != 0) displayVec.x = (float) dx;
					if (dy != 0) displayVec.y = (float) dy;
				}
			}
		};
		
		swc = new GLFWScrollCallback() {
			public void invoke(long argWindow, double x, double y) {
				setScroll(x, y);
			}
		};
		
		cec = new GLFWCursorEnterCallback() {
			public void invoke(long argWindow, boolean entered) {
				inWindow = entered;
			}
		};
		
		glfwSetMouseButtonCallback(window, mbc);
		glfwSetCursorPosCallback(window, cpc);
		glfwSetScrollCallback(window, swc);
		glfwSetCursorEnterCallback(window, cec);
	}
	
	public void destroy() {
		mbc.close();
		cpc.close();
		swc.close();
		cec.close();
	}
	
	//SETTERS
	public void setPos(double x, double y) {
		lastPos.set(pos.x, pos.y);
		pos.set(x, y);
	}
	
	public void setScroll(double x, double y) {
		scroll.set(x, y);
	}
	
	public void setButtonState(int key, int state) {
		buttons[key] = state;
	}
	
	//GETTERS
	public Vector2d getPos() {
		return pos;
	}
	
	public Vector2d getLastPos() {
		return lastPos;
	}
	
	public Vector2d getScroll() {
		return scroll;
	}
	
	public Vector2f getDisplayVec() {
		return displayVec;
	}
	
	public int check(int key) {
		return buttons[key];
	}
	
	public static Mouse getInstance() {
		if (mouse == null) mouse = new Mouse();
		return mouse;
	}
}
