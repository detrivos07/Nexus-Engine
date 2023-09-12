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

public class LeecherTunnel1 extends Level {

	public LeecherTunnel1(String path) {
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
		
		add(new Turret(9, 2, Type.NORMAL));
		add(new Turret(36, 6, Type.NORMAL));
		
		add(new Turret(14, 5, Type.MACHINE));
		
		add(new Leecher(24, 4));
		add(new Leecher(20, 8));
		
		add(new Medkit(3, 3, Tier.LOW));
		add(new Medkit(24, 1, Tier.LOW));
		add(new Medkit(25, 6, Tier.LOW));
		
		add(new Medkit(45, 4, Tier.MID));
	}
	
	protected void generateLevel() {
	}
}
