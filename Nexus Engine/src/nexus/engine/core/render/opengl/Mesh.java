package nexus.engine.core.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.function.Consumer;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import nexus.engine.core.render.utils.VAO;
import nexus.engine.core.render.utils.VBO;
import nexus.engine.scene.GameObject;

public class Mesh {
	public static final int MAX_WEIGHTS = 4;
	protected static final Vector3f DEFAULT_COLOUR = new Vector3f(0.2f, 0.5f, 0.8f);
	
	protected VAO vao;
	protected VBO vbo;
	protected List<VBO> vbos;
	protected final int vCount;
	private float boundingRadius;
	
	protected Vector3f colour;
	protected Material material;
	
	public Mesh(float[] pos, float[] texCoords, float[] normals, int[] inds) {
		this(pos, texCoords, normals, inds, Mesh.createEmptyIntArray(Mesh.MAX_WEIGHTS * pos.length / 3, 0), Mesh.createEmptyFloatArray(Mesh.MAX_WEIGHTS * pos.length / 3, 0));
	}
	
	public Mesh(float[] pos, float[] texCoords, float[] normals, int[] inds, int[] jinds, float[] weights) {
		vbos = new ArrayList<VBO>();
		vCount = inds.length;
		setBoundingRadius(1);
		
		FloatBuffer vertBuffer = null;
		FloatBuffer textureBuffer = null;
		FloatBuffer normBuffer = null;
		FloatBuffer weightBuffer = null;
		IntBuffer indBuffer = null;
		IntBuffer jindsBuffer = null;
		
		try {
			//setColour(DEFAULT_COLOUR);
			
			vertBuffer = MemoryUtil.memAllocFloat(pos.length);
			vertBuffer.put(pos).flip();
			
			textureBuffer = MemoryUtil.memAllocFloat(texCoords.length);
			textureBuffer.put(texCoords).flip();
			
			normBuffer = MemoryUtil.memAllocFloat(normals.length);
			normBuffer.put(normals).flip();
			
			weightBuffer = MemoryUtil.memAllocFloat(weights.length);
			weightBuffer.put(weights).flip();
			
			indBuffer = MemoryUtil.memAllocInt(inds.length);
			indBuffer.put(inds).flip();
			
			jindsBuffer = MemoryUtil.memAllocInt(jinds.length);
			jindsBuffer.put(jinds).flip();
			
			vao = new VAO();
			vao.bind();
			
			//INDICES
			vbo =  new VBO(GL_ELEMENT_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(indBuffer);
			vbos.add(vbo);
			
			//POSITIONS
			vbo = new VBO(GL_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(vertBuffer);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			vbos.add(vbo);
			
			//TEXTURES
			vbo = new VBO(GL_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(textureBuffer);
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			vbos.add(vbo);
			
			//NORMALS
			vbo = new VBO(GL_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(normBuffer);
			glEnableVertexAttribArray(2);
			glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
			vbos.add(vbo);
			
			//WEIGHTS
			vbo = new VBO(GL_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(weightBuffer);
			glEnableVertexAttribArray(3);
			glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
			vbos.add(vbo);
			
			//JOINT INDS
			vbo = new VBO(GL_ARRAY_BUFFER);
			vbo.bind();
			vbo.uploadData(jindsBuffer);
			glEnableVertexAttribArray(4);
			glVertexAttribPointer(4, 4, GL_FLOAT, false, 0, 0);
			vbos.add(vbo);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			vao.unbind();
		} finally {
			if (vertBuffer != null) MemoryUtil.memFree(vertBuffer);//MUST be called when manually allocating memory
			if (textureBuffer != null) MemoryUtil.memFree(textureBuffer);
			if (normBuffer != null) MemoryUtil.memFree(normBuffer);
			if (weightBuffer != null) MemoryUtil.memFree(weightBuffer);
			if (indBuffer != null) MemoryUtil.memFree(indBuffer);
			if (jindsBuffer != null) MemoryUtil.memFree(jindsBuffer);
		}
	}
	
	/**
	 * All pre-render code goes in here, per mesh
	 */
	protected void startRender() {
		Texture t = material.getTexture();
		if (t != null) t.bind(0);
		
		Texture n = material.getNormalMap();
		if (n != null) n.bind(1);
		
		vao.bind();
	}
	
	/**
	 * All render cleanup code, per mesh
	 */
	protected void endRender() {
		vao.unbind();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Primary render pass for the current mesh
	 */
	public void render() {
		startRender();
		glDrawElements(GL_TRIANGLES, vCount, GL_UNSIGNED_INT, 0);
		endRender();
	}
	
	/**
	 * Primary render pass for shared meshes.  Good for instances
	 * @param objects List of game objects with this mesh
	 * @param consumer Consumer to differentiate object
	 */
	public void renderList(List<GameObject> objects, Consumer<GameObject> consumer) {
		startRender();
		for (GameObject o : objects) {
			consumer.accept(o);
			glDrawElements(GL_TRIANGLES, vCount, GL_UNSIGNED_INT, 0);
		}
		endRender();
	}
	
	/**
	 * Disables active attribute arrays
	 * Destroys all VBOs and VAOs
	 * Destroys Material
	 */
	public void destroy() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		if (material != null) material.destroy();
		
		for (VBO vbo : vbos) vbo.destroy();
	}
	
	//SETTERS
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	public void setMaterial(Material m) {
		this.material = m;
	}
	
	public void setBoundingRadius(float radius) {
		this.boundingRadius = radius;
	}
	
	//GETTERS
	public Vector3f getColour() {
		return colour;
	}
	
	public int getVertexCount() {
		return vCount;
	}
	
	public float getBoundingRadius() {
		return boundingRadius;
	}
	
	public VAO getVAO() {
		return vao;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	static float[] createEmptyFloatArray(int length, float defaultValue) {
		float[] result = new float[length];
		Arrays.fill(result, defaultValue);
		return result;
	}
	
	static int[] createEmptyIntArray(int length, int defaultValue) {
		int[] result = new int[length];
		Arrays.fill(result, defaultValue);
		return result;
	}
}
