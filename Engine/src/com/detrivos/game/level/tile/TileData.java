package com.detrivos.game.level.tile;

import org.joml.Vector2i;

public class TileData {

	private Vector2i pos;
	private float[] texUnits;
	
	public TileData(Vector2i pos, float[] texUnits) {
		this.pos = pos;
		this.texUnits = texUnits;
	}

	public Vector2i getPos() {
		return pos;
	}

	public float[] getTexUnits() {
		return texUnits;
	}
}
