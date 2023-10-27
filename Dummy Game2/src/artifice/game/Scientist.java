package artifice.game;

import java.util.List;

import org.joml.Vector2i;
import org.joml.Vector3f;

import artifice.engine.level.Level;
import artifice.engine.level.entity.Entity;
import artifice.engine.level.entity.ai.AStarComponent;
import nexus.engine.core.ai.Node;

public class Scientist extends Entity {

	List<Node> path = null;
	Vector2i target = null;
	boolean found = false;
	
	public Scientist(Level level, Vector2i pos) {
		super(level, pos);
	}
	
	@Override
	public void update() {
		handlePlayer();
		postMove();
		if (target  != null) if (tilePos.x == target.x && tilePos.y == target.y) found = true;
	}
	
	void handlePlayer() {
		List<Entity> ents = level.getEnts();
		Entity p = null;
		for (Entity e : ents) if (e instanceof Player) p = e;
		if (p != null) {
			Vector2i start = tilePos;
			Vector2i goal = p.getTilePos();
			if (found == true || target == null) {
				path = AStarComponent.findPath(level, start, goal, 20);
				found = false;
			}
			
			if (path != null) {
				if (path.size() > 0 && AStarComponent.getDist(new Vector2i((int) pos.x, (int) pos.y), new Vector2i((int) p.getPos().x, (int) p.getPos().y)) >= 5) {
					target = path.get(path.size() - 1).tile;
					float varSpd = 0.0f;
					if (target.x > pos.x / 2) {
						if (Math.abs(target.x - (pos.x / 2)) < speed.x) {
							varSpd = (Math.abs(target.x - (pos.x / 2))); 
							movement.add(new Vector3f(varSpd, 0, 0));
						} else movement.add(new Vector3f(speed.x, 0, 0));
					}
					if (target.x < pos.x / 2) {
						if (Math.abs(target.x - (pos.x / 2)) < speed.x) {
							varSpd = (Math.abs(target.x - (pos.x / 2))); 
							movement.sub(new Vector3f(varSpd, 0, 0));
						} else movement.sub(new Vector3f(speed.x, 0, 0));
					}
					if (target.y < -pos.y / 2) {
						if (Math.abs(target.y - -(pos.y / 2)) < speed.x) {
							varSpd = (Math.abs(target.y - -(pos.y / 2))); 
							movement.add(new Vector3f(0, varSpd, 0));
						} else movement.add(new Vector3f(0, speed.x, 0));
					}
					if (target.y > -pos.y / 2) {
						if (Math.abs(target.y - -(pos.y / 2)) < speed.x) {
							varSpd = (Math.abs(target.y - -(pos.y / 2)));
							movement.sub(new Vector3f(0, varSpd, 0));
						} else movement.sub(new Vector3f(0, speed.x, 0));
					}
				} else target = null;
			}
		}
	}
}
