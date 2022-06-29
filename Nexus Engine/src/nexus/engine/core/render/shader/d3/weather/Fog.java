package nexus.engine.core.render.shader.d3.weather;

import org.joml.Vector3f;

public class Fog {

	private boolean active;
	private Vector3f col;
	private float density;
	
	public static Fog NOFOG = new Fog();
	
	/**
	 * Generates a new fog instance with no fog (USED FOR LOCAL VAR: NOFOG)
	 */
	public Fog() {
		this(false, new Vector3f(0f), 0);
	}
	
	/**
	 * Creates a new fog instance with the given parameters
	 * @param active Whether or not the instance is active
	 * @param col The colour of the instance
	 * @param density The density of the instance
	 */
	public Fog(boolean active, Vector3f col, float density) {
		this.active = active;
		this.col = col;
		this.density = density;
	}
	
	/**
	 * Increments the density by the given amount
	 * @param inc Amount to increment density by (+/-)
	 */
	public void incDensity(float inc) {
		this.density += inc;
	}
	
	//SETTERS
	public void setActive(boolean a) {
		this.active = a;
	}
	
	public void setCol(float r, float g, float b) {
		this.col.set(r,g,b);
	}
	
	public void setCol(Vector3f col) {
		this.col.set(col);
	}
	
	public void setDensity(float d) {
		this.density = d;
	}
	
	//GETTERS
	public boolean isActive() {
		return active;
	}
	
	public Vector3f getCol() {
		return col;
	}
	
	public float getDensity() {
		return density;
	}
}
