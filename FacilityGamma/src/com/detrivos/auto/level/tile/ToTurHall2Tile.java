package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class ToTurHall2Tile extends Tile {

	public ToTurHall2Tile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
