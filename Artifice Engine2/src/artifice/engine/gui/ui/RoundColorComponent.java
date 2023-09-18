package artifice.engine.gui.ui;

import static org.lwjgl.nanovg.NanoVG.*;

import org.joml.Vector2d;
import org.lwjgl.nanovg.NVGColor;

import artifice.engine.gui.*;
import artifice.engine.io.*;

public class RoundColorComponent extends ColorComponent {
	
	private float radius;
	
	public RoundColorComponent(GUI ui, GUIComponent comp, float x, float y, float rad, NVGColor col) {
		super(ui, comp, x - rad, y - rad, 0, 0, col);
		this.radius = rad;
	}
	
	@Override
	public void render(Window window) {
		nvgBeginPath(ui.getVG());
		nvgCircle(ui.getVG(), ui.getX() + comp.getX() + x, ui.getY() + comp.getY() + y, radius);
		nvgFillColor(ui.getVG(), active);
		nvgFill(ui.getVG());
	}
	
	public boolean isHovered(Cursor cursor) {
		Vector2d mp = cursor.getScreenPos();
		return Math.pow(mp.x - (ui.getX() + x), 2) + Math.pow(mp.y - (ui.getY() + y), 2) < Math.pow(radius, 2);
	}
	
	public float getRadius() {
		return radius;
	}
}
