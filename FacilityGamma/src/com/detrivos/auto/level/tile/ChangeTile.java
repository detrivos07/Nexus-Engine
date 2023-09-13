package com.detrivos.auto.level.tile;

import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class ChangeTile extends Tile {

	public ChangeTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean solid() {
		boolean solid = true;
		if (Player.animNum == 2 || Player.animNum == 3) {
			solid = false;
			Player.gunThought = false;
		} else {
			solid = true;
			Player.gunThought = true;
		}
		return solid;
	}
}
