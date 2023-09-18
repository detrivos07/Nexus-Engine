package artifice.engine.io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import artifice.engine.Engine;

public class Window {
	
	private long window;
	
	private int width, height;
	private String title;
	private boolean fullscreen;
	
	private boolean hasResized = false;
	
	private GLFWWindowSizeCallback wsc;
	
	public Window(int width, int height, String title) {
		this(width, height, title, false);
	}
	
	public Window(int width, int height, String title, boolean f) {
		if (!glfwInit()) throw new IllegalStateException("Unable to initalize GLFW");
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = f;
		
		createWindow();
	}
	
	public void update() {
		hasResized = false;
		if (glfwWindowShouldClose(window)) Engine.running = false;
	}
	
	public void restore() {
		glEnable(GL_BLEND);
		glEnable(GL_STENCIL_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void postRender() {
		glfwSwapBuffers(window);
	}
	
	public void destroy() {
		glfwTerminate();
	}
	
	void createWindow() {
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		this.window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0) throw new IllegalStateException("Unable to create GLFW window");
		
		if (!fullscreen) {
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		}
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glEnable(GL_BLEND);
		glEnable(GL_STENCIL_TEST);
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0.2f, 0.5f, 0.8f, 1.0f);
		setLocalCallbacks();
	}
	
	void setLocalCallbacks() {
		wsc = new GLFWWindowSizeCallback() {
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				hasResized = true;
			}
		};
		
		glfwSetWindowSizeCallback(window, wsc);
	}
	
	//SETTERS
	public void setClearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	//GETTERS
	public boolean isFullscreen() {
		return fullscreen;
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
	
	public long getWindow() {
		return window;
	}
	
	public boolean hasResized() {
		return hasResized;
	}
}
