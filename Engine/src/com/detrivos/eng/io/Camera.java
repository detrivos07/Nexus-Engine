package com.detrivos.eng.io;

import java.awt.Dimension;

import org.joml.*;

import com.detrivos.eng.handle.Handler;

public class Camera {

	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(Dimension d) {
		position = new Vector3f(0, 0, 0);
		setProjection((int) d.getWidth(), (int) d.getHeight());
	}
	
	public void setProjection(int width, int height) {
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
	}
	
	public void setPos(Vector3f pos) {
		this.position = pos;
	}
	
	public void addPos(Vector3f pos) {
		this.position.add(pos);
	}
	
	public float getCameraLeft() {
		return getPos().x - Handler.absScale * (Handler.size.width / 2);
	}
	
	public float getCameraTop() {
		return getPos().x - Handler.absScale * (Handler.size.height / 2);
	}
	
	public Vector3f getPos() {
		return position;
	}
	
	public Matrix4f getProjection() {
		return projection.translate(position, new Matrix4f());
	}
}
