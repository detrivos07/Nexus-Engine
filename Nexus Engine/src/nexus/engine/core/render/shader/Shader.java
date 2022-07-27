package nexus.engine.core.render.shader;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.*;
import org.lwjgl.system.MemoryStack;

import com.google.common.flogger.StackSize;

import nexus.engine.core.render.opengl.Material;
import nexus.engine.core.render.shader.d3.lighting.*;
import nexus.engine.core.render.shader.d3.weather.Fog;
import nexus.engine.core.utils.FileUtils;

public class Shader {

	private final Map<String, Integer> uniforms;
	
	private int program;
	private int vID, fID;
	
	public Shader(String path) {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		if (program == 0) throw new IllegalStateException("Unable to create Shader Program: " + path);
		vID = loadShader(path, GL_VERTEX_SHADER);
		fID = loadShader(path, GL_FRAGMENT_SHADER);
		
		attachShaders();
		link();
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
	
	/************************ UNIFORM SETTERS ********************************************************************/
	/**
	 * Sets the matrix data for the uniform
	 * @param name Uniform to set data to
	 * @param data Data to be set to the uniform
	 */
	public void setUniform(String name, Matrix4f data) {
		//Dump matrix to FloatBuffer
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			data.get(fb);
			glUniformMatrix4fv(uniforms.get(name), false, fb);
		}
	}
	
	public void setUniform(String name, Vector3f data) {
		glUniform3f(uniforms.get(name), data.x, data.y, data.z);
	}
	public void setUniform(String name, Vector4f data) {
		glUniform4f(uniforms.get(name), data.x, data.y, data.z, data.w);
	}
	
	public void setUniform(String name, int value) {
		glUniform1i(uniforms.get(name), value);
	}
	
	public void setUniform(String name, int[] value) {
		glUniform1iv(uniforms.get(name), value);
	}
	
	public void setUniform(String name, float value) {
		glUniform1f(uniforms.get(name), value);
	}
	
	public void setUniform(String name, float[] value) {
		glUniform1fv(uniforms.get(name), value);
	}
	
	public void setUniform(String name, DirectionalLight light) {
		setUniform(name + ".col", light.getColour());
		setUniform(name + ".dir", light.getDir());
		setUniform(name + ".intensity", light.getIntensity());
	}
	
	public void setUniform(String name, PointLight light) {
		setUniform(name + ".col", light.getColour());
		setUniform(name + ".pos", light.getPos());
		setUniform(name + ".intensity", light.getIntensity());
		PointLight.Attenuation att = light.getAttenuation();
		setUniform(name + ".att.constant", att.getConstant());
		setUniform(name + ".att.linear", att.getLinear());
		setUniform(name + ".att.exponent", att.getExponent());
	}
	
	public void setUniform(String name, PointLight[] lights) {
		int num = lights != null ? lights.length : 0;
		for (int i = 0; i < num; i++) setUniform(name, lights[i], i);
	}
	
	public void setUniform(String name, PointLight light, int pos) {
		setUniform(name + "[" + pos + "]", light);
	}
	
	public void setUniform(String name, SpotLight light) {
		setUniform(name + ".point", light.getPoint());
		setUniform(name + ".dir", light.getDir());
		setUniform(name + ".range", light.getRange());
	}
	
	public void setUniform(String name, SpotLight[] lights) {
		int num = lights != null ? lights.length : 0;
		for (int i = 0; i < num; i++) setUniform(name, lights[i], i);
	}
	
	public void setUniform(String name, SpotLight light, int pos) {
		setUniform(name + "[" + pos + "]", light);
	}
	
	public void setUniform(String name, Material material) {
		setUniform(name + ".ambient", material.getAmbientCol());
		setUniform(name + ".diffuse", material.getDiffuseCol());
		setUniform(name + ".specular", material.getSpecCol());
		setUniform(name + ".hasTex", material.isTextured() ? 1 : 0);
		setUniform(name + ".hasNormalMap", material.hasNormalMap() ? 1 : 0);
		setUniform(name + ".reflectance", material.getReflectance());
	}
	
	/**
	 * Sets the uniform variables for the given Fog instance
	 * @param name Name of the fog variable
	 * @param fog Fog instance to be loaded to shader
	 */
	public void setUniform(String name, Fog fog) {
		setUniform(name + ".activef", fog.isActive() ? 1 : 0);
		setUniform(name + ".col", fog.getCol());
		setUniform(name + ".density", fog.getDensity());
	}
	
	public void setUniform(String uniformName, Matrix4f[] matrices) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			int length = matrices != null ? matrices.length : 0;
			FloatBuffer fb = stack.mallocFloat(16 * length);
			for (int i = 0; i < length; i++) matrices[i].get(16 * i, fb);
			glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
		}
	}
	
	/******************************************* CREATION **********************************************************/
	
	/**
	 * Creates the uniform location for the variable name
	 * @param name Variable uniform in the shader
	 */
	public void createUniform(String name) {
		int loc = glGetUniformLocation(program, name);
		if (loc < 0) throw new IllegalStateException("Couldn't find uniform: " + name);
		uniforms.put(name, loc);
	}
	
	public void createDirectionalLightUniform(String name) {
		createUniform(name + ".col");
		createUniform(name + ".dir");
		createUniform(name + ".intensity");
	}
	
	public void createPointLightUniform(String name) {
		createUniform(name + ".col");
		createUniform(name + ".pos");
		createUniform(name + ".intensity");
		createUniform(name + ".att.constant");
		createUniform(name + ".att.linear");
		createUniform(name + ".att.exponent");
	}
	
	public void createPointLightUniform(String name, int size) {
		for (int i = 0; i < size; i++) createPointLightUniform(name + "[" + i + "]");
	}
	
	/**
	 * Creates a new SpotLight uniform
	 * @param name Name of the variable in the shader
	 */
	public void createSpotLightUniform(String name) {
		createPointLightUniform(name + ".point");
		createUniform(name + ".dir");
		createUniform(name + ".range");
	}
	
	/**
	 * Creates a new SpotLight uniform
	 * @param name Name of the variable in the shader
	 * @param size 
	 */
	public void createSpotLightUniform(String name, int size) {
		for (int i = 0; i < size; i++) createSpotLightUniform(name + "[" + i + "]");
	}
	
	/**
	 * Creates the material uniform
	 * @param name Name of the uniform
	 */
	public void createMaterialUniform(String name) {
		createUniform(name + ".ambient");
		createUniform(name + ".diffuse");
		createUniform(name + ".specular");
		createUniform(name + ".hasTex");
		createUniform(name + ".hasNormalMap");
		createUniform(name + ".reflectance");
	}
	
	/**
	 * Creates the fog uniforms in the shader
	 * @param name of the fog uniform
	 */
	public void createFogUniform(String name) {
		createUniform(name + ".activef");
		createUniform(name + ".col");
		createUniform(name + ".density");
	}
	/*********************************************** END OF CREATION ******************************************/
	
	/************************************************* LOCAL METHODS ************************************************/
	/**Attaches the Vertex and Fragment shaders to the Program*/
	void attachShaders() {
		glAttachShader(program, vID);
		glAttachShader(program, fID);
	}
	
	/**Links and validates the newly build Shader program*/
	void link() {//TODO:: FIX logging
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) throw new IllegalStateException();//logger.atWarning().log("Error linking Shader code: %s", glGetProgramInfoLog(program, 1024));
        
        if (vID != 0) glDetachShader(program, vID);
        if (fID != 0) glDetachShader(program, fID);
        
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) throw new IllegalStateException();//logger.atWarning().log("Warning validating Shader code: %s", glGetProgramInfoLog(program, 1024));
    }
	
	/**TODO::FIX logging
	 * Loads the shader code into opengl
	 * @param path Path to shader text file
	 * @param type Either GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
	 * @return the newly loaded shader
	 */
	static int loadShader(String path, int type) {
		StringBuilder string = new StringBuilder();
		String t = (type == GL_VERTEX_SHADER) ? ".vs" : ".fs";
		
		try {//TODO::ERROR HANDLING
			BufferedReader br = new BufferedReader(FileUtils.newReader("/nexus/engine/core/render/shader/" + path + "" + t));
			String s;//TODO:: HARDCODING
			while ((s = br.readLine()) != null) string.append(s).append("\n");
			br.close();
		} catch (IOException e) {
//			logger.atWarning().withCause(e).log("Problem encountered when loading shader");
		}
		
		int id = glCreateShader(type);
		glShaderSource(id, string);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
//			logger.atWarning().log(glGetShaderInfoLog(id));
//			logger.atWarning().withStackTrace(StackSize.MEDIUM).log("Problem occurred during the Compile of the shader at %s%s", path, t);
		}
		return id;
	}
}
