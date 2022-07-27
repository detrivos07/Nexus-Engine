package com.detrivos.game.level.tile;

public class TileTransition {
	
	public static TileTransition dirtToGrass = new TileTransition(Tile.dirt1, Tile.grass, Tile.dirtToGrass);

	private Tile[] trans;
	private Tile base;
	private Tile transTo;
	
	public TileTransition(Tile base, Tile transTo, Tile[] trans) {
		this.base = base;
		this.transTo = transTo;
		this.trans = trans;
	}

	public Tile[] getTrans() {
		return trans;
	}

	public Tile getBase() {
		return base;
	}

	public Tile getTransTo() {
		return transTo;
	}
}
