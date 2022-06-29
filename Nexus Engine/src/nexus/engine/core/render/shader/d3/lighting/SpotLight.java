package nexus.engine.core.render.shader.d3.lighting;

import org.joml.Vector3f;

public class SpotLight {

	private PointLight point;
	private Vector3f dir;
	private float range;
	
	public SpotLight(PointLight point, Vector3f dir, float range) {
		setPoint(point);
		setDir(dir);
		setRangeAngle(range);
	}
	
	public SpotLight(SpotLight light) {
		this(new PointLight(light.getPoint()), new Vector3f(light.getDir()), 0);
		setRangeAngle(light.getRange());
	}
	
	public final void setRangeAngle(float range) {
		this.setRange((float) Math.cos(Math.toRadians(range)));
	}
	
	//SETTERS
	public void setPoint(PointLight point) {
		this.point = point;
	}
	
	public void setDir(Vector3f dir) {
		this.dir = dir;
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	//GETTERS
	public PointLight getPoint() {
		return point;
	}
	
	public Vector3f getDir() {
		return dir;
	}
	
	public float getRange() {
		return range;
	}
}
