package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class FloorTile extends Tile {

	public FloorTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
