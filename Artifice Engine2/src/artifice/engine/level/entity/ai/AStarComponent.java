package artifice.engine.level.entity.ai;

import java.util.*;

import org.joml.Vector2i;

import artifice.engine.level.*;
import artifice.engine.level.entity.Entity;
import artifice.engine.level.tile.Tile;

public abstract class AStarComponent {
	
	private static Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return 1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static Entity findEntity(Entity start, Entity target) {
		for (int i = 0; i < start.getLevel().getEnts().size(); i++) if (start.getLevel().getEnts().get(i).equals(target)) {
			return start.getLevel().getEnts().get(i);
		}
		return null;
	}
	
	public static Vector2i checkGoal(Vector2i goal, Level level) {
		if (goal.x < 0) goal.x = 1;
		if (goal.y < 0) goal.y = 1;
		if (goal.x > level.getWidth()) goal.x = level.getWidth() - 1;
		if (goal.y > level.getHeight()) goal.y = level.getHeight() - 1;
		return goal;
	}
	
	public static List<Node> findPath(Level level, Vector2i start, Vector2i goal, int pathMax) {
		List<Node> open = new ArrayList<Node>();
		List<Node> closed = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDist(start, goal));
		open.add(current);
		while (!open.isEmpty()) {
			Collections.sort(open, nodeSorter);
			current = open.get(0);
			
			if (checkVector(current, goal) || current.dist >= pathMax) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				open.clear();
				closed.clear();
				return path;
			}
			
			open.remove(current);
			closed.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				
				int x = current.tile.x;
				int y = current.tile.y;
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				
				Tile t = level.getMap().getTile(x + xi, y + yi);
				
				if (t == null) continue;
				if (t.isSolid()) continue;
				
				Vector2i vec = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDist(current.tile, vec);
				double hCost = getDist(vec, goal);
				Node node = new Node(vec, current, gCost, hCost);
				
				if (vecInList(closed, vec) && node.fCost >= current.fCost) continue;
				if (!vecInList(open, vec) || node.fCost < current.fCost) open.add(node);
			}
		}
		closed.clear();
		return null;
	}
	
	public static double getDist(Vector2i a, Vector2i goal) {
		int dx = a.x - goal.x;
		int dy = a.y - goal.y;
		double dist = Math.sqrt(dx * dx + dy * dy);
		//System.out.println(a.x + " " + a.y + " goal: " + goal.x + " " + goal.y + " " + dist);
		return dist;
		//return (dist > 1.1 && dist < 1.9) ? 0.95 : dist;
	}
	
	public static double getActDist(Vector2i a, Vector2i goal) {
		int dx = a.x - goal.x;
		int dy = a.y - goal.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	static boolean checkVector(Node tile, Vector2i goal) {
		if (tile.tile.x == goal.x && tile.tile.y == goal.y) return true;
		return false;
	}
	
	static boolean vecInList(List<Node> list, Vector2i vec) {
		for (Node n : list) {
			if (checkVector(n, vec)) return true;
		}
		return false;
	}
}
