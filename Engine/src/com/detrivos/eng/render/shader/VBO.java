package com.detrivos.eng.render.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.*;

public class VBO {

	private final int id;
	
	public VBO() {
		id = glGenBuffers();
	}
	
	public void bind(int target) {
		glBindBuffer(target, id);
	}
	
	public void unbind(int target) {
		glBindBuffer(target, 0);
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
	
	public void uploadData(int target, FloatBuffer data, int usage) {
		glBufferData(target, data, usage);
	}
	
	public void uploadData(int target, long size, int usage) {
		glBufferData(target, size, usage);
	}
	
	public void uploadData(int target, IntBuffer data, int usage) {
		glBufferData(target, data, usage);
	}
	
	public void uploadSubData(int target, long offset, FloatBuffer data) {
		glBufferSubData(target, offset, data);
	}
	
	public void delete() {
		glDeleteBuffers(id);
	}
	
	public int getID() {
		return id;
	}
}
