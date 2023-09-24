package nexus.engine.core.io;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.*;

import nexus.engine.core.collision.AABB;

public class Mouse {
	private static Mouse mouse;
	
	public static final int BUTTON_1 = GLFW_MOUSE_BUTTON_1,
							BUTTON_2 = GLFW_MOUSE_BUTTON_2,
							BUTTON_3 = GLFW_MOUSE_BUTTON_3,
							BUTTON_4 = GLFW_MOUSE_BUTTON_4,
							BUTTON_5 = GLFW_MOUSE_BUTTON_5,
							BUTTON_6 = GLFW_MOUSE_BUTTON_6,
							BUTTON_7 = GLFW_MOUSE_BUTTON_7;
	
	public static final float MOUSE_SENS = 0.2f;
	
	private final Vector2d pos, screenPos, lastPos, scroll;
	private final Vector2f displayVec;
	private boolean inWindow = false;
	
	private int[] buttons;
	
	private GLFWMouseButtonCallback mbc;
	private GLFWCursorPosCallback cpc;
	private GLFWScrollCallback swc;
	private GLFWCursorEnterCallback cec;
	
	private AABB bb;
	private float bbrad = 1f;
	
	private Mouse() {
		pos = new Vector2d();
		screenPos = new Vector2d();
		lastPos = new Vector2d(-1);
		scroll = new Vector2d();
		bb = new AABB(AABB.getCenterPosFromTopLeft((float) screenPos.x, (float) screenPos.y, bbrad, bbrad), new Vector2f(bbrad));

		displayVec = new Vector2f();
		
		buttons = new int[8];
		for (int i = 0 ; i < buttons.length; i++) buttons[i] = 0;
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
				screenPos.set(x, y);
				displayVec.zero();
				if (lastPos.x > 0 && lastPos.y > 0 && inWindow) {
					double dx = y - lastPos.y;
					double dy = x - lastPos.x;
					if (dx != 0) displayVec.x = (float) dx;
					if (dy != 0) displayVec.y = (float) dy;
				}
				bb.setCenter(AABB.getCenterPosFromTopLeft((float) screenPos.x, (float) screenPos.y, bb.getHalfExtent().x, bb.getHalfExtent().y));
				lastPos.set(pos);
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
	
	public Vector2d getScreenPos() {
		return screenPos;
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
	
	public boolean check(int key) {
		return buttons[key] != 0 ? true : false;
	}
	
	public static Mouse getInstance() {
		if (mouse == null) mouse = new Mouse();
		return mouse;
	}
}
