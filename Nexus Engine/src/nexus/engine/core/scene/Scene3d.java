package nexus.engine.core.scene;

import java.util.*;

import org.joml.Vector3f;

import nexus.engine.core.render.opengl.InstancedMesh;
import nexus.engine.core.render.opengl.Mesh;
import nexus.engine.core.render.shader.d3.lighting.SceneLight;
import nexus.engine.core.render.shader.d3.weather.Fog;

public class Scene3d {
	
	private float blockScale = 0.5f,
			  skyBoxScale = 100.0f,
			  extension = 2.0f;

	private Skybox skybox;
	
	private SceneLight sl;
	private final Map<Mesh, List<GameObject>> meshMap;
	private final Map<InstancedMesh, List<GameObject>> imeshMap;
	private Fog fog;
	
	private boolean renderShadows;
	
	public Scene3d() {
		meshMap = new HashMap<Mesh, List<GameObject>>();
		imeshMap = new HashMap<InstancedMesh, List<GameObject>>();
		sl = new SceneLight();
		fog = Fog.NOFOG;
		renderShadows = true;
	}
	
	public void init() {
		initSkybox();
	}
	
	public void update() {
		sl.update();
	}
	
	public void destroy() {
		skybox.destroy();
		
		for (Mesh m : meshMap.keySet()) m.destroy();
		for (InstancedMesh m : imeshMap.keySet()) m.destroy();
	}
	
	public void addGameObjects(List<GameObject> objects) {
		int amt = objects != null ? objects.size() : 0;
		for (int i = 0; i < amt; i++) {
			GameObject o = objects.get(i);
			Mesh[] meshes = o.getMeshes();
			for (Mesh mesh : meshes) {
				boolean im = mesh instanceof InstancedMesh;
				List<GameObject> list = im ? imeshMap.get(mesh) : meshMap.get(mesh);
				if (list == null) {
					list = new ArrayList<GameObject>();
					if (im) imeshMap.put((InstancedMesh) mesh, list);
					else meshMap.put(mesh, list);
				}
				list.add(o);
			}
		}
	}
	
	public void addGameObjects(GameObject... objects) {
		int amt = objects != null ? objects.length : 0;
		for (int i = 0; i < amt; i++) {
			GameObject o = objects[i];
			Mesh mesh = o.getMesh();
			boolean im = mesh instanceof InstancedMesh;
			List<GameObject> list = im ? imeshMap.get(mesh) : meshMap.get(mesh);
			if (list == null) {
				list = new ArrayList<GameObject>();
				if (im) imeshMap.put((InstancedMesh) mesh, list);
				else meshMap.put(mesh, list);
			}
			list.add(o);
		}
	}
	
	public void setFog(Fog fog) {
		this.fog = fog;
	}
	
	public void setRenderShadows(boolean val) {
		this.renderShadows = val;
	}
	
	//GETTERS
	public SceneLight getSceneLight() {
		return sl;
	}
	
	public Skybox getSkybox() {
		return skybox;
	}
	
	public Fog getFog() {
		return fog;
	}
	
	public boolean isRenderShadows() {
		return renderShadows;
	}
	
	public Map<Mesh, List<GameObject>> getMeshMap() {
		return meshMap;
	}
	
	public Map<InstancedMesh, List<GameObject>> getIMeshMap() {
		return imeshMap;
	}
	
	//LOCAL
	void initSkybox() {
        skybox = new Skybox("/models/skybox.obj", "res/skyboxes/basicDay/daybox.png");//TODO:: loading outside of a dedicated class
        skybox.setScale(new Vector3f(skyBoxScale));
	}
}
