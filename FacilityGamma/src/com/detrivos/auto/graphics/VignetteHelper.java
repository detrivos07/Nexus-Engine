package com.detrivos.auto.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VignetteHelper {

	private String path;
	private final int WIDTH;
	private final int HEIGHT;
	public static BufferedImage titleBI;
	public static BufferedImage pausedBI;

	// public static VignetteHelper v = new
	// VignetteHelper("/textures/vingette.png", 400, 225);
	public static VignetteHelper title = new VignetteHelper("/textures/ui/title.png", 279, 86);
	public static VignetteHelper paused = new VignetteHelper("/textures/ui/paused.png", 405, 74);

	public VignetteHelper(String path, int width, int height) {
		this.path = path;
		HEIGHT = height;
		WIDTH = width;
		loadImage();
	}

	private void loadImage() {
		try {
			BufferedImage image = ImageIO.read(VignetteHelper.class.getResource(path));
			if (path == "/textures/ui/title.png") titleBI = image;
			if (path == "/textures/ui/paused.png") pausedBI = image;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("NO LOAD VIGNETTE");
		}
	}
}
