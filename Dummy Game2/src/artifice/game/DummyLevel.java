package artifice.game;

import org.joml.Vector2i;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;
import artifice.engine.level.tile.TileMap;
import nexus.engine.core.io.Mouse;
import nexus.engine.core.io.Window;
import nexus.engine.core.render.opengl.TextureAtlas;
import nexus.engine.core.sound.SoundManager;

public class DummyLevel extends Level {

	public DummyLevel(TextureAtlas atlas, int width, int height, int scale) {
		super(atlas, width, height, scale);
		
		ents.add(new Player(this, new Vector2i(width / 2,  height / 2)));
		ents.add(new Scientist(this, new Vector2i(width / 2 + 5, height / 2 + 5)));
	}
	
	public DummyLevel(TextureAtlas atlas, String path, int scale) {
		super(atlas, path, scale);
		
		ents.add(new Player(this, new Vector2i(20, 20)));
		ents.add(new Scientist(this, new Vector2i(20, 17)));
		ents.add(new Scientist(this, new Vector2i(20, 23)));
	}

	@Override
	public void generate() {
		tmap = new TileMap(types);
		tmap.load(path);
		
		above = new TileMap(types);
		above.load(path+"Above");
		
		this.width = tmap.getWidth();
		this.height = tmap.getHeight();
	}
	
	public void input(Window window, Camera camera, Mouse mouse, SoundManager sm) {
		setMouseWorldPos(mouse.getScreenPos().x, mouse.getScreenPos().y, camera, window);
		for (int i = 0; i < ents.size(); i++) if (ents.get(i) instanceof Player) ((Player) ents.get(i)).input(window, camera, sm);
	}

	@Override
	public void update() {
		for (int i = 0; i < ents.size(); i++) ents.get(i).update();
		for (int i = 0; i < projs.size(); i++) projs.get(i).update();
		remove();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
