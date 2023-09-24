package com.detrivos.auto.entity.assets.story;

import java.util.List;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.ui.StoryUI;

public class ShotgunNPC extends Entity {
	
	public boolean collided = false;
	private StoryUI ui = StoryUI.shotgunText;
	private boolean given = false;
	
	public ShotgunNPC(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = Sprite.shotgunNPC;
	}
	
	public void tick() {
		List<Player> players = level.getPlayers(this, 24);
		if (players.size() > 0) {
			collided = true;
			if (!given) {
				Player.scatterBullets += 300;
				given = true;
			}
		} else {
			collided = false;
		}
		Player.hasScatter = true;
	}
	
	public void render(Screen screen) {
		screen.renderMob((int) x - 4, (int) y - 9, sprite, this);
		if (collided) {
			screen.renderStoryUI((int) x - 60, (int) y - 95, ui, true);
		}
	}
}
