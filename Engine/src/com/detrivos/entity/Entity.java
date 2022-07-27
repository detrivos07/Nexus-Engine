package com.detrivos.entity;

import static com.detrivos.eng.handle.Handler.*;

import java.util.*;
import java.util.Random;

import org.joml.*;

import com.detrivos.eng.render.*;
import com.detrivos.eng.render.shader.EntityShader;
import com.detrivos.entity.utils.TransformUtils;
import com.detrivos.game.level.Level;
import com.detrivos.game.utils.*;

public abstract class Entity {

	protected final Random random = new Random();
	
	//level data
	protected Level level;
	
	//rendering data
	protected Model model;
	protected Animation[] anims;
	protected Animation use;
	protected Texture debug;
	
	//collision data
	protected AABB boundingBox;
	
	//positional data
	protected Vector3f pos, scale;
	protected Vector3f movement = new Vector3f();
	protected Vector2i tilePos;
	
	protected boolean removed = false;
	
	public Entity(Vector3f pos) {
		setPos(pos);
	}
	
	public Entity(Vector3f pos, Level level) {
		setPos(pos);
		init(level);
	}
	
	public abstract void tick(float delta);
	
	public void render(EntityShader shader) {
		shader.bind();
		
		Matrix4f target = camera.getProjection();
		target.mul(level.getLevelData());
		
//		shader.createVBO(model);
		shader.setSampler();
		shader.setUniform("projection", TransformUtils.getProjection(target, scale, pos));
		use.bind(0);
		if (debug != null) debug.bind(0);
		model.render();
	}
	
	protected void init(Level level) {
		this.level = level;
	}
	
	protected void setAnim(int anim) {
		this.use = anims[anim];
	}
	
	public void remove() {
		removed = true;
	}
	
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public void setPos(float x, float y) {
		this.pos.set(x, y, 0);
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
				boundingBox.correctPos(box, data);
				pos.set(boundingBox.getCenter(), 0);
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
				boundingBox.correctPos(box, data);
				pos.set(boundingBox.getCenter(), 0);
			}
		}
	}
	
	protected void checkEntityCollision() {
		List<Entity> ents = level.handler.ents;
		for (int i = 0; i < ents.size(); i++) {
			Entity e = ents.get(i);
			if (e.equals(this)) continue;
			Collision c = boundingBox.getCollision(e.boundingBox);
			c.dist.x /= 2;
			c.dist.y /=  2;
			if (c.isIntersecting) {
				boundingBox.correctPos(e.boundingBox, c);
				pos.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);
				
				e.boundingBox.correctPos(boundingBox, c);
				e.pos.set(e.boundingBox.getCenter().x, e.boundingBox.getCenter().y, 0);
			}
		}
	}
	
	public Vector3f getPos() {
		return this.pos;
	}
	
	public Vector2i getTilePos() {
		return tilePos;
	}
	
	public AABB getBoundingBox() {
		return boundingBox;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Vector3f getMovement() {
		return movement;
	}
}
