package nexus.engine.core.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {
	private static Keyboard keyboard;
	
	public static final int KEY_A = GLFW_KEY_A,
							KEY_B = GLFW_KEY_B,
							KEY_C = GLFW_KEY_C,
							KEY_D = GLFW_KEY_D,
							KEY_E = GLFW_KEY_E,
							KEY_F = GLFW_KEY_F,
							KEY_G = GLFW_KEY_G,
							KEY_H = GLFW_KEY_H,
							KEY_I = GLFW_KEY_I,
							KEY_J = GLFW_KEY_J,
							KEY_K = GLFW_KEY_K,
							KEY_L = GLFW_KEY_L,
							KEY_M = GLFW_KEY_M,
							KEY_N = GLFW_KEY_N,
							KEY_O = GLFW_KEY_O,
							KEY_P = GLFW_KEY_P,
							KEY_Q = GLFW_KEY_Q,
							KEY_R = GLFW_KEY_R,
							KEY_S = GLFW_KEY_S,
							KEY_T = GLFW_KEY_T,
							KEY_U = GLFW_KEY_U,
							KEY_V = GLFW_KEY_V,
							KEY_W = GLFW_KEY_W,
							KEY_X = GLFW_KEY_X,
							KEY_Y = GLFW_KEY_Y,
							KEY_Z = GLFW_KEY_Z,
							KEY_0 = GLFW_KEY_0,
							KEY_1 = GLFW_KEY_1,
							KEY_2 = GLFW_KEY_2,
							KEY_3 = GLFW_KEY_3,
							KEY_4 = GLFW_KEY_4,
							KEY_5 = GLFW_KEY_5,
							KEY_6 = GLFW_KEY_6,
							KEY_7 = GLFW_KEY_7,
							KEY_8 = GLFW_KEY_8,
							KEY_9 = GLFW_KEY_9,
							KEY_N0 = GLFW_KEY_KP_0,
							KEY_N1 = GLFW_KEY_KP_1,
							KEY_N2 = GLFW_KEY_KP_2,
							KEY_N3 = GLFW_KEY_KP_3,
							KEY_N4 = GLFW_KEY_KP_4,
							KEY_N5 = GLFW_KEY_KP_5,
							KEY_N6 = GLFW_KEY_KP_6,
							KEY_N7 = GLFW_KEY_KP_7,
							KEY_N8 = GLFW_KEY_KP_8,
							KEY_N9 = GLFW_KEY_KP_9,
							KEY_MINUS = GLFW_KEY_MINUS,
							KEY_ESC = GLFW_KEY_ESCAPE,
							KEY_EQUAL = GLFW_KEY_EQUAL,
							KEY_LESS = GLFW_KEY_LEFT_SUPER,
							KEY_MORE = GLFW_KEY_RIGHT_SUPER,
							KEY_COMMA = GLFW_KEY_COMMA,
							KEY_PERIOD = GLFW_KEY_PERIOD,
							KEY_SPACE = GLFW_KEY_SPACE,
							KEY_LSHIFT = GLFW_KEY_LEFT_SHIFT;
	
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
