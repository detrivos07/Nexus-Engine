package artifice.engine.gui;

import artifice.engine.io.*;

public abstract class GUIComponent {
	
	protected float x, y, w, h;
	protected GUI ui;
	
	public GUIComponent(GUI ui, float x, float y, float w, float h) {
		this.ui = ui;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void input(Keyboard board, Cursor cursor);
	public abstract void update();
	public abstract void render(Window window);
	public abstract void destroy();
	
	//GETTERS
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getW() {
		return w;
	}
	
	public float getH() {
		return h;
	}
}
