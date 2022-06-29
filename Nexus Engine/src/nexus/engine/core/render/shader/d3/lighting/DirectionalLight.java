package nexus.engine.core.render.shader.d3.lighting;

import org.joml.Vector3f;

public class DirectionalLight {

	private Vector3f colour, dir;
	private float intensity, shadowPosMul;
	private OrthoCoords ocoords;
	
	/**
	 * Creates a new DirectionalLight Object from data
	 * @param colour Colour of the new light
	 * @param dir Direction of the light
	 * @param intensity Intensity of the light
	 */
	public DirectionalLight(Vector3f colour, Vector3f dir, float intensity) {
		this.ocoords = new OrthoCoords();
		this.colour = colour;
		this.dir = dir;
		this.intensity = intensity;
		this.shadowPosMul = 1;
	}
	
	/**
	 * Creates a new DirectionalLight Object from another one
	 * @param light Light object to be modelled after
	 */
	public DirectionalLight(DirectionalLight light) {
		this(new Vector3f(light.getColour()), new Vector3f(light.getDir()), light.getIntensity());
	}
	
	//SETTERS
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	public void setDir(Vector3f dir) {
		this.dir = dir;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	public void setShadowPosMul(float shadowPosMul) {
		this.shadowPosMul = shadowPosMul;
	}
	
	public void setOrthoCoords(float l, float r, float b, float t, float n, float f) {
		ocoords.left = l;
		ocoords.right = r;
		ocoords.bottom = b;
		ocoords.top = t;
		ocoords.near = n;
		ocoords.far = f;
	}
	
	//GETTERS
	public Vector3f getColour() {
		return colour;
	}
	
	public Vector3f getDir() {
		return dir;
	}
	
	public float getIntensity() {
		return intensity;
	}
	
	public float getShadowPosMul() {
		return shadowPosMul;
	}
	
	public OrthoCoords getOrthoCoords() {
		return ocoords;
	}
	
	public static class OrthoCoords {
		public float left, right, bottom, top, near, far;
	}
}
