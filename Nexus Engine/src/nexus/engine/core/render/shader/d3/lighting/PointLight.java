package nexus.engine.core.render.shader.d3.lighting;

import org.joml.Vector3f;

public class PointLight {

	private Vector3f colour, pos;
	private Attenuation att;
	protected float intensity;

	public PointLight(Vector3f colour, Vector3f pos, float intensity) {
		att = new Attenuation(1, 0, 0);
		this.colour = colour;
		this.pos = pos;
		this.intensity = intensity;
	}
	
	public PointLight(Vector3f colour, Vector3f pos, float intensity, Attenuation att) {
		this(colour, pos, intensity);
		this.att = att;
	}
	
	public PointLight(PointLight light) {
		this(new Vector3f(light.getColour()), new Vector3f(light.getPos()), light.getIntensity(), light.getAttenuation());
	}
	
	//SETTERS
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	public void setAttenuation(Attenuation att) {
		this.att = att;
	}
	
	//GETTERS
	public Vector3f getColour() {
		return colour;
	}
	
	public Vector3f getPos() {
		return pos;
	}
	
	public Attenuation getAttenuation() {
		return att;
	}
	
	public float getIntensity() {
		return intensity;
	}
	
	public static class Attenuation {
		private float constant, linear, exponent;
		
		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}
		
		//SETTERS
		public void setConstant(float constant) {
			this.constant = constant;
		}
		
		public void setLinear(float linear) {
			this.linear = linear;
		}
		
		public void setExponent(float exponent) {
			this.exponent = exponent;
		}
		
		//GETTERS
		public float getConstant() {
			return constant;
		}
		
		public float getLinear() {
			return linear;
		}
		
		public float getExponent() {
			return exponent;
		}
	}
}
