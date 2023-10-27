package artifice.engine.render.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import nexus.engine.core.render.utils.VAO;
import nexus.engine.core.render.utils.VBO;

public class Mesh {
	protected float[] verts = new float[] {
			-1f, 1f, 0, //TL
			1f, 1f, 0, //TR
			1f, -1f, 0, //BR
			-1f, -1f, 0 //BL
	};
	
	protected float[] texs = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1,
	};
	
	protected int[] inds = new int[] {
			0, 1, 2,
			2, 3, 0
	};
	
	protected int vCount;
	protected VAO vao;
	protected VBO ivbo, mvbo;
	
	public Mesh() {
		vCount = inds.length;
		vao = new VAO();
		vao.bind();
		
		ivbo = new VBO(GL_ELEMENT_ARRAY_BUFFER);
		ivbo.bind();
		IntBuffer buffer = BufferUtils.createIntBuffer(inds.length);
		buffer.put(inds);
		buffer.flip();
		ivbo.uploadData(buffer, GL_STATIC_DRAW);
		ivbo.unbind();
		
		mvbo = new VBO(GL_ARRAY_BUFFER);
		mvbo.bind();
		mvbo.uploadData(createBuffer(verts, texs), GL_STATIC_DRAW);
		mvbo.attribPointer(0, 3, 0);
		mvbo.attribPointer(1, 2, 3);
		mvbo.unbind();
		vao.unbind();
	}
	
	public void render() {
		vao.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		ivbo.bind();
		
		glDrawElements(GL_TRIANGLES, vCount, GL_UNSIGNED_INT, 0);
		
		ivbo.unbind();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		vao.unbind();
	}
	
	public void render(int amt) {
		vao.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glEnableVertexAttribArray(5);
		glEnableVertexAttribArray(6);
		glEnableVertexAttribArray(7);
		glEnableVertexAttribArray(8);
		ivbo.bind();
		
		glDrawElementsInstanced(GL_TRIANGLES, vCount, GL_UNSIGNED_INT, 0, amt);
		
		ivbo.unbind();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glDisableVertexAttribArray(5);
		glDisableVertexAttribArray(6);
		glDisableVertexAttribArray(7);
		glDisableVertexAttribArray(8);
		vao.unbind();
	}
	
	public void destroy() {
		ivbo.destroy();
		mvbo.destroy();
	}
	
	public float[] getVerts() {
		return verts;
	}
	
	public float[] getTexUnits() {
		return texs;
	}
	
	protected FloatBuffer createBuffer(float[]... data) {
		int totalLength = 0;
		for (int i = 0; i < data.length; i++) for (int o = 0; o < data[i].length; o++) totalLength++;
		int pointer = 0;
		float[] all = new float[totalLength];
		for (int i = 0; i < data.length; i++) for (int o = 0; o < data[i].length; o++) all[pointer++] = data[i][o];
		FloatBuffer buffer = BufferUtils.createFloatBuffer(all.length);
		buffer.put(all);
		buffer.flip();
		return buffer;
	}
}
