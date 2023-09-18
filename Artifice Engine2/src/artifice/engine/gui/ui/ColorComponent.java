package artifice.engine.gui.ui;

import static org.lwjgl.nanovg.NanoVG.*;

import org.joml.Vector2d;
import org.lwjgl.nanovg.NVGColor;

import artifice.engine.gui.*;
import artifice.engine.io.*;

public class ColorComponent extends UIComponent {
	
	protected NVGColor active, colour, hovCol;
	protected boolean hoverable = false;
	
	public ColorComponent(GUI ui, NVGColor col) {
		super(ui);
		this.colour = col;
		this.active = col;
	}

	public ColorComponent(GUI ui, float x, float y, float w, float h, NVGColor col) {
		super(ui, x, y, w, h);
		this.colour = col;
		this.active = col;
	}
	
	public ColorComponent(GUI ui, GUIComponent comp, NVGColor col) {
		super(ui, comp);
		this.colour = col;
		this.active = col;
	}

	public ColorComponent(GUI ui, GUIComponent comp, float x, float y, float w, float h, NVGColor col) {
		super(ui, comp, x, y, w, h);
		this.colour = col;
		this.active = col;
	}

	@Override
	public void input(Keyboard board, Cursor cursor) {
		if (isHoverable()) {
			if (isHovered(cursor)) active = rgba(hovCol);
			else active = rgba(colour);
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Window window) {
		nvgBeginPath(ui.getVG());
		if (comp != null) nvgRect(ui.getVG(), ui.getX() + comp.getX() + x, ui.getY() + comp.getY() + y, w, h);
		else nvgRect(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h);
		nvgFillColor(ui.getVG(), active);
		nvgFill(ui.getVG());
	}

	@Override
	public void destroy() {
	}
	
	public ColorComponent setHoverable(NVGColor col) {
		hoverable = true;
		this.hovCol = col;
		return this;
	}
	
	public NVGColor getActiveCol() {
		return active;
	}
	
	public NVGColor getCol() {
		return colour;
	}
	
	public boolean isHoverable() {
		return hoverable;
	}
	
	public boolean isHovered(Cursor cursor) {
		Vector2d mp = cursor.getScreenPos();
		if (mp.x > (ui.getX() + comp.getX() + x) && mp.x < (ui.getX() + comp.getX() + x) + w && mp.y > (ui.getY() + comp.getY() + y) && mp.y < (ui.getY() + comp.getY() + y) + h) return true; 
		return false;
	}
	
	public NVGColor getHoveredColour() {
		return hovCol;
	}
	
	public static NVGColor rgba(int r, int g, int b, int a) {
		NVGColor colour = NVGColor.create();
		colour.r(r / 255.0f);
		colour.g(g / 255.0f);
		colour.b(b / 255.0f);
		colour.a(a / 255.0f);
		return colour;
	}
	
	public static NVGColor rgba(NVGColor col) {
		NVGColor colour = NVGColor.create();
		colour.r(col.r());
		colour.g(col.g());
		colour.b(col.b());
		colour.a(col.a());
		return colour;
	}
}
