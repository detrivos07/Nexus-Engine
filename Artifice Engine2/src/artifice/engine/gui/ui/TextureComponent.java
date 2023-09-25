package artifice.engine.gui.ui;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import org.lwjgl.nanovg.NVGPaint;

import artifice.engine.gui.GUI;
import artifice.engine.gui.GUIComponent;
import artifice.engine.render.texture.Texture;
import nexus.engine.core.io.*;

public class TextureComponent extends ColorComponent {
	
	private int t = -1, t2 = -1, activet = -1;
	private long vg;
	
	public TextureComponent(GUI ui, GUIComponent comp, Texture t) {
		super(ui, comp, rgba(0,0,0,0));
		this.vg = ui.getVG();
		this.t = nvglCreateImageFromHandle(vg, t.getID(), t.getWidth(), t.getHeight(), NVG_IMAGE_FLIPY);
	}

	public TextureComponent(GUI ui, GUIComponent comp, float x, float y, float w, float h, Texture t) {
		super(ui, comp, x, y, w, h, rgba(0,0,0,0));
		this.vg = ui.getVG();
		this.t = nvglCreateImageFromHandle(vg, t.getID(), t.getWidth(), t.getHeight(), NVG_IMAGE_FLIPY);
	}
	
	@Override
	public void input(Keyboard board, Mouse mouse) {
		if (isHoverable()) {
			if (isHovered(mouse)) activet = t2;
			else activet = t;
		}
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Window window) {
		nvgBeginPath(ui.getVG());
		try (NVGPaint paint = NVGPaint.calloc()) {
			nvgImagePattern(vg, ui.getX() + x, ui.getY() + y, w, h, (float) Math.toRadians(90), activet, 1, paint);
			nvgRect(vg, ui.getX() + comp.getX() + x, ui.getY() + comp.getY() + y, w, h);
			nvgFillPaint(vg, paint);
		}
		nvgFill(vg);
	}

	@Override
	public void destroy() {
		nvgDeleteImage(vg, t);
		nvgDeleteImage(vg, activet);
		if (t2 != -1) nvgDeleteImage(vg, t2);
	}
	
	public TextureComponent setHoverable(Texture t) {
		hoverable = true;
		this.t2 = nvglCreateImageFromHandle(vg, t.getID(), t.getWidth(), t.getHeight(), NVG_IMAGE_FLIPY);;
		return this;
	}
}
