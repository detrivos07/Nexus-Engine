package com.detrivos.entity;

import static com.detrivos.eng.handle.Handler.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;

import java.lang.Math;

import com.detrivos.eng.render.*;
import com.detrivos.game.level.Level;
import com.detrivos.game.utils.AABB;

public class Player extends Entity {
	
	//movement
	private double walkSpeed = 8;
	private double runSpeed = 13;
	private double currentSpeed = walkSpeed;
	private int dir = 0;
	private boolean running = false;

	//stats
	private double health = 50;
	
	//range attack data
	private double fireDir = 0;
	
	public Player(Vector3f pos, Level level) {
		super(pos);
		init(level);
		this.level.handler.add(this);
		
		//render data
		this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
		this.model = new Model();
		anims = MasterRenderer.activeTexPack.getPlayerAnims();
		setAnim(0);
		
		//positional data
		this.tilePos = new Vector2i((int) pos.x / 2, (int) -pos.y / 2);
		
		//collision data
		boundingBox = new AABB(new Vector2f(pos.x, pos.y), new Vector2f(scale.x - 0.1f, scale.y));
	}

	@Override
	public void tick(float delta) {
		setDirection();
		move(delta);
		checkEntityCollision();
		checkTileCollision();
		camera.setPos(pos.mul(-absScale, new Vector3f()));
	}
	
	private void move(float delta) {
		if (input.isKeyDown(GLFW_KEY_LEFT_SHIFT)) running = true;
		else running = false;
		
		if (running) currentSpeed = runSpeed;
		else currentSpeed = walkSpeed;
		
		if (input.isKeyDown(GLFW_KEY_W)) {
			movement.add(new Vector3f(0, (float) (currentSpeed * delta), 0));
			dir = 0;
		}
		if (input.isKeyDown(GLFW_KEY_S)) {
			movement.add(new Vector3f(0, (float) (-currentSpeed * delta), 0));
			dir = 2;
		}
		if (input.isKeyDown(GLFW_KEY_A)) {
			movement.add(new Vector3f((float) (-currentSpeed * delta), 0, 0));
			dir = 3;
		}
		if (input.isKeyDown(GLFW_KEY_D)) {
			movement.add(new Vector3f((float) (currentSpeed * delta), 0, 0));
			dir = 1;
		}
		
		if (movement.x != 0 || movement.y != 0) setAnim(dir + 4);
		else setAnim(dir);
		
		pos.add(movement);
		tilePos.set((int) (pos.x + 1) / 2, (int) -(pos.y - 1) / 2);
		movement.zero();
		boundingBox.getCenter().set(pos.x, pos.y);
//		camera.getPos().lerp(position.mul(-absScale, new Vector3f()), 0.15f);
	}

	private void setDirection() {
		setDir();
		double temp = Math.toDegrees(fireDir);
		if (temp >= 45 && temp < 135) dir = 0;
		else if (temp <= 45 && temp > -45) dir = 1;
		else if (temp <= -45 && temp > -135) dir = 2;
		else dir = 3;
	}
	
	private void setDir() {
		double dx = mouseWorldPos.x - pos.x;
		double dy = (-mouseWorldPos.y) - (-pos.y);
		fireDir = Math.atan2(dy, dx) * -1;
	}
}
