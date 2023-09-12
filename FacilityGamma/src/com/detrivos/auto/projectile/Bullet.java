package com.detrivos.auto.projectile;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.entity.assets.Player.Weapon;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.assets.Turret.Type;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class Bullet extends Projectile {
	
	public Bullet(double x, double y, double dir, Entity e) {
		super(x, y, dir);
		range = 125;
		speed = 4;
		damage = 17;
		fireRate = 40;
		sprite = Sprite.rotate(Sprite.bullet, dir);
		
		if (e instanceof Turret) {
			type = 1;
			if (((Turret) e).t == Type.NORMAL) projectileVariable(4, 150, 21, 35);
			if (((Turret) e).t == Type.MACHINE) projectileVariable(5, 200, 15, 6);
		} else {
			type = 0;
			if (((Player) e).w == Weapon.PISTOL) projectileVariable(4, 125, 17, 40);
			if (((Player) e).w == Weapon.SCATTER) projectileVariable(3, 70, 27, 64);
			if (((Player) e).w == Weapon.MACHINE) projectileVariable(5, 150, 12, 6);
		}
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void tick() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 0, 0, 0)) {
			remove();
		}
		move();
	}
	
	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}
	
	protected double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}
}
