package nexus.engine.core.render.utils;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VBO {
	private final int id, type;
	
	/**
	 * Creates a new vertex buffer of the specified type
	 * @param type Type of vertex buffer to be created, 
	 * Either: GL_ARRAY_BUFFER or GL_ELEMENT_ARRAY_BUFFER
	 */
	public VBO(int type) {
		id = glGenBuffers();
		this.type = type;
	}
	
	public void uploadData(FloatBuffer data) {
		glBufferData(type, data, GL_STATIC_DRAW);
	}
	
	public void uploadData(FloatBuffer data, int usage) {
		glBufferData(type, data, usage);
	}
	
	public void uploadData(IntBuffer data) {
		glBufferData(type, data, GL_STATIC_DRAW);
	}
	
	public void uploadData(IntBuffer data, int usage) {
		glBufferData(type, data, usage);
	}
	
	public void uploadData(long size, int usage) {
		glBufferData(type, size, usage);
	}
	
	public void uploadSubData(long offset, FloatBuffer data) {
		glBufferSubData(type, offset, data);
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
	
	/**
	 * Binds the buffer object to the type
	 */
	public void bind() {
		glBindBuffer(type, this.getID());
	}
	
	/**
	 * unbinds all buffers of this type
	 */
	public void unbind() {
		glBindBuffer(type, 0);
	}
	
	/**
	 * Unbinds and deletes this buffer
	 */
	public void destroy() {
		unbind();
		glDeleteBuffers(id);
	}
	
	//GETTERS
	public int getID() {
		return this.id;
	}
}
