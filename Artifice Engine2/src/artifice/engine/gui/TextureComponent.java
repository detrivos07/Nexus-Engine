package artifice.engine.gui;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import org.lwjgl.nanovg.NVGPaint;

import artifice.engine.io.Window;
import artifice.engine.render.texture.Texture;
import nexus.engine.core.io.Mouse;

public class TextureComponent extends GUIComponent {

	private int t2;
	
	public TextureComponent(GUI ui, float x, float y, float w, float h) {
		super(ui, x, y, w, h);
	}
	
	public void initTextures(Texture t) {
		this.t2 = nvglCreateImageFromHandle(ui.getVG(), t.getID(), t.getWidth(), t.getHeight(), NVG_IMAGE_FLIPY);
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
		try (NVGPaint paint = NVGPaint.calloc()) {
			nvgImagePattern(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h, (float) Math.toRadians(90), t2, 1, paint);
			nvgRect(ui.getVG(), ui.getX() + x, ui.getY() + y, w, h);
			nvgFillPaint(ui.getVG(), paint);
		}
		nvgFill(ui.getVG());
	}

	@Override
	public void destroy() {
		nvgDeleteImage(ui.getVG(), t2);
		if (t2 != -1) nvgDeleteImage(ui.getVG(), t2);
	}
}
