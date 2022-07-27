package com.detrivos.eng.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.*;

import org.lwjgl.BufferUtils;

import com.detrivos.eng.handle.Handler;

public class Model {

	private int drawCount;
	private int iID;
	
	private float[] verticies = new float[] {
			-1f, 1f, 0, //TL
			1f, 1f, 0, //TR
			1f, -1f, 0, //BR
			-1f, -1f, 0 //BL
	};
	
	private float[] texture = new float[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
	};
	
	private int[] indices = new int[] {
			0, 1, 2,
			2, 3, 0
	};
	
	public Model() {
		drawCount = indices.length;
		
		iID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public Model(float[] verticies, float[] texture, int[] indices) {
		drawCount = indices.length;
		
		iID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	protected void finalize() throws Throwable {
		if (!Handler.running) glDeleteBuffers(iID);
		super.finalize();
	}
	
	public void setTextureUnits(float[] texUnits) {
		this.texture = texUnits;
	}
	
	public void render(int amt) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
		glDrawElementsInstanced(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0, amt);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public float[] getVerts() {
		return verticies;
	}
	
	public float[] getTexUnits() {
		return texture;
	}
}
