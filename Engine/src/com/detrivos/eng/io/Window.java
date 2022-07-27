package com.detrivos.eng.io;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Dimension;

import org.lwjgl.glfw.*;

import com.detrivos.eng.handle.Handler;
import com.detrivos.eng.render.MasterRenderer;

public class Window {

	private long window;
	private String title;
	private boolean fullscreen;
	
	private boolean hasResized = false;
	
	private Dimension size = new Dimension();
	
	private GLFWWindowSizeCallback wsc;
	private GLFWCursorPosCallback cpc;
	
	public Window(Dimension size, String title, boolean f) {
		setSize(size);
		this.title = title;
		setFullscreen(f);
		createWindow();
	}
	
	private void createWindow() {
		this.window = glfwCreateWindow((int) size.getWidth(), (int) size.getHeight(), title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0) throw new IllegalStateException("Unable to initialize glfw window!");
		
//		if (!fullscreen) {
//			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//			glfwSetWindowPos(window, (vidmode.width() - (int) size.getWidth()) / 2, (int) (vidmode.height() - size.getHeight()) / 2);
//		}
		
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		MasterRenderer.prepareWindow();
		setLocalCallbacks();
	}
	
	public void tick() {
		hasResized = false;
		if (Handler.input.isKeyDown(GLFW_KEY_ESCAPE)) glfwSetWindowShouldClose(window, true);
		if (glfwWindowShouldClose(window)) Handler.running = false;
		glfwPollEvents();
	}
	
	public void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	private void setLocalCallbacks() {
		wsc = new GLFWWindowSizeCallback() {
			public void invoke(long argWindow, int argWidth, int argHeight) {
				size.setSize(argWidth, argHeight);
				hasResized = true;
			}
		};
		
		cpc = new GLFWCursorPosCallback() {
			public void invoke(long argWindow, double x, double y) {
				Handler.mousePos.set(x, y);
			}
		};
		
		glfwSetWindowSizeCallback(window, wsc);
		glfwSetCursorPosCallback(window, cpc);
	}
	//SETTERS
	public void setFullscreen(boolean f) {
		this.fullscreen = f;
	}
	
	public void setSize(Dimension size) {
		this.size.setSize(size);
	}
	
	public void setSize(int width, int height) {
		this.size.setSize(width, height);
	}
	//GETTERS
	public boolean hasResized() {
		return hasResized;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public long getWindow() {
		return window;
	}
}
