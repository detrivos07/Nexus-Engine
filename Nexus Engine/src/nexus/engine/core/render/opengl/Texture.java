package nexus.engine.core.render.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Texture {

	protected long iID;
	protected String path;
	protected String[] tags;
	
	protected int id;
	protected int[] raw;
	protected int width, height;
	protected ByteBuffer pixels;
	
	/**
	 * Generates Shadow Map texture
	 * @param w Desired width
	 * @param h Desired height
	 * @param pFormat pixel format
	 */
	public Texture(int w, int h, int pFormat) {
		this.width = w;
		this.height = h;
		
		pixels = null;
		
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, pFormat, GL_FLOAT, pixels);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}
	
	/**
	 * Generates a Texture object from a @path
	 * @param path Path to the texture file to be loaded
	 */
	@Deprecated
	public Texture(String path) {
		this.path = path;
		loadSTB();
	}
	
	/**
	 * 
	 * @param iID Internal ID
	 * @param path Path to the texture file
	 * @param name Name describing the texture
	 */
	public Texture(long iID, String path, String... tags) {
		this.path = path;
		setInternalID(iID);
	}
	
	/**
	 * Binds this object to the active texture slot at @sampler
	 * @param sampler Active texture slot to be bound to
	 */
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	
	public Texture loadSTB() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);
			
			pixels = stbi_load(path, w, h, channels, 4);
			if (pixels == null) throw new IllegalStateException("Unable to load STBImage at: " + path);
			width = w.get();
			height = h.get();
		}
		
		genGLTexture();
		
		stbi_image_free(pixels);
		return this;
	}
	
	public Texture load() {
		loadSTB2();
		genGLTexture();
		return this;
	}
	
	public void unload() {
		this.destroy();
		
		this.raw = null;
		pixels.clear();
		this.pixels = null;
	}
	
	public Texture loadSTB2() {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);
			
			pixels = stbi_load(path, w, h, channels, 4);
			if (pixels == null) throw new IllegalStateException("Unable to load STBImage at: " + path);
			width = w.get();
			height = h.get();
		}
		
		
		stbi_image_free(pixels);
		return this;
	}
	
	/**
	 * Destroys this object at id in memory
	 */
	public void destroy() {
		glDeleteTextures(id);
	}
	
	//SETTERS
	public void setTags(String... tags) {
		this.tags = tags;
	}
	
	public void setInternalID(long iID) {
		this.iID = iID;
	}
	
	//GETTERS
	public String[] getTags() {
		return tags;
	}
	
	public boolean hasTag(String tag) {
		for (int i = 0; i < tags.length; i++) {
			if (tags[i].contains(tag)) return true;
		}
		return false;
	}
	
	public String getPath() {
		return path;
	}
	
	public long getInternalID() {
		return iID;
	}
	
	public int[] getRaw() {
		return raw;
	}
	
	public int getID() {
		return id;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ByteBuffer getBuffer() {
		return pixels;
	}
	
	protected void genGLTexture() {
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glGenerateMipmap(GL_TEXTURE_2D);
	}
}
