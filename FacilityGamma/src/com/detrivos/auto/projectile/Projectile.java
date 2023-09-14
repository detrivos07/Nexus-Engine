package com.detrivos.auto.projectile;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny;
	protected double dist;
	public double speed, range, damage;
	public static int fireRate;
	public int type;
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void projectileVariable(int speed, int range, int damage, int fireRate) {
		if (speed != 0) this.speed = speed;
		if (range != 0) this.range = range;
		if (damage != 0) this.damage = damage;
		if (fireRate != 0) this.fireRate = fireRate;
	}
	
	protected void move() {
	}
}
