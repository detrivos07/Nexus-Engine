package nexus.engine.core.render.opengl;

import org.joml.Vector4f;

public class Material {
private static final Vector4f DEFAULT_COL= new Vector4f(1.0f);
	
	private Vector4f ambientCol, diffuseCol, specCol;
	private float reflectance;
	private Texture texture, normalMap;
	
	/**
	 * Generates a Material object with no texture, no reflectance, and default colour values
	 */
	public Material() {
		this(DEFAULT_COL, DEFAULT_COL, DEFAULT_COL, null, 0.0f);
	}
	
	/**
	 * Generates a Material object with no texture, and on the given colour and reflectance values
	 * @param col Colour of the material
	 * @param reflectance Reflectance value of the material
	 */
	public Material(Vector4f col, float reflectance) {
		this(col, col, col, null, reflectance);
	}
	
	/**
	 * Generates a Material object with the given texture, no reflectance, and default colours
	 * @param t Texture
	 */
	public Material(Texture t) {
		this(DEFAULT_COL, DEFAULT_COL, DEFAULT_COL, t, 0);
	}
	
	/**
	 * Generates a Material object with the given texture and reflectance values, and the default color
	 * @param t Tecture
	 * @param reflectance Reflectance value of the material
	 */
	public Material(Texture t, float reflectance) {
		this(DEFAULT_COL, DEFAULT_COL, DEFAULT_COL, t, reflectance);
	}
	
	/**
	 * Generates a Material object with specific values for all aspects
	 * @param ambientCol Ambient light colour
	 * @param diffuseCol Diffuse light colour
	 * @param specCol Specular light colour
	 * @param t Texture
	 * @param reflectance Reflectance value of the material
	 */
	public Material(Vector4f ambientCol, Vector4f diffuseCol, Vector4f specCol, Texture t, float reflectance) {
		this.ambientCol = ambientCol;
		this.diffuseCol = diffuseCol;
		this.specCol = specCol;
		this.texture = t;
		this.reflectance = reflectance;
	}
	
	/**
	 * Destroys texture if there is one
	 */
	public void destroy() {
		if (isTextured()) texture.destroy();
	}
	
	//SETTERS
	public void setAmbientCol(Vector4f ambientCol) {
		this.ambientCol = ambientCol;
	}
	
	public void setDiffuseCol(Vector4f diffuseCol) {
		this.diffuseCol = diffuseCol;
	}
	
	public void setSpecCol(Vector4f specCol) {
		this.specCol = specCol;
	}
	
	public void setReflectance(float reflectance) {
		this.reflectance = reflectance;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setNormalMap(Texture nm) {
		this.normalMap = nm;
	}
	
	//GETTERS
	public Vector4f getAmbientCol() {
		return ambientCol;
	}
	
	public Vector4f getDiffuseCol() {
		return diffuseCol;
	}
	
	public Vector4f getSpecCol() {
		return specCol;
	}
	
	public float getReflectance() {
		return reflectance;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Texture getNormalMap() {
		return normalMap;
	}
	
	public boolean isTextured() {
		return texture != null;
	}
	
	public boolean hasNormalMap() {
		return normalMap != null;
	}
}
