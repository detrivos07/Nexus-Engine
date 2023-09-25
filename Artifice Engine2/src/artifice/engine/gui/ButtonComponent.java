package artifice.engine.gui;

import static artifice.engine.gui.NVGUtils.rgba;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import org.joml.Vector2d;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import artifice.engine.gui.ui.TextComponent;
import artifice.engine.render.texture.Texture;
import nexus.engine.core.io.*;

public class ButtonComponent extends GUIComponent {
	
	private NVGColor active, base, select;
	private int actTex = -1, baseTex = -1, selectTex = -1;
	private TextComponent text;
	
	private boolean colup = false, texup = false;
	
	public ButtonComponent(GUI ui, float x, float y, float w, float h) {
		super(ui, x, y, w, h);
	}
	
	public void initCols(NVGColor base, NVGColor select) {
		this.base = base;
		this.select = select;
		
		this.active = base;
		colup = true;
	}
	
	public void initTextures(Texture base, Texture select) {
		this.baseTex = nvglCreateImageFromHandle(ui.getVG(), base.getID(), base.getWidth(), base.getHeight(), NVG_IMAGE_FLIPY);
		this.selectTex = nvglCreateImageFromHandle(ui.getVG(), select.getID(), select.getWidth(), select.getHeight(), NVG_IMAGE_FLIPY);
		
		this.actTex = baseTex;
		texup = true;
	}
	
	@Override
	public void input(Mouse mouse) {
		if (isHovered(mouse))  {
			if (colup) active = rgba(select);
			if (texup) actTex = selectTex;
			if (text != null) text.hover = true;
			
			if (mouse.check(Mouse.BUTTON_1)) invoke();
			
		} else {
			if (colup) active = rgba(base);
			if (texup) actTex = baseTex;
			if (text != null) text.hover = false;
		}
	}
	
	public void invoke() {
	}
	@Override
	public void update() {
	}
	
	@Override
	public void render(Window window) {
		if (texup) {
			nvgBeginPath(ui.getVG());
			try (NVGPaint paint = NVGPaint.calloc()) {
				nvgImagePattern(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h, (float) Math.toRadians(90), actTex, 1, paint);
				nvgRect(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h);
				nvgFillPaint(ui.getVG(), paint);
			}
			nvgFill(ui.getVG());
		}
		
		if (colup) {
			nvgBeginPath(ui.getVG());
			nvgRect(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h);
			nvgFillColor(ui.getVG(), active);
			nvgFill(ui.getVG());
		}
		
		if (text != null) text.render(window);
	}
	
	@Override
	public void destroy() {
		if (baseTex != -1) nvgDeleteImage(ui.getVG(), baseTex);
		if (selectTex != -1) nvgDeleteImage(ui.getVG(), selectTex);
		if (actTex != -1) nvgDeleteImage(ui.getVG(), actTex);
	}
	
	public void setText(float x, float y, String words, float size, NVGColor base, NVGColor selected) {
		text = new TextComponent(ui, this, x, y, words, size, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, base, selected);
	}
	
	public void setText(String words, float size, NVGColor base, NVGColor selected) {
		text = new TextComponent(ui, this, w/2, h/2, words, size, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE, base, selected);
	}
	
	public boolean isHovered(Mouse cursor) {
		Vector2d mp = cursor.getScreenPos();
		if (mp.x > (ui.getX() + x) && mp.x < (ui.getX() + x) + w && mp.y > (ui.getY() + y) && mp.y < (ui.getY() + y) + h) return true; 
		return false;
	}
}
