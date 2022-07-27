package com.detrivos.eng.render.shader;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import com.detrivos.eng.render.Model;

public class EntityShader extends ShaderProgram {
	
	private int loc_sampler;
	
	private VAO vao;
	private VBO modelVBO;
	private Model model;

	public EntityShader(String path) {
		super(path);
		vao = new VAO();
		modelVBO = new VBO();
		model = new Model();
		vao.bind();
		modelVBO.bind(GL_ARRAY_BUFFER);
		modelVBO.uploadData(GL_ARRAY_BUFFER, createBuffer(model.getVerts(), model.getTexUnits()), GL_STATIC_DRAW);
		modelVBO.attribPointer(0, 3, 0);
		modelVBO.attribPointer(1, 2, 3);
		modelVBO.unbind(GL_ARRAY_BUFFER);
		vao.unbind();
	}
	
	@Override
	public void bind() {
		super.bind();
		vao.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}
	
	@Override
	public void unbind() {
		super.unbind();
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		vao.unbind();
	}

	@Override
	protected void getAllUniformLocations() {
		loc_sampler = super.getUniformLocation("sampler");
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "pos");
		super.bindAttrib(1, "textures");
	}
	
	public void setSampler() {
		super.setUniform(loc_sampler, 0);
	}
}
