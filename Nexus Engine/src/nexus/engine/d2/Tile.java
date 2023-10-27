package nexus.engine.d2;

public class Tile {

	private int x, y;
	private TileMap map;
	
	private TileType type;
	private boolean solid = false;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//SETTERS
	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	
	public Tile setType(TileType type) {
		this.type = type;
		return this;
	}
	
	//GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isSolid() {
		return solid;
	}
}
