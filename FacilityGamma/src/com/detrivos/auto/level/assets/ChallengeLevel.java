package com.detrivos.auto.level.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.spawners.TurretSpawner;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.LevelIn;
import com.detrivos.auto.modes.Challenge;

public class ChallengeLevel extends Level {

	public ChallengeLevel(String path) {
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
		
		add(new TurretSpawner(5 * 16, 5 * 16, 1, this));
	}
	
	protected void generateLevel() {
	}
}
