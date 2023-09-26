package nexus.engine.core.gui.ui;

import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.nanovg.NVGColor;

import nexus.engine.core.gui.GUI;
import nexus.engine.core.gui.GUIComponent;
import nexus.engine.core.io.Window;

public class TextComponent {
	
	protected NVGColor base, selected;
	protected GUI ui;
	private GUIComponent comp;
	private float x, y;
	
	private String text;
	private float size;
	private int alignment;
	public boolean hover = false;
	
	public TextComponent(GUI ui, GUIComponent comp, float x, float y, String text, float size, int align, NVGColor col, NVGColor sel) {
		this.ui = ui;
		this.comp = comp;
		this.x = x;
		this.y = y;
		this.text = text;
		this.size = size;
		this.alignment = align;
		this.selected = sel;
		this.base = col;
	}
	
	public void render(Window window) {
		//nvgBeginPath(ui.getVG());
		//nvgRect(ui.getVG(), ui.getX(), ui.getY(), ui.getW(), ui.getH());
		nvgFontSize(ui.getVG(), size);
		nvgFontFace(ui.getVG(), "FONT");
		nvgTextAlign(ui.getVG(), alignment);
		nvgFillColor(ui.getVG(), hover ? selected : base);
		nvgText(ui.getVG(), ui.getX() + comp.getX() + x, ui.getY() + comp.getY() + y, text);
	}
	
	public void setText(String s) {
		this.text = s;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public String getText() {
		return text;
	}
	
	public float getSize() {
		return size;
	}
}
