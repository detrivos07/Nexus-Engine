package artifice.engine;

import java.io.File;
import java.util.*;

import artifice.engine.render.texture.*;

public class AssetLoader {
	
	public static TextureAtlas loadAtlas(String path) {
		List<Texture> textures = new ArrayList<Texture>();
		String tempPath = path + "/";
		File root = new File(tempPath);
		for (int j = 0; j < root.list().length; j++) {
			textures.add(new Texture(tempPath + root.list()[j]));
		}
		return new TextureAtlas(textures);
	}
}
