package com.detrivos.eng.handle;

import static com.detrivos.eng.handle.Handler.*;

import org.joml.Vector3f;

public class CameraHandler {

	public static void correctCamera() {
		Vector3f pos = camera.getPos();
		int w = ((int) -currentLevel.getDimension().getWidth()) * absScale * 2;
		int h = ((int) currentLevel.getDimension().getHeight()) * absScale * 2;
		
		int windowWidth = (int) window.getSize().getWidth();
		int windowHeight = (int) window.getSize().getHeight();
		
		if (pos.x > -(windowWidth / 2) + absScale) pos.x = -(windowWidth / 2) + absScale;
		if (pos.x < w + (windowWidth / 2) + absScale) pos.x = w + (windowWidth / 2) + absScale;
		if (pos.y < (windowHeight / 2) - absScale) pos.y = (windowHeight / 2) - absScale;
		if (pos.y > h - (windowHeight / 2) - absScale) pos.y = h - (windowHeight / 2) - absScale;
	}
}
