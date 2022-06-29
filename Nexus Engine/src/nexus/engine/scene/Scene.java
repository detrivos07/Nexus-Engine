package nexus.engine.scene;

import java.util.*;

import nexus.engine.core.render.opengl.InstancedMesh;
import nexus.engine.core.render.opengl.Mesh;

public abstract class Scene {

	private final Map<Mesh, List<GameObject>> meshMap;
	private final Map<InstancedMesh, List<GameObject>> imeshMap;
	
	public Scene() {
		meshMap = new HashMap<Mesh, List<GameObject>>();
		imeshMap = new HashMap<InstancedMesh, List<GameObject>>();
	}
	
	public void init() {
		
	}
	
	public void update() {
		
	}
	
	public void destroy() {
		
	}
	
	//SETTERS***************************
	
	
	//GETTERS***************************
	
	
	//LOCAL*****************************
}
