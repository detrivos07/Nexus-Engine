package artifice.engine.level.entity;

import java.util.List;

import org.joml.*;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;
import artifice.engine.render.model.Mesh;
import nexus.engine.core.collision.AABB;
import nexus.engine.core.collision.AABB.Collision;
import nexus.engine.core.render.opengl.Texture;
import nexus.engine.core.render.shader.Shader;
import nexus.engine.core.render.utils.Transformation3D;

public abstract class Entity {
	static int nID = 0;
	
	protected boolean removed = false;
	
	//Level data
	protected Level level;
	
	//Rendering data
	protected Mesh mesh;
	protected Texture debugTexture;
	
	//Positional data
	protected Vector3f pos, scale;
	protected Vector3f movement = new Vector3f();
	protected Vector3f speed = new Vector3f(8.0f * (1.0f/60.0f));
	protected Vector2i tilePos;
	protected int id;
	
	//Collision Data
	protected AABB bb;
	
	public Entity(Level level, Vector2i pos) {
		id = nID;
		if (nID != Integer.MAX_VALUE) nID++;
		else nID = Integer.MIN_VALUE;
		this.level = level;
		this.tilePos = pos;
		this.pos = new Vector3f(pos.x * 2, -(pos.y * 2), 0);
		this.scale = new Vector3f(1);
		this.mesh = new Mesh();
		this.debugTexture = new Texture("res/0.png").load();
		this.bb = new AABB(new Vector2f(pos.x, pos.y), new Vector2f(scale.x - 0.1f, scale.y));
	}
	
	public Entity(Level level, Vector3f pos) {
		id = nID;
		if (nID != Integer.MAX_VALUE) nID++;
		else nID = Integer.MIN_VALUE;
		this.level = level;
		this.pos = pos;
		this.tilePos = new Vector2i(pos.x / 2, -(pos.y / 2), 0);
		this.scale = new Vector3f(1);
		this.mesh = new Mesh();
		this.debugTexture = new Texture("res/0.png").load();
		this.bb = new AABB(new Vector2f(pos.x, pos.y), new Vector2f(scale.x - 0.1f, scale.y));
	}
	
	public abstract void update();
	
	public void postMove() {
		pos.add(movement);
		tilePos.set((int) (pos.x + 0.5f) / 2, (int) -(pos.y - 0.5f) / 2);
		movement.zero();
		bb.getCenter().set(pos.x, pos.y);
		checkEntityCollision();
		checkTileCollision();
	}
	
	public void render(Shader shader, Camera camera) {
		shader.bind();
		
		Matrix4f target = camera.getProjection();
		target.mul(level.getLevelData());
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projMat", Transformation3D.getProjection(target, scale, pos));
		debugTexture.bind(0);
		mesh.render();
		shader.unbind();
	}
	
	public void destroy() {
		debugTexture.destroy();
		mesh.destroy();
	}
	
	public void remove() {
		removed = true;
	}
	
	protected void checkTileCollision() {
		AABB[] boxes = new AABB[25];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				boxes[x + y * 5] = level.getMap().getBB(
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
			Collision data = bb.getCollision(box);
			if (data.isIntersecting) {
				bb.correctPos(box, data);
				pos.set(bb.getCenter(), 0);
			}
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) { 
					if (box == null) box = boxes[i];
					
					Vector2f l1 = box.getCenter().sub(pos.x, pos.y, new Vector2f());
					Vector2f l2 = boxes[i].getCenter().sub(pos.x, pos.y, new Vector2f());
					
					if (l1.lengthSquared() > l2.lengthSquared()) box = boxes[i];
				}
			}
			
			data = bb.getCollision(box);
			if (data.isIntersecting) {
				bb.correctPos(box, data);
				pos.set(bb.getCenter(), 0);
			}
		}
	}
	
	protected void checkEntityCollision() {
		List<Entity> ents = level.getEnts();
		for (int i = 0; i < ents.size(); i++) {
			Entity e = ents.get(i);
			if (e.equals(this)) continue;
			Collision c = bb.getCollision(e.bb);
			c.dist.x /= 2;
			c.dist.y /=  2;
			if (c.isIntersecting) {
				bb.correctPos(e.bb, c);
				pos.set(bb.getCenter().x, bb.getCenter().y, 0);
				
				e.bb.correctPos(bb, c);
				e.pos.set(e.bb.getCenter().x, e.bb.getCenter().y, 0);
			}
		}
	}
	
	//SETTERS
	public void setPos(Vector3f pos) {
		this.pos = pos;
		tilePos.set((int) (pos.x + 1) / 2, (int) -(pos.y - 1) / 2);
	}
	
	public void setPos(float x, float y) {
		this.pos.set(x, y, 0);
	}
	
	//GETTERS
	public boolean isRemoved() {
		return removed;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Vector3f getMovement() {
		return movement;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public Vector3f getPos() {
		return this.pos;
	}
	
	public Vector2i getTilePos() {
		return tilePos;
	}
	
	public int getID() {
		return id;
	}
}
