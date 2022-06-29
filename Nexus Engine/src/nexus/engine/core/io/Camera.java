package nexus.engine.core.io;

import org.joml.Vector3f;

public class Camera {
	private final Vector3f pos, rot;

	public Camera() {
		pos = new Vector3f();
		rot = new Vector3f();
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.pos = pos;
		this.rot = rot;
	}
	
	public void move(float xOff, float yOff, float zOff) {
		if (zOff != 0) {
			pos.x += (float) Math.sin(Math.toRadians(rot.y)) * -1.0f * zOff;
			pos.z += (float) Math.cos(Math.toRadians(rot.y)) * zOff;
		}
		if (xOff != 0) {
			pos.x += (float) Math.sin(Math.toRadians(rot.y - 90)) * -1.0f * xOff;
			pos.z += (float) Math.cos(Math.toRadians(rot.y - 90)) * xOff;
		}
		pos.y += yOff;
	}
	
	public void rotate(float xOff, float yOff, float zOff) {
		rot.x += xOff;
		rot.y += yOff;
		rot.z += zOff;
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
}
