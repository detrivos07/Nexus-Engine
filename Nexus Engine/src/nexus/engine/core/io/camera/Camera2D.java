package nexus.engine.core.io.camera;

public class Camera2D extends Camera {

	public float getCameraLeft(int scale, int width) {
		return getPos().x - scale * (width / 2);
	}
	
	public float getCameraTop(int scale, int height) {
		return getPos().x - scale * (height / 2);
	}
}
