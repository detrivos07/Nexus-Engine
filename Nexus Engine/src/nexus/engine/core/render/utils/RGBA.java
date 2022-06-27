package nexus.engine.core.render.utils;

public class RGBA {
	public static RGBA WHITE = new RGBA(255,255,255);
	public static RGBA BLACK = new RGBA(0,0,0);
	public static RGBA RED = new RGBA(255,0,0);
	public static RGBA GREEN = new RGBA(0,255,0);
	public static RGBA BLUE = new RGBA(0,0,255);
	public static RGBA YELLOW = new RGBA(0,255,255);
	
	private float r, g, b, a;
	
	public RGBA(float r, float g, float b) {
		rgba(r, g, b, 255.0f);
	}
	
	public RGBA(float r, float g, float b, float a) {
		rgba(r, g, b, a);
	}
	
	//SETTERS
	/**Sets the value of r*/
	public void r(float r) {
		this.r = r;
	}
	
	/**Sets the value of g*/
	public void g(float g) {
		this.g = g;
	}
	
	/**Sets the value of b*/
	public void b(float b) {
		this.b = b;
	}
	
	/**Sets the value of a*/
	public void a(float a) {
		this.a = a;
	}
	
	/**Sets the value of r, g, b, a*/
	public void rgba(float r, float g, float b, float a) {
		r(r);
		g(g);
		b(b);
		a(a);
	}
	
	//GETTERS
	public float r() {
		return r;
	}
	
	public float g() {
		return g;
	}
	
	public float b() {
		return b;
	}
	
	public float a() {
		return a;
	}
}
