package com.detrivos.game.level;

import java.awt.Dimension;
import java.util.*;

import org.joml.Vector2f;

import com.detrivos.game.level.feature.*;
import com.detrivos.game.level.tile.*;
import com.detrivos.game.utils.AABB;

public class LevelGenerator {
	
	private Random random  = new Random();
	
	private Dimension size;
	private LevelType lt;
	
	private int[] tiles;
	private Tile[] tilesAt;
	private AABB[] boundingBoxes;
	
	public Level level;
	public Feature[] featureMap;
	private BorderFeature bf = new BorderFeature(this, Tile.stone1);
	
	public LevelGenerator(Dimension size, LevelType lt) {
		this.featureMap = new Feature[(int) size.getWidth() * (int) size.getHeight()];
		this.size = size;
		this.lt = lt;
		this.level = null;
	}
	
	public LevelGenerator setLevel() {
		this.level = generateLevel();
		return this;
	}

	public Level generateLevel() {
		tiles = new int[(int) size.getWidth() * (int) size.getHeight()];
		tilesAt = new Tile[tiles.length];
		boundingBoxes = new AABB[tiles.length];
		
		switch (lt) {
		case TEST:
			generateTest();
			break;
		default:
			break;
		}
		
		return new Level(size, tiles, tilesAt, boundingBoxes);
	}
	
	private void generateTest() {
		int w = (int) size.getWidth();
		int h = (int) size.getHeight();
		bf.addToFeatureMap(featureMap, w, h);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Tile t;
				if (random.nextInt(3) % 2 == 0) t = initTile(x, y, w, Tile.grass.getID());// grass1
				else t = initTile(x, y, w, Tile.dirt1.getID());
				
				if (featureMap[x + y * w] instanceof BorderFeature) t = initTile(x, y, w, featureMap[x + y * w].getPrimary().getID());
				
				
				if (t != null) setTile(t, x, y);
			}
		}
		addPreGenFeature(PreGenFeature.test1, 1, 2, w);
		transitionTiles(TileTransition.dirtToGrass, w, h);
	}
	
	private void transitionTiles(TileTransition tt, int w, int h) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Tile t = getTilesAt(x, y);
				if (t.getID() == tt.getBase().getID()) {
					boolean[] around = new boolean[4];
					for (int i = 0; i < 9; i++) {
						if (i % 2 == 0) continue;
						
						int xi = (i % 3) - 1;
						int yi = (i / 3) - 1;
						
						Tile check = getTilesAt(x + xi, y + yi);
						if (check == null) continue;
						
						if (check.getID() == tt.getTransTo().getID()) {
							if (xi == -1) around[3] = true;
							else if (xi == 1) around[1] = true;
							if (yi == -1) around[0] = true;
							else if (yi == 1) around[2] = true;
						}
					}
					int numSides = 0;
					for (int i = 0; i < around.length; i++) if (around[i]) numSides++;
					
					Tile newTile = null;
					switch (numSides) {
					case 1:
						for (int i = 0; i < around.length; i++) {
							if (around[i]) {
								newTile = initTile(x, y, w, tt.getTrans()[i].getID());
								break;
							}
						}
						break;
					case 2:
						if (around[0] && around[2]) {
							newTile = initTile(x, y, w, tt.getTrans()[4].getID());
						} else if (around[1] && around[3]) {
							newTile = initTile(x, y, w, tt.getTrans()[5].getID());
						} else {
							for (int i = 0; i < around.length; i++) {
								if (i == 0) {
									if (around[i] && around[3]) {
										newTile = initTile(x, y, w, tt.getTrans()[6].getID());
									}
								} else if (around[i] && around[i - 1]) {
									newTile = initTile(x, y, w, tt.getTrans()[i + 6].getID());
								}
							}
						}
						break;
					case 3:
						for (int i = 0; i < around.length; i++) {
							if (!around[i]) {
								newTile = initTile(x, y, w, tt.getTrans()[10 + i].getID());
								break;
							}
						}
						break;
					case 4:
						newTile = initTile(x, y, w, tt.getTrans()[14].getID());
						break;
					}
					if(newTile != null) setTile(newTile, x, y);
				}
			}
		}
	}
	
	private void addPreGenFeature(PreGenFeature feature, int tx, int ty, int actw) {
		Tile[] featureTiles = feature.getTiles();
		int fw = feature.getW();
		int fh = feature.getH();
		
		for (int y = 0; y < fh; y++) {
			for (int x = 0; x < fw; x++) {
				initTile(tx + x, ty + y, actw, featureTiles[x + y * fw].getID());
				setTile(featureTiles[x + y * fw], tx + x, ty + y);
			}
		}
	}
	
	private Tile initTile(int x, int y, int w, int tile) {
		Tile t = Tile.tiles[tile];
		tilesAt[x + y * w] = t;
		return t;
	}
	
	public void setTile(Tile tile, int x, int y) {
		int w = (int) size.getWidth();
		tiles[x + y * w] = tile.getID();
		if (tile.isSolid()) boundingBoxes[x + y * w] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));
		else boundingBoxes[x + y * w] = null;
	}
	
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * (int) size.getWidth()]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile getTilesAt(int x, int y) {
		try {
			return tilesAt[x + y * (int) size.getWidth()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public AABB getTileBoundingBox(int x, int y) {
		try {
			return boundingBoxes[x + y * (int) size.getWidth()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
