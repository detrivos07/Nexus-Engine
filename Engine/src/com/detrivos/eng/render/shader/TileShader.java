package com.detrivos.eng.render.shader;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import com.detrivos.eng.render.Model;

public class TileShader extends ShaderProgram {
	
	private int loc_sampler;
	
	public static final int MAX = 1000000;
	public static final int f_COUNT = 24;
	
	private VAO vao;
	private VBO modelVBO;
	private VBO vbo;

	public TileShader(String path, Model model) {
		super(path);
		vao = new VAO();
		modelVBO = new VBO();
		vbo = super.createVBO(MAX * f_COUNT);
		
		vao.bind();
		modelVBO.bind(GL_ARRAY_BUFFER);
		modelVBO.uploadData(GL_ARRAY_BUFFER, createBuffer(model.getVerts()), GL_STATIC_DRAW);
		modelVBO.attribPointer(0, 3, 0);
		modelVBO.unbind(GL_ARRAY_BUFFER);
		
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.instanceAttribPointer(1, 4, f_COUNT, 0);
		vbo.instanceAttribPointer(2, 4, f_COUNT, 4);
		vbo.instanceAttribPointer(3, 4, f_COUNT, 8);
		vbo.instanceAttribPointer(4, 4, f_COUNT, 12);
		vbo.instanceAttribPointer(5, 2, f_COUNT, 16);
		vbo.instanceAttribPointer(6, 2, f_COUNT, 18);
		vbo.instanceAttribPointer(7, 2, f_COUNT, 20);
		vbo.instanceAttribPointer(8, 2, f_COUNT, 22);
		vbo.unbind(GL_ARRAY_BUFFER);
		vao.unbind();
	}
	
	@Override
	public void bind() {
		super.bind();
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
	}
	
	@Override
	public void unbind() {
		super.unbind();
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

	@Override
	protected void getAllUniformLocations() {
		loc_sampler = super.getUniformLocation("sampler");
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "pos");
		super.bindAttrib(1, "projection");
		super.bindAttrib(5, "textures0");
		super.bindAttrib(6, "textures1");
		super.bindAttrib(7, "textures2");
		super.bindAttrib(8, "textures3");
	}
	
	public void setSampler(int value) {
		super.setUniform(loc_sampler, value);
	}
	
	public void setSampler(int[] value) {
		super.setUniform(loc_sampler, value);
	}
	
	public void updateVBO(float[] data, FloatBuffer buffer) {
		super.updateVBO(vbo, data, buffer);
	}
	
	public int storeData(Matrix4f matrix, float[] data, int pointer) {
		data[pointer++] = matrix.m00();
		data[pointer++] = matrix.m01();
		data[pointer++] = matrix.m02();
		data[pointer++] = matrix.m03();
		data[pointer++] = matrix.m10();
		data[pointer++] = matrix.m11();
		data[pointer++] = matrix.m12();
		data[pointer++] = matrix.m13();
		data[pointer++] = matrix.m20();
		data[pointer++] = matrix.m21();
		data[pointer++] = matrix.m22();
		data[pointer++] = matrix.m23();
		data[pointer++] = matrix.m30();
		data[pointer++] = matrix.m31();
		data[pointer++] = matrix.m32();
		data[pointer++] = matrix.m33();
		return pointer;
	}
	
	public int storeData(float[] data, float[] target, int pointer) {
		for (int i = 0; i < data.length; i++) target[pointer++] = data[i];
		return pointer;
	}
}
