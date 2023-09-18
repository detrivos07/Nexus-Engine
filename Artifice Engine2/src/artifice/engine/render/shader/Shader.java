package artifice.engine.render.shader;

import static org.lwjgl.opengl.GL20.*;

import java.io.*;
import java.nio.*;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {
	
	private int loc_sampler;
	
	private int program;
	private int vID, fID;
	
	public Shader(String path) {
		program = glCreateProgram();
		if (program == 0) throw new IllegalStateException("Unable to create Shader Program: " + path);
		vID = loadShader(path, GL_VERTEX_SHADER);
		fID = loadShader(path, GL_FRAGMENT_SHADER);
		
		attachShaders();
		link();
	}
	
	//UNIFORM SETTERS
	public void setUniform(int location, int value) {
		if (location != -1) glUniform1i(location, value);
	}
	
	public void setUniform(int location, int[] value) {
		IntBuffer buffer = BufferUtils.createIntBuffer(value.length);
		buffer.put(value);
		buffer.flip();
		if (location != -1) glUniform1iv(location, buffer);
		buffer.clear();
	}
	
	public void setUniform(int location, float value) {
		if (location != -1) glUniform1f(location, value);
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
	
	/**
	 * Stores data from a Matrix4f to the given float array
	 * @param matrix Matrix of data
	 * @param data Array to have data put into
	 * @param pointer pointer to data
	 * @return pointer
	 */
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
	
	//Sampler setters
	public void setSampler() {
		setUniform(loc_sampler, 0);
	}
	
	public void setSampler(int... value) {
		setUniform(loc_sampler, value);
	}
	
	/**
	 * Binds the current shader program
	 */
	public void bind() {
		glUseProgram(program);
	}
	
	/**
	 * Unbinds all shader programs
	 */
	public void unbind() {
		glUseProgram(0);
	}
	
	public void destroy() {
		glDetachShader(vID, program);
		glDetachShader(fID, program);
		glDeleteShader(vID);
		glDeleteShader(fID);
		glDeleteProgram(program);
	}
	
	//LOCAL METHODS
	void attachShaders() {
		glAttachShader(program, vID);
		glAttachShader(program, fID);
	}
	
	void link() throws IllegalStateException {
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) throw new IllegalStateException("Error linking Shader code: " + glGetProgramInfoLog(program, 1024));
        
        if (vID != 0) glDetachShader(program, vID);
        if (fID != 0) glDetachShader(program, fID);
        
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(program, 1024));
    }
	
	static int loadShader(String path, int type) {
		StringBuilder string = new StringBuilder();
		String t = (type == GL_VERTEX_SHADER) ? ".vs" : ".fs";
		InputStream in = Shader.class.getResourceAsStream("/artifice/engine/render/shader/" + path + "" + t);
		
		try {//TODO::ERROR HANDLING
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
		try {//TODO::ERROR HANDLING
			if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) throw new IllegalStateException(glGetShaderInfoLog(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}
