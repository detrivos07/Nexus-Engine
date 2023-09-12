package com.detrivos.auto.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImportUtils {

	public static void loadImage(String path, int[] pixels) {
		try {
			BufferedImage image = ImageIO.read(StoryUI.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("NO LOAD STORY_UI");
		}
	}
}
