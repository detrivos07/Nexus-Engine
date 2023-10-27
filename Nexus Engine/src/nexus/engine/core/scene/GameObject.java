package nexus.engine.core.scene;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import nexus.engine.core.render.opengl.Mesh;

public class GameObject {
	protected Mesh[] meshes;
	protected final Vector3f pos, scale;
	protected final Quaternionf rot;
	protected int texPos;
	protected boolean insideFrustum;
	
	/**
	 * Creates a Meshless GameObject
	 */
	public GameObject() {
		pos = new Vector3f();
		scale = new Vector3f(1f);
		rot = new Quaternionf();
		texPos = 0;
		insideFrustum = true;
	}
	
	/**
	 * Creates a new GameObject from a mesh
	 * @param mesh Mesh for the GameObject
	 */
	public GameObject(Mesh mesh) {
		this();
		this.meshes = new Mesh[] {mesh};
	}
	
	/**
	 * Creates a new GameObject from a mesh
	 * @param meshes Mesh for the GameObject
	 */
	public GameObject(Mesh[] meshes) {
		this();
		this.meshes = meshes;
	}
	
	/**
	 * Renders this objects Mesh instance
	 */
	public void render() {
		for (Mesh mesh : meshes) mesh.render();
	}
	
	/**
	 * Destroys this objects Mesh instance
	 */
	public void destroy() {
		for (Mesh mesh : meshes) mesh.destroy();
	}
	
	public void setTexPos(int tpos) {
		this.texPos = tpos;
	}
	
	public void setInFrustum(boolean ifrust) {
		this.insideFrustum = ifrust;
	}
	
	/**
	 * Sets the position of the GameObject
	 * @param x X position of the object
	 * @param y Y position of the object
	 * @param z Z position of the object
	 */
	public void setPos(float x, float y, float z) {
		this.pos.set(x, y, z);
	}
	
	/**
	 * Sets the position of the GameObject
	 * @param pos Vector3f pos to be set to
	 */
	public void setPos(Vector3f pos) {
		this.pos.set(pos);
	}
	
	/**
	 * Sets the rotation of the GameObject
	 * @param rot Quaternion of the new rotation values
	 */
	public void setRot(Quaternionf rot) {
		this.rot.set(rot);
	}
	
	/**
	 * Sets the scale of the GameObject
	 * @param scale Vector3f of the new scale
	 */
	public void setScale(Vector3f scale) {
		this.scale.set(scale);
	}
	
	/**
	 * Sets the scale of the GameObject
	 * @param scale float of the new scale
	 */
	public void setScale(float scale) {
		this.scale.set(scale);
	}
	
	/**
	 * Sets the GameObjects Mesh
	 * @param m The new Mesh for the Object
	 */
	public void setMesh(Mesh m) {
		if (meshes != null) for (Mesh mesh : meshes) mesh.destroy();
		this.meshes = new Mesh[] {m};
	}
	
	/**
	 * Sets the GameObjects Mesh array
	 * @param m The new Mesh array for the Object
	 */
	public void setMeshes(Mesh[] m) {
		this.meshes = m;
	}

	//GETTERS
	public int getTexPos() {
		return texPos;
	}
	
	public boolean insideFrustum() {
		return insideFrustum;
	}
	
	public Mesh getMesh() {
		return meshes[0];
	}
	
	public Mesh getMesh(int i) {
		return meshes[i];
	}
	
	public Mesh[] getMeshes() {
		return meshes;
	}
	
	public Vector3f getPos() {
		return pos;
	}
	
	public Quaternionf getRot() {
		return rot;
	}
	
	public Vector3f getScale() {
		return scale;
	}
}
