package com.detrivos.auto.projectile;

import com.detrivos.auto.audio.SoundClip;
import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.graphics.AnimatedSprite;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;

public class Rocket extends Projectile {
	
	public boolean exploded = false;
	public boolean didDamage = false;
	private boolean soundCreated = false;
	
	private AnimatedSprite anim = new AnimatedSprite(SpriteSheet.kaboom, 16, 16, 8);
	private SoundClip explode = new SoundClip("sounds/explosion1.wav");

	public Rocket(double x, double y, double dir, Entity e) {
		super(x, y, dir);
		range = 200;
		speed = 5;
		damage = 67;
		fireRate = 80;
		sprite = Sprite.rotate(Sprite.rocket, dir);
		
		if (e instanceof Turret) {
			type = 1;
			projectileVariable(5, 200, 82, 100);
		} else {
			type = 0;
			projectileVariable(5, 200, 82, 100);
		}
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void tick() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 0, 0, 0)) {
			exploded = true;
			if (anim.getFrame() >= 7) {
				remove();
			}
		}
		
		if (exploded) {
			if (!soundCreated) {
				soundCreated = true;
				SoundClip.play(explode, -10.0f);
			}
			anim.tick();
			anim.setFrameRate(5);
			if (anim.getFrame() >= 7) remove();
		}
		if (!exploded) move();
	}
	
	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) {
			exploded = true;
		}
	}
	
	protected double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
	public void render(Screen screen) {
		if (!exploded) screen.renderProjectile((int) x, (int) y, this);
		else if (exploded) {
			sprite = anim.getSprite();
			screen.renderMob((int) x - 8, (int) y - 8, sprite);
		}
	}
}
