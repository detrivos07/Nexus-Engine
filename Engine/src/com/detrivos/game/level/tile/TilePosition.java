package com.detrivos.game.level.tile;

import java.util.List;

import org.joml.Vector2i;

import com.detrivos.eng.render.Texture;

public class TilePosition {

	public final String tileType;
	public Texture t;
	public List<Vector2i> positions;
	
	public TilePosition(Tile tile) {
		this.tileType = tile.getTexture().getPath();
		this.t = tile.getTexture();
	}
	
	public TilePosition(Tile tile, List<Vector2i> positions) {
		this.tileType = tile.getTexture().getPath();
		addPositions(positions);
	}
	
	public void addPositions(List<Vector2i> pos) {
		this.positions = pos;
	}
}
