package artifice.engine.gui.ui;

import artifice.engine.gui.GUI;
import artifice.engine.gui.GUIComponent;
import nexus.engine.core.io.*;

public abstract class UIComponent {
	
	protected float x, y, w, h;
	protected GUI ui;
	protected GUIComponent comp;
	
	public UIComponent(GUI ui) {
		this(ui, 0, 0, ui.getW(), ui.getH());
	}
	
	public UIComponent(GUI ui, float x, float y, float w, float h) {
		this.ui = ui;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public UIComponent(GUI ui, GUIComponent comp) {
		this(ui, comp, 0, 0, ui.getW(), ui.getH());
		this.comp = comp;
	}
	
	public UIComponent(GUI ui, GUIComponent comp, float x, float y, float w, float h) {
		this.ui = ui;
		this.comp = comp;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void input(Keyboard board, Mouse mouse);
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
