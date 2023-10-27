package nexus.engine.d2;

import java.util.ArrayList;
import java.util.List;

public class TileTypeManager {
	
	private List<TileType> types;
	
	public TileTypeManager() {
		types = new ArrayList<>();
	}
	
	public void add(TileType type) {
		types.add(type);
	}
	
	public void remove(TileType type) {
		types.remove(type);
	}
}
