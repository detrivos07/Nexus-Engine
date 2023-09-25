package artifice.engine.io;

import org.joml.*;

import artifice.engine.level.Level;
import nexus.engine.core.io.DisplayManager;
import nexus.engine.core.io.Window;

public class Camera {
	private static Camera camera;
	
	private Vector3f pos;
	private Matrix4f projMat;
	
	public Camera() {
		pos = new Vector3f(0, 0, 0);
		setProjection(DisplayManager.getInstance().getWindow().getWidth(), DisplayManager.getInstance().getWindow().getHeight());
	}
	
	public void setProjection(int width, int height) {
		projMat = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
	}
	
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public void addPos(Vector3f pos) {
		this.pos.add(pos);
	}
	
	public float getCameraLeft(int scale, int width) {
		return getPos().x - scale * (width / 2);
	}
	
	public float getCameraTop(int scale, int height) {
		return getPos().x - scale * (height / 2);
	}
	
	public Vector3f getPos() {
		return pos;
	}
	
	public Matrix4f getProjection() {
		return projMat.translate(pos, new Matrix4f());
	}
	
	public void correctCamera(Window window, Level level) {
		int w = (-level.getWidth()) * level.getScale() * 2;
		int h = (level.getHeight()) * level.getScale() * 2;
		
		int windowWidth = window.getWidth();
		int windowHeight = window.getHeight();
		
		if (pos.x > -(windowWidth / 2) + level.getScale()) pos.x = -(windowWidth / 2) + level.getScale();
		if (pos.x < w + (windowWidth / 2) + level.getScale()) pos.x = w + (windowWidth / 2) + level.getScale();
		if (pos.y < (windowHeight / 2) - level.getScale()) pos.y = (windowHeight / 2) - level.getScale();
		if (pos.y > h - (windowHeight / 2) - level.getScale()) pos.y = h - (windowHeight / 2) - level.getScale();
	}
	
	public static Camera getInstance() {
		if (camera == null) camera = new Camera();
		return camera;
	}
}
