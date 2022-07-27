package com.detrivos.game.level.feature;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.detrivos.game.level.*;
import com.detrivos.game.level.tile.Tile;

public class PreGenFeature {
	
	public static PreGenFeature test1 = new PreGenFeature("test1");
	
	private Tile[] tiles;
	private int w, h;

	public PreGenFeature(String path) {
		loadFeatureToArray(path);
	}
	
	private void loadFeatureToArray(String path) {
		try {
			BufferedImage lvl = ImageIO.read(Level.class.getResource("/levels/features/" + path + ".png"));
			w = lvl.getWidth();
			h = lvl.getHeight();
			
			int[] colSheet = lvl.getRGB(0, 0, w, h, null, 0, w);
			tiles = new Tile[w * h];
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int pixel = colSheet[x + y * w];
					
					Tile t;
					switch (pixel) {
					case 0xFFFFFFFF:
						t = Tile.grass;
						break;
					case 0xFFFFFF00:
						t = Tile.dirt1;
						break;
					case 0xFFFF9900:
						t = Tile.woodFloor1;
						break;
					case 0xFF000000:
						t = Tile.brick1;
						break;
					default:
						t = Tile.NA;
						break;
					}
					tiles[x + y * w] = t;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
