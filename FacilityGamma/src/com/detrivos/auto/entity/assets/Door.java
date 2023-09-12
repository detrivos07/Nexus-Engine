package com.detrivos.auto.entity.assets;

import java.util.List;

import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.graphics.AnimatedSprite;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;

public class Door extends Mob {
	
	private boolean unlocked;

	private AnimatedSprite or = new AnimatedSprite(SpriteSheet.openRight, 32, 32, 6);
	private AnimatedSprite ol = new AnimatedSprite(SpriteSheet.openLeft, 32, 32, 6);
	private AnimatedSprite cr = new AnimatedSprite(SpriteSheet.closeRight, 32, 32, 6);
	private AnimatedSprite cl = new AnimatedSprite(SpriteSheet.closeLeft, 32, 32, 6);
	private AnimatedSprite anim = or;
	
	public Door(int x, int y, boolean unlocked) {
		this.x = x - 16;
		this.y = y - 16;
		this.unlocked = unlocked;
		sprite = Sprite.door;
	}
	
	public void tick() {
		List<Player> players = level.getPlayers(this, 40);
		if (players.size() > 0 && unlocked) {
			Player p = players.get(0);
			if (p.getX() < this.x + 8) {
				anim = or;
			}
			
			if (anim.getFrame() < 5) {
				anim.tick(); 
				anim.setFrameRate(5);
			} else {
				anim.setFrame(5);
			}
		}
		
		if (Player.animNum == 2 || Player.animNum == 3) unlocked = true;
	}

	public void render(Screen screen) {
		if (anim != null) sprite = anim.getSprite();
		else sprite = Sprite.door;
		screen.renderMob((int) x, (int) y, sprite, this);
	}
}
