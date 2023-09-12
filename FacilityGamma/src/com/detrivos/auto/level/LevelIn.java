package com.detrivos.auto.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.assets.Landmine;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.spawners.LeecherSpawner;

public class LevelIn extends Level {

	public LevelIn(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(LevelIn.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION!!  NO LOAD LEVEL!");
		}
	}
	
	protected void generateLevel() {
	}
}
