package com.detrivos.game.utils;

import org.joml.Vector2f;

public class AABB {

	private Vector2f center, halfExtent;
	
	public AABB(Vector2f center, Vector2f halfExtent) {
		this.center = center;
		this.halfExtent = halfExtent;
	}
	
	public Collision getCollision(AABB box) {
		Vector2f dist = box.center.sub(center, new Vector2f());
		dist.x = (float) Math.abs(dist.x);
		dist.y = (float) Math.abs(dist.y);
		
		dist.sub(halfExtent.add(box.halfExtent, new Vector2f()));
		
		return new Collision(dist, dist.x < 0 && dist.y < 0);
	}
	
	public void correctPos(AABB box, Collision data) {
		Vector2f correction = box.center.sub(center, new Vector2f());
		if (data.dist.x > data.dist.y) {
			if (correction.x > 0) center.add(data.dist.x, 0);
			else center.add(-data.dist.x, 0);
		} else {
			if (correction.y > 0) center.add(0, data.dist.y);
			else center.add(0, -data.dist.y);
		}
	}
	
	public Vector2f getCenter() {
		return center;
	}
	
	public Vector2f getHalfExtent() {
		return halfExtent;
	}
}
