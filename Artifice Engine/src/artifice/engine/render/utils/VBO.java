package artifice.engine.render.utils;

import static org.lwjgl.opengl.GL33.*;

import java.nio.*;

public class VBO {
	
	private int id, type;
	
	public VBO(int type) {
		id = glGenBuffers();
		this.type = type;
	}
	
	public void bind() {
		glBindBuffer(type, id);
	}
	
	public void unbind() {
		glBindBuffer(type, 0);
	}
	
	public void attribPointer(int attrib, int dataSize, int offset) {
		glVertexAttribPointer(attrib, dataSize, GL_FLOAT, false, 0, offset * 16);
	}
	public void instanceAttribPointer(int attrib, int dataSize, int length, int offset) {
		glVertexAttribPointer(attrib, dataSize, GL_FLOAT, false, length * 4, offset * 4);
		glVertexAttribDivisor(attrib, 1);
	}
	
	public void instanceAttribPointer(int attrib, int dataSize, int length, int offset, int divisor) {
		glVertexAttribPointer(attrib, dataSize, GL_FLOAT, false, length * 4, offset * 4);
		glVertexAttribDivisor(attrib, divisor);
	}
	
	public void uploadData(FloatBuffer data, int usage) {
		glBufferData(type, data, usage);
	}
	
	public void uploadData(long size, int usage) {
		glBufferData(type, size, usage);
	}
	
	public void uploadData(IntBuffer data, int usage) {
		glBufferData(type, data, usage);
	}
	
	public void uploadSubData(long offset, FloatBuffer data) {
		glBufferSubData(type, offset, data);
	}
	
	public void delete() {
		glDeleteBuffers(id);
	}
	
	public int getID() {
		return id;
	}
}
