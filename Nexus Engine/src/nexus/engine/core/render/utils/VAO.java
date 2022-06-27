package nexus.engine.core.render.utils;

import static org.lwjgl.opengl.GL30.*;

public class VAO {
	private final int id;
	
	/**
	 * Generates a new vertex array
	 */
	public VAO() {
		id = glGenVertexArrays();
	}
	
	/**
	 * Binds this vertex array
	 */
	public void bind() {
		glBindVertexArray(id);
	}
	
	/**
	 * unbinds all vertex arrays
	 */
	public void unbind() {
		glBindVertexArray(0);
	}
	
	/**
	 * Unbinds and deletes all vertex arrays at id
	 */
	public void destroy() {
		unbind();
		glDeleteVertexArrays(id);
	}
	
	//GETTERS
	public int getID() {
		return this.id;
	}
}
