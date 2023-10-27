package nexus.engine.core.render.opengl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import nexus.engine.core.render.utils.Transformation3D;
import nexus.engine.core.render.utils.VBO;
import nexus.engine.core.scene.GameObject;

public class InstancedMesh extends Mesh {
	static final int MAT_SIZE_FLOATS = 4 * 4,
			 VEC4F_SIZE_BYTES = 4 * 4,
			 MAT_SIZE_BYTES = 4 * VEC4F_SIZE_BYTES;
	
	private final int amt;
	private final VBO mvvbo, mlvvbo;
	private FloatBuffer mvBuffer, mlvBuffer;
	
	public InstancedMesh(float[] pos, float[] texCoords, float[] normals, int[] inds, int amt) {
		super(pos, texCoords, normals, inds);
		this.amt = amt;
		
		vao.bind();
		
		mvBuffer = MemoryUtil.memAllocFloat(amt * MAT_SIZE_FLOATS);
		mlvBuffer = MemoryUtil.memAllocFloat(amt * MAT_SIZE_FLOATS);
		
		//Model mat
		mvvbo = new VBO(GL_ARRAY_BUFFER);
		mvvbo.bind();
		mvvbo.uploadData(mvBuffer);
		int s = 5;
		for (int i = 0; i < 4; i++) {
			mvvbo.instanceAttribPointer(s, 4, MAT_SIZE_BYTES, i * VEC4F_SIZE_BYTES);
			glEnableVertexAttribArray(s++);
		}
		mvvbo.unbind();
		vbos.add(mvvbo);
		
		//Light mat
		mlvvbo = new VBO(GL_ARRAY_BUFFER);
		mlvvbo.bind();
		mlvvbo.uploadData(mlvBuffer);
		for (int i = 0; i < 4; i++) {
			mlvvbo.instanceAttribPointer(s, 4, MAT_SIZE_BYTES, i * VEC4F_SIZE_BYTES);
			glEnableVertexAttribArray(s++);
		}
		mlvvbo.unbind();
		vao.unbind();
		
		MemoryUtil.memFree(mlvBuffer);
		MemoryUtil.memFree(mvBuffer);
	}
	
	public void renderListInstanced(List<GameObject> objects, boolean dmap, Transformation3D t, Matrix4f viewMat, Matrix4f lightViewMat) {
		startRender();
		
		int chunkSize = amt;
		int length = objects.size();
		for (int i = 0; i < length; i+= chunkSize) {
			int end = Math.min(length, i + chunkSize);
			List<GameObject> subList = objects.subList(i, end);
			renderChunkInstanced(subList, dmap, t, viewMat, lightViewMat);
		}
		
		endRender();
	}
	
	private void renderChunkInstanced(List<GameObject> objects, boolean dmap, Transformation3D t, Matrix4f viewMat, Matrix4f lightViewMat) {
		mvBuffer.clear();
		mlvBuffer.clear();
		
		int i = 0;
		for (GameObject o : objects) {
			Matrix4f modelMat = t.buildModelMatrix(o);
			if (!dmap) {
				Matrix4f modelViewMat = t.buildModelViewMatrix(modelMat, viewMat);
				modelViewMat.get(MAT_SIZE_FLOATS * i, mvBuffer);
			}
			Matrix4f modelLightViewMat = t.buildModelLightViewMatrix(modelMat, lightViewMat);
			modelLightViewMat.get(MAT_SIZE_FLOATS * i, mlvBuffer);
			i++;
		}
		
		mvvbo.bind();
		mvvbo.uploadData(mvBuffer, GL_DYNAMIC_DRAW);
		
		mlvvbo.bind();
		mlvvbo.uploadData(mlvBuffer, GL_DYNAMIC_DRAW);
		
		glDrawElementsInstanced(GL_TRIANGLES, this.getVertexCount(), GL_UNSIGNED_INT, 0, objects.size());
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * All pre-render code goes in here, per mesh
	 */
	@Override
	protected void startRender() {
		super.startRender();
		int s = 5;
		int amt = 4 * 2;
		for (int i = 0; i < amt; i++) glEnableVertexAttribArray(s + i);
	}
	
	/**
	 * All render cleanup code, per mesh
	 */
	@Override
	protected void endRender() {
		super.endRender();
		int s = 5;
		int amt = 4 * 2;
		for (int i = 0; i < amt; i++) glDisableVertexAttribArray(s + i);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		if (mvBuffer != null) MemoryUtil.memFree(mvBuffer);
		if (mlvBuffer != null) MemoryUtil.memFree(mlvBuffer);
	}
}
