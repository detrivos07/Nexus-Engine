package com.detrivos.auto.level.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.assets.Turret.Type;
import com.detrivos.auto.entity.assets.drops.Medkit;
import com.detrivos.auto.entity.assets.drops.Medkit.Tier;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.LevelIn;

public class TurretHall3 extends Level {

	public TurretHall3(String path) {
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
		
		add(new Turret(6, 2, Type.NORMAL));
		add(new Turret(7, 14, Type.NORMAL));
		add(new Turret(16, 20, Type.NORMAL));
		add(new Turret(17, 10, Type.NORMAL));
		add(new Turret(18, 3, Type.NORMAL));
		add(new Turret(24, 9, Type.NORMAL));
		add(new Turret(28, 3, Type.NORMAL));
		
		add(new Turret(11, 1, Type.MACHINE));
		add(new Turret(10, 9, Type.MACHINE));
		add(new Turret(13, 16, Type.MACHINE));
		add(new Turret(5, 23, Type.MACHINE));
		add(new Turret(21, 5, Type.MACHINE));
		
		add(new Turret(22, 15, Type.ROCKET));
		add(new Turret(16, 4, Type.ROCKET));
		
		add(new Medkit(7, 4, Tier.LOW));
		add(new Medkit(2, 11, Tier.LOW));
		add(new Medkit(2, 23, Tier.LOW));
		add(new Medkit(18, 16, Tier.LOW));
		add(new Medkit(13, 10, Tier.LOW));
		add(new Medkit(14, 6, Tier.LOW));
		add(new Medkit(28, 1, Tier.LOW));
		add(new Medkit(25, 6, Tier.LOW));
		add(new Medkit(23, 12, Tier.MID));
	}
	
	protected void generateLevel() {
	}
}
