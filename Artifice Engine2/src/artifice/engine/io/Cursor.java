package artifice.engine.io;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;
import org.lwjgl.glfw.*;

import artifice.engine.utils.AABB;

public class Cursor {
	
public static final float MOUSE_SENS = 0.2f;
	
	private int scroll;
	private final Vector2d screenPos, pos, lastPos;
	private final Vector2f displayVec;
	private boolean inWindow;
	
	private boolean[] buttons;
	
	private GLFWCursorPosCallback cpc;
	private GLFWScrollCallback swc;
	private GLFWCursorEnterCallback cec;
	private Window window;
	
	private AABB bb;
	private float bbrad = 1f;

	public Cursor(Window window) {
		this.window = window;
		lastPos = new Vector2d(-1, -1);
		screenPos = new Vector2d();
		pos = new Vector2d();
		bb = new AABB(AABB.getCenterPosFromTopLeft((float) screenPos.x, (float) screenPos.y, bbrad, bbrad), new Vector2f(bbrad));
		displayVec = new Vector2f();
		
		
		buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
		for (int i = 0 ; i < buttons.length; i++) buttons[i] = false;
	}
	
	public void init() {
		cpc = new GLFWCursorPosCallback() {
			public void invoke(long argWindow, double x, double y) {
				setPos(new Vector2d(y, x));//TODO:: Figure out why x and y are flipped
				screenPos.set(x, y);
			}
		};
		
		swc = new GLFWScrollCallback() {
			public void invoke(long argWindow, double x, double y) {
				scroll += y;
			}
		};
		
		cec = new GLFWCursorEnterCallback() {
			public void invoke(long argWindow, boolean entered) {
				inWindow = entered;
			}
		};
		
		glfwSetCursorPosCallback(window.getWindow(), cpc);
		glfwSetScrollCallback(window.getWindow(), swc);
		glfwSetCursorEnterCallback(window.getWindow(), cec);
	}
	
	public void input() {
		for (int i = 0; i < buttons.length; i++) buttons[i] = isButtonDown(i);
		glfwPollEvents();
		bb.setCenter(AABB.getCenterPosFromTopLeft((float) screenPos.x, (float) screenPos.y, bb.getHalfExtent().x, bb.getHalfExtent().y));
		
		displayVec.zero();
		if (lastPos.x > 0 && lastPos.y > 0 && inWindow) {
			double dx = pos.x - lastPos.x;
			double dy = pos.y - lastPos.y;
			if (dx != 0) displayVec.x = (float) dx;
			if (dy != 0) displayVec.y = (float) dy;
		}
		lastPos.set(pos);
	}
	
	public boolean isButtonDown(int button) {
		return glfwGetMouseButton(window.getWindow(), button) == 1;
	}
	
	public boolean isButtonPressed(int button) {
		return (isButtonDown(button) && !buttons[button]);
	}
	
	public boolean isButtonReleased(int button) {
		return (!isButtonDown(button) && buttons[button]);
	}
	
	public void destroy() {
		cpc.close();
	}
	
	//SETTERS
	public void setPos(Vector2d pos) {
		this.pos.set(pos);
	}
	
	public void setScroll(double y) {
		this.scroll += y;
	}
	
	//GETTERS
	public Vector2d getPos() {
		return pos;
	}
	
	public Vector2d getLastPos() {
		return lastPos;
	}
	
	public Vector2d getScreenPos() {
		return screenPos;
	}
	
	public int getScroll() {
		return scroll;
	}

	public boolean isInWindow() {
		return inWindow;
	}

	public Vector2f getDisplayVec() {
		return displayVec;
	}
	
	public AABB getBB() {
		return bb;
	}
}
