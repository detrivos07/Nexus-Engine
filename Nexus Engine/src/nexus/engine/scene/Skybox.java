package nexus.engine.scene;

import nexus.engine.core.render.opengl.*;
import nexus.engine.utils.loaders.OBJLoader;

public class Skybox extends GameObject {

	@SuppressWarnings("deprecation")
	public Skybox(String mesh, String texture) {
		super();
		Mesh skyMesh = OBJLoader.loadMesh(mesh);
		skyMesh.setMaterial(new Material(new Texture(texture)));
		setMesh(skyMesh);
		setPos(0, 0, 0);
	}
}
