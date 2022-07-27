package artifice.engine.utils;

import org.joml.Vector2f;

public class AABB {
	
	private Vector2f center, halfExtent;
	
	public AABB(Vector2f center, Vector2f halfExtent) {
		this.center = center;
		this.halfExtent = halfExtent;
	}
	
	public Collision getCollision(AABB box) {
		Vector2f dist = box.center.sub(center, new Vector2f());
		dist.x = Math.abs(dist.x);
		dist.y = Math.abs(dist.y);
		
		dist.sub(halfExtent.add(box.halfExtent, new Vector2f()));
		
		return new Collision(dist, dist.x < 0 && dist.y < 0);
	}
	
	public Collision checkOOBounds(AABB box) {
		Vector2f dist1 = box.center.sub(box.halfExtent, new Vector2f());
		dist1.sub(center.sub(halfExtent, new Vector2f()));
		Vector2f dist2 = box.center.add(box.halfExtent, new Vector2f());
		dist2.sub(center.add(halfExtent, new Vector2f()));
		System.out.println(dist1.x + " " + dist1.y + " " + dist2.x + " " + dist2.y);
		
		return null;
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
	
	//SETTERS
	public void setCenter(float x, float y) {
		this.center.set(x, y);
	}
	
	public void setCenter(Vector2f center) {
		this.center.set(new Vector2f(center));
	}
	
	//GETTERS
	public Vector2f getCenter() {
		return center;
	}
	
	public Vector2f getHalfExtent() {
		return halfExtent;
	}
	
	public static Vector2f getCenterPosFromTopLeft(float x, float y, float width, float height) {
		return new Vector2f(x + (width / 2), y + (height / 2));
	}
	
	public static Vector2f getTopLeftPosFromCenter(float x, float y, float width, float height) {
		return new Vector2f(x - (width / 2), y - (height / 2));
	}
	
	public class Collision {
		public Vector2f dist;
		public boolean isIntersecting;
		
		public Collision(Vector2f dist, boolean isIntersecting) {
			this.dist = dist;
			this.isIntersecting = isIntersecting;
		}
	}
}
