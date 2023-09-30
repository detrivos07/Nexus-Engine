package nexus.engine.core.io.camera;

import org.joml.Vector3f;

public class Camera {
	private static Camera camera;
	
	protected final Vector3f pos, rot;
	
	public Camera() {
		pos = new Vector3f();
		rot = new Vector3f();
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.pos = pos;
		this.rot = rot;
	}
	
	//SETTERS
	public void setPos(float x, float y, float z) {
		pos.set(x, y, z);
	}
	
	public void setRot(float x, float y, float z) {
		rot.set(x, y, z);
	}
	
	//GETTERS
	public Vector3f getPos() {
		return pos;
	}
	
	public Vector3f getRot() {
		return rot;
	}
	
	public static Camera getInstance(Camera camera) {
		Camera.camera = camera;
		return Camera.camera;
	}
	
	public static Camera getInstance() {
		return camera;
	}
}
