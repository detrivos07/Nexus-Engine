package nexus.engine.core.io.camera;

public class Camera3D extends Camera {
	
	public void move(float xOff, float yOff, float zOff, float step) {
		xOff *= step;
		yOff *= step;
		zOff *= step;
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
}
