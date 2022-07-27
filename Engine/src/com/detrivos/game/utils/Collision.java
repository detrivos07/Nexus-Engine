package com.detrivos.game.utils;

import org.joml.Vector2f;

public class Collision {

	public Vector2f dist;
	public boolean isIntersecting;
	
	public Collision(Vector2f dist, boolean intersecting) {
		this.dist = dist;
		this.isIntersecting = intersecting;
	}
}
