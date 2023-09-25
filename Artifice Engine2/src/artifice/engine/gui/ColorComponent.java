package artifice.engine.gui;

import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.nanovg.NVGColor;

import nexus.engine.core.io.*;

public class ColorComponent extends GUIComponent {
	
	protected NVGColor colour;

	public ColorComponent(GUI ui, float x, float y, float w, float h) {
		super(ui, x, y, w, h);
	}
	
	public void initCols(Window window, NVGColor colour) {
		this.colour = colour;
	}
	
	@Override
	public void input(Mouse mouse) {
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void render(Window window) {
		nvgBeginPath(ui.getVG());
		nvgRect(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h);
		nvgFillColor(ui.getVG(), colour);
		nvgFill(ui.getVG());
	}
	
	@Override
	public void destroy() {
	}
	
	public static NVGColor rgba(int r, int g, int b, int a) {
		NVGColor colour = NVGColor.create();
		colour.r(r / 255.0f);
		colour.g(g / 255.0f);
		colour.b(b / 255.0f);
		colour.a(a / 255.0f);
		return colour;
	}
}
