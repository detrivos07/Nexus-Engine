package nexus.engine.core.gui;

import org.lwjgl.nanovg.NVGColor;

public class NVGUtils {

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
