package com.detrivos.auto.entity.assets;

import java.util.List;

import com.detrivos.auto.audio.SoundClip;
import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.graphics.AnimatedSprite;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;
import com.detrivos.auto.projectile.Projectile;

public class Landmine extends Mob {
	
	public boolean exploded = false;
	public boolean hitPlayer = false;
	private boolean soundCreated = false;
	
	private int editX = random.nextInt(8);
	private int editY = random.nextInt(8);
	private int lx, ly;
	
	private SoundClip explode = new SoundClip("sounds/explosion1.wav");
	
	private AnimatedSprite anim = new AnimatedSprite(SpriteSheet.kaboom, 16, 16, 8);

	public Landmine(int x, int y, boolean grass) {
		this.x = lx = (x << 4) + editX;
		this.y = ly = (y << 4) + editY;
		if (grass) sprite = Sprite.landmineGrass;
		else sprite = Sprite.landmineDirt;
	}
	
	public void tick() {
		List<Projectile> projectiles = level.getProjectiles(this, 4);
		if (projectiles.size() > 0) {
			Projectile p = projectiles.get(0);
			p.remove();
			exploded = true;
		}
		
		if (exploded) {
			this.x = lx - 4;
			this.y = ly - 4;
			if (!soundCreated) {
				SoundClip.play(explode, -15.0f);
				soundCreated = true;
			}
			anim.tick();
			anim.setFrameRate(5);
			if (anim.getFrame() + 1 > 7) remove();
		}
	}

	public void render(Screen screen) {
		if (exploded) sprite = anim.getSprite();
		screen.renderMob((int) x, (int) y, sprite, this);
	}
}
