package nexus.engine.core.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import nexus.engine.Engine;

public class Window {
	
	private int width = 1920;
	private int height = 1080;
	private String title = "";
	private String ctx = "";
	private boolean fullscreen = false;
	private boolean hasResized = false;
	
	private long window;
	private WindowContext context;
	
	private GLFWWindowSizeCallback wsc;
	
	public Window() {
		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
	}
	
	public void init() {
		this.window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0) throw new IllegalStateException("Unable to create GLFW window");
		
//		if (!fullscreen) {
//			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//			glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
//		}
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		setLocalCallbacks();
	}
	
	public void update(Keyboard board) {
		hasResized = false;
		if (board.check(GLFW_KEY_ESCAPE)) glfwSetWindowShouldClose(window, true);
		if (glfwWindowShouldClose(window)) Engine.running = false;
		glfwPollEvents();
	}
	
	public void render() {
		glfwSwapBuffers(window);
	}
	
	//TODO :: Window Icons
	
	public void destroy() {
		glfwSwapInterval(1);
		glfwTerminate();
	}
	
	//SETTERS *******************************************************************
	public void setHasResized(boolean hasResized) {
		this.hasResized = hasResized;
	}
	
	public void setContext(WindowContext context) {
		this.context = context;
	}
	
	//GETTERS ********************************************************************
	public boolean isHasResized() {
		return hasResized;
	}
	
	public WindowContext getContext() {
		return context;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCTX() {
		return ctx;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public long getWindow() {
		return window;
	}
	
	//LOCAL **********************************************************
	void setLocalCallbacks() {
		wsc = new GLFWWindowSizeCallback() {
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				context.updateViewport(argWidth, argHeight);
				hasResized = true;
				System.out.println("Window resized. W: " + argWidth + " H: " + argHeight);
			}
		};
		glfwSetWindowSizeCallback(window, wsc);
	}
}
