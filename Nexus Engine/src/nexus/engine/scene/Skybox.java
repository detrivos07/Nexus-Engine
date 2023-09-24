package nexus.engine.scene;

import nexus.engine.core.render.opengl.*;
import nexus.engine.utils.loaders.OBJLoader;

public class Skybox extends GameObject {

	public Skybox(String mesh, String texture) {
		super();
		Mesh skyMesh = OBJLoader.loadMesh(mesh);
		Texture t = new Texture(texture);//TODO :: WTF
		t.loadSTB();
		skyMesh.setMaterial(new Material(t));
		setMesh(skyMesh);
		setPos(0, 0, 0);
	}
}
