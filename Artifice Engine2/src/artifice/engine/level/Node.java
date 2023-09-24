package artifice.engine.level;

import org.joml.Vector2i;

public class Node {
	
	public int dist = 0;

	public Vector2i tile;
	public Node parent;
	public int dirToParent = 0;//1, 2 // ns, ew
	public double fCost, gCost, hCost;//h = cost to target //f = g+h // g=totalofpath
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
		if (this.parent != null) dist = this.parent.dist + 1;
		
		if (parent != null) {
			if (parent.tile.x != this.tile.x) dirToParent = 1;
			if (parent.tile.y != this.tile.y) dirToParent = 2;
		}
	}
}
