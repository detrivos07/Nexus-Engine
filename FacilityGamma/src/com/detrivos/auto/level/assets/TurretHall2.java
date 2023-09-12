package com.detrivos.auto.level.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.assets.Turret.Type;
import com.detrivos.auto.entity.assets.drops.Medkit;
import com.detrivos.auto.entity.assets.drops.Medkit.Tier;
import com.detrivos.auto.entity.assets.story.ShotgunNPC;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.LevelIn;

public class TurretHall2 extends Level {

	public TurretHall2(String path) {
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
		
		add(new Turret(3, 8, Type.NORMAL));
		add(new Turret(11, 5, Type.NORMAL));
		add(new Turret(21, 2, Type.NORMAL));
		add(new Turret(25, 4, Type.NORMAL));
		add(new Turret(30, 10, Type.NORMAL));
		add(new Turret(28, 17, Type.NORMAL));
		add(new Turret(17, 16, Type.NORMAL));
		
		add(new Turret(9, 12, Type.MACHINE));
		add(new Turret(19, 7, Type.MACHINE));
		add(new Turret(24, 15, Type.MACHINE));
		
		add(new ShotgunNPC(16 * 14, 16 * 17));
		
		add(new Medkit(2, 19, Tier.LOW));
		add(new Medkit(2, 2, Tier.LOW));
		add(new Medkit(17, 3, Tier.LOW));
		add(new Medkit(29, 1, Tier.LOW));
		add(new Medkit(27, 6, Tier.LOW));
		add(new Medkit(29, 19, Tier.LOW));
	}
	
	protected void generateLevel() {
	}
}
