package artifice.engine.gui;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import artifice.engine.io.Window;
import artifice.engine.utils.LoaderUtils;
import nexus.engine.core.io.Mouse;

public class GUI {
	
	private long vg;
	private ByteBuffer font;
	
	private float x, y, w, h;
	
	private GUIComponent[] comps;
	
	public GUI(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.comps = new GUIComponent[50];
	}
	
	public void init(Window window) {
		this.vg = nvgCreate(NVG_STENCIL_STROKES);
		if (vg == NULL) throw new IllegalStateException("Could not init nanovg graphics");
		font = LoaderUtils.ioResourceToByteBuffer("/res/sagesans-Regular.ttf", 150 * 1024);
		int f = nvgCreateFontMem(vg, "FONT", font, 0);
		if (f == -1) throw new IllegalStateException("Could not generate NVG font");
	}
	
	public void input(Mouse mouse) {
		for (GUIComponent component : comps) if (component != null) component.input(mouse);
	}
	
	public void update() {
		for (GUIComponent component : comps) if (component != null) component.update();
	}
	
	public void render(Window window) {
		nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
		
		for (GUIComponent component : comps) if (component != null) component.render(window);
		
		nvgEndFrame(vg);
        window.restore();
	}
	
	public void destroy() {
		for (GUIComponent comp : comps) if (comp != null) comp.destroy();
        nvgDelete(vg);
    }
	
	public void addComponent(GUIComponent component, int priority) {
		if (comps[priority] != null) addComponent(component, priority+1);
		else comps[priority] = component;
	}
	
	//GETTERS
	public long getVG() {
		return vg;
	}
	
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
	
	public GUIComponent getComponent(int priority) {
		return comps[priority];
	}
	
	public GUIComponent[] getComponentArray() {
		return comps;
	}
}
