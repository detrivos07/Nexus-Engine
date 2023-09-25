package artifice.engine.render.model;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import nexus.engine.core.render.utils.VAO;
import nexus.engine.core.render.utils.VBO;

public class InstMesh extends Mesh {
	public static final int MAX = 10000;// MAX instances to be rendered
	public static final int f_COUNT = 24;
	
	private VBO vbo;
	private FloatBuffer buffer;
	
	public InstMesh() {
		vCount = inds.length;
		buffer = BufferUtils.createFloatBuffer(MAX * f_COUNT);
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
		mvbo.uploadData(createBuffer(verts), GL_STATIC_DRAW);
		mvbo.attribPointer(0, 3, 0);
		mvbo.unbind();
		
		vbo = new VBO(GL_ARRAY_BUFFER);
		vbo.bind();
		vbo.uploadData((MAX * f_COUNT) * 4, GL_STREAM_DRAW);
		vbo.instanceAttribPointer(1, 4, f_COUNT, 0);
		vbo.instanceAttribPointer(2, 4, f_COUNT, 4);
		vbo.instanceAttribPointer(3, 4, f_COUNT, 8);
		vbo.instanceAttribPointer(4, 4, f_COUNT, 12);
		vbo.instanceAttribPointer(5, 2, f_COUNT, 16);
		vbo.instanceAttribPointer(6, 2, f_COUNT, 18);
		vbo.instanceAttribPointer(7, 2, f_COUNT, 20);
		vbo.instanceAttribPointer(8, 2, f_COUNT, 22);
		vbo.unbind();
		vao.unbind();
	}
	
	public void render(int amt, float[] data) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		vbo.bind();
		vbo.uploadData(buffer.capacity() * 4, GL_STREAM_DRAW);
		vbo.uploadSubData(0, buffer);
		vbo.unbind();
		super.render(amt);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		vbo.destroy();
	}
	
	public VBO getVBO() {
		return vbo;
	}
}
