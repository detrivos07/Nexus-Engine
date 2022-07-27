package com.detrivos.entity.projectile;

import org.joml.*;

import com.detrivos.entity.Entity;
import com.detrivos.game.utils.*;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected float nx, ny;
	protected double dist;
	public double speed, range, damage;
	public static int fireRate;
	 
	public Projectile(Vector3f pos, double dir) {
		super(pos);
		xOrigin = pos.x;
		yOrigin = pos.y;
		angle = dir;
	}
	
	public Projectile varyProj(double speed, double range, double damage, int fireRate) {
		this.speed = speed;
		this.range = range;
		this.damage = damage;
		this.fireRate = fireRate;
		return this;
	}
	
	protected void checkTileCollision() {
		AABB[] boxes = new AABB[25];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				boxes[x + y * 5] = level.getTileBoundingBox(
							(int) (((pos.x / 2) + 0.5f) - (5 / 2)) + x,
							(int) (((-pos.y / 2) + 0.5f) - (5 / 2)) + y);
			}
		}
		
		AABB box = null;
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) { 
				if (box == null) box = boxes[i];
				
				Vector2f l1 = box.getCenter().sub(pos.x, pos.y, new Vector2f());
				Vector2f l2 = boxes[i].getCenter().sub(pos.x, pos.y, new Vector2f());
				
				if (l1.lengthSquared() > l2.lengthSquared()) box = boxes[i];
			}
		}
		
		if (box != null) {
			Collision data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				remove();
			}
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) { 
					if (box == null) box = boxes[i];
					
					Vector2f l1 = box.getCenter().sub(pos.x, pos.y, new Vector2f());
					Vector2f l2 = boxes[i].getCenter().sub(pos.x, pos.y, new Vector2f());
					
					if (l1.lengthSquared() > l2.lengthSquared()) box = boxes[i];
				}
			}
			
			data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				remove();
			}
		}
	}
	
	protected void move(float delta) {
	}
}
