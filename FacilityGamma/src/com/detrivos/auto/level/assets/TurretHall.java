package com.detrivos.auto.level.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.assets.Leecher;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.assets.Turret.Type;
import com.detrivos.auto.entity.assets.drops.Medkit;
import com.detrivos.auto.entity.assets.drops.Medkit.Tier;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.LevelIn;

public class TurretHall extends Level {

	public TurretHall(String path) {
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
		
		add(new Turret(10, 3, Type.NORMAL));
		add(new Turret(19, 4, Type.NORMAL));
		add(new Turret(37, 2, Type.NORMAL));
		add(new Turret(44, 5, Type.NORMAL));
		add(new Turret(47, 3, Type.NORMAL));
		
		add(new Medkit(18, 5, Tier.LOW));
		add(new Medkit(34, 2, Tier.LOW));
		add(new Medkit(48, 6, Tier.LOW));
	}
	
	protected void generateLevel() {
	}
}
