package com.detrivos.auto.entity.assets;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.graphics.AnimatedSprite;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;

public class CryoPod extends Entity {
	
	private boolean player = false;
	public boolean emerged = false;
	
	private AnimatedSprite emerge = new AnimatedSprite(SpriteSheet.emerge, 26, 26, 14);
	private AnimatedSprite anim = null;
	private Sprite s = null;

	public CryoPod(int x, int y, boolean player, Sprite sprite) {
		if (player) anim = emerge;
		else s = sprite;
		this.player = player;
		this.x = x - 13;
		this.y = y - 13;
	}
	
	public void tick() {
		if (player && !emerged) {
			if (!emerged) {
				anim.setFrameRate(20);
				if (anim.getFrame() < 12) anim.tick();
				else {
					emerged = true;
					Player.animFin = true;
				}
			}
		}
	}
	
	public void render(Screen screen) {
		if (player && !emerged) {
			sprite = anim.getSprite();
		} else if (player && emerged) {
			anim.setFrame(13);
			sprite = anim.getSprite();
		} else if (!player) {
			sprite = s;
		}
		screen.renderMob((int) x, (int) y, sprite, this);
	}
}
