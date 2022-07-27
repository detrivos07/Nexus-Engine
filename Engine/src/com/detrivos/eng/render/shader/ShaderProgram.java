package com.detrivos.eng.render.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.*;
import java.nio.*;

import org.joml.*;
import org.lwjgl.BufferUtils;

public abstract class ShaderProgram {

	private int program;
	private int vID, fID;
	
	public ShaderProgram(String path) {
		program = glCreateProgram();
		vID = loadShader(path, GL_VERTEX_SHADER);
		fID = loadShader(path, GL_FRAGMENT_SHADER);
		
		attachShaders();
		bindAttribs();
		glLinkProgram(program);
		glValidateProgram(program);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return glGetUniformLocation(program, uniformName);
	}
	
	protected abstract void bindAttribs();
	
	protected void bindAttrib(int attrib, String varName) {
		glBindAttribLocation(program, attrib, varName);
	}
	
	protected void attachShaders() {
		glAttachShader(program, vID);
		glAttachShader(program, fID);
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
	
	public void setUniform(int location, int[] value) {
		IntBuffer buffer = BufferUtils.createIntBuffer(value.length);
		buffer.put(value);
		buffer.flip();
		if (location != -1) glUniform1iv(location, buffer);
		buffer.clear();
	}
	
	public void setUniform(int location, int value) {
		if (location != -1) glUniform1i(location, value);
	}
	
	public void setUniform(String name, float[] value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(value.length);
		buffer.put(value);
		buffer.flip();
		if (location != -1) {
			if (buffer.capacity() < 9) glUniform2fv(location, buffer);
			else glUniform3fv(location, buffer);
		}
		buffer.clear();
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if (location != -1) glUniformMatrix4fv(location, false, buffer);
		buffer.clear();
	}
	
	public VBO createVBO(int floatCount) {
		VBO vbo = new VBO();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.uploadData(GL_ARRAY_BUFFER, (floatCount) * 4, GL_STREAM_DRAW);
		vbo.unbind(GL_ARRAY_BUFFER);
		return vbo;
	}
	
	public void addAttrib(int vao, int vbo, int attrib, int dataSize) {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindVertexArray(vao);
		glVertexAttribPointer(attrib, dataSize, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void addInstanceAttrib(VAO vao, VBO vbo, int attrib, int dataSize, int length, int offset) {
		vao.bind();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.instanceAttribPointer(attrib, dataSize, length, offset);
		vbo.unbind(GL_ARRAY_BUFFER);
		vao.unbind();
	}
	
	public void updateVBO(VBO vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.uploadData(GL_ARRAY_BUFFER, buffer.capacity() * 4, GL_STREAM_DRAW);
		vbo.uploadSubData(GL_ARRAY_BUFFER, 0, buffer);
		vbo.unbind(GL_ARRAY_BUFFER);
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	protected static int loadShader(String path, int type) {
		StringBuilder string = new StringBuilder();
		String t = (type == GL_VERTEX_SHADER) ? ".vs" : ".fs";
		InputStream in = ShaderProgram.class.getResourceAsStream("/com/detrivos/eng/render/shader/" + path + "" + t);
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s;
			while ((s = br.readLine()) != null) string.append(s).append("\n");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int id = glCreateShader(type);
		glShaderSource(id, string);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) throw new IllegalStateException(glGetShaderInfoLog(id));
		return id;
	}
}
