package com.detrivos.auto.level.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.auto.entity.assets.CryoPod;
import com.detrivos.auto.entity.assets.Door;
import com.detrivos.auto.entity.assets.Leecher;
import com.detrivos.auto.entity.assets.drops.BulletDrops;
import com.detrivos.auto.entity.assets.drops.BulletDrops.BType;
import com.detrivos.auto.entity.assets.story.Note;
import com.detrivos.auto.entity.assets.story.Note.Type;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.LevelIn;

public class CryoRoom extends Level {

	public CryoRoom(String path) {
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
		
		add(new CryoPod(16 * 4, 16 * 3, true, null));
		add(new CryoPod(16 * 6, 16 * 3, false, Sprite.cryoPod1));
		add(new CryoPod(16 * 8, 16 * 3, false, Sprite.cryoPod4));
		add(new CryoPod(16 * 5, 16 * 7, false, Sprite.cryoPod3));
		add(new CryoPod(16 * 7, 16 * 7, false, Sprite.cryoPod2));
		
		add(new Note(16 * 7, 16 * 5, Type.CONTROLS, Sprite.note));
		add(new Note(16 * 21 - 7, 16 * 2 - 3, Type.CRYO, Sprite.cryonote));
		
		add(new Door(16 * 11, 16 * 5, true));
		add(new Door(16 * 22 + 4, 16 * 5, false));
		
		add(new BulletDrops(16 * 13, 16 * 8, BType.PISTOL, 15));
		add(new BulletDrops(16 * 20, 16 * 6, BType.PISTOL, 15));
		add(new BulletDrops(16 * 15, 16 * 2, BType.PISTOL, 15));
	}
	
	protected void generateLevel() {
	}
}
