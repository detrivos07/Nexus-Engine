package com.detrivos.game.level.feature;

import java.util.Random;

import com.detrivos.game.level.LevelGenerator;
import com.detrivos.game.level.tile.Tile;

public abstract class Feature {
	
	protected Random random = new Random();
	
	protected LevelGenerator lg;
	protected Tile primary;
	protected Tile[] secondary;
	
	public Feature() {
	}
	
	public Feature(LevelGenerator lg) {
		this.lg = lg;
	}
	
	public Feature(LevelGenerator lg, Tile primary) {
		this.lg = lg;
		this.primary = primary;
	}
	
	public Feature(LevelGenerator lg, Tile primary, Tile[] secondary) {
		this.lg = lg;
		this.primary = primary;
		this.secondary = secondary;
	}

	public abstract void addToFeatureMap(Feature[] map, int w, int h);
	
	protected Feature getAt(Feature[] map, int x, int y, int w) {
		try {
			return map[x + y * w];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile getPrimary() {
		return primary;
	}
}
