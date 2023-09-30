package artifice.game;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.*;
import java.lang.Math;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;
import artifice.engine.level.entity.Entity;
import artifice.engine.level.entity.Projectile;
import nexus.engine.core.collision.AABB;
import nexus.engine.core.io.*;
import nexus.engine.sound.SoundManager;

public class Player extends Entity {
	
	int fireRate = 0;
	double dir = 0;
	
	private Keyboard board;
	private Mouse mouse;

	public Player(Level level, Vector2i pos) {
		super(level, pos);
		this.bb = new AABB(new Vector2f(pos.x, pos.y), new Vector2f(scale.x - 0.2f, scale.y - 0.1f));
		speed = new Vector3f(13.0f * (1.0f/60.0f), 13.0f * (1.0f/60.0f), 18.0f * (1.0f/60.0f));
		fireRate = 13;
		
		board = Keyboard.getInstance();
		mouse = Mouse.getInstance();
	}
	
	public void input(Window window, Camera camera, SoundManager sm) {
		if (board.check(GLFW_KEY_LEFT_SHIFT)) speed.x = speed.z;
		else speed.x = speed.y;
		if (board.check(GLFW_KEY_W)) movement.add(new Vector3f(0, speed.x, 0));
		if (board.check(GLFW_KEY_S)) movement.add(new Vector3f(0, -speed.x, 0));
		if (board.check(GLFW_KEY_A)) movement.add(new Vector3f(-speed.x, 0, 0));
		if (board.check(GLFW_KEY_D)) movement.add(new Vector3f(speed.x, 0, 0));
		
		postMove();
		camera.setPos(pos.mul(-level.getScale(), new Vector3f()));
		
		if (fireRate == 0 && mouse.check(Mouse.BUTTON_1)) {
			shoot(level);
			if (!sm.getSource("place").isPlaying());
			sm.playSource("place");
		}
	}

	@Override
	public void update() {
		if (fireRate > 0) fireRate--;
	}
	
	void shoot(Level level) {
		setDir(level);
		Projectile p = new Projectile(level, this, new Vector3f(pos), dir);
		level.getProjs().add(p);
		fireRate = Projectile.fireRate;
	}
	
	void setDir(Level level) {
		double dx = level.getMouseWorldPos().x - (pos.x / 2) - 0.5;
		double dy = level.getMouseWorldPos().y - -(pos.y / 2) - 0.5;
		dir = Math.atan2(dy, dx) * -1;
	}
}
