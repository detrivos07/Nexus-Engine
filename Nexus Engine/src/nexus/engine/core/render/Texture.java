package nexus.engine.core.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

public class Texture {
	protected long iID;
	protected int id;
	
	protected int width, height;
	protected int[] raw;
	protected ByteBuffer pixels;
	protected String path;
	protected boolean isLoaded;
	
	public Texture(long iID, String path, String... tags) {//TODO::Tags for textures??
		this.iID = iID;
		this.path = path;
		
		setLoaded(false);
	}
	
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
		
		genGLTexture(pFormat);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}
	
	/**
	 * Binds the texture to a slot in graphical memory??
	 * @param sampler
	 */
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	/**
	 * Clears the texture from graphical memory??
	 * @param sampler
	 */
	public void unbind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}
	
	public Texture load() {
		File texFile = new File(path);
		BufferedImage image;
		try {
			if (!texFile.exists()) image = ImageIO.read(Texture.class.getResource(path));
			else image = ImageIO.read(texFile);
			width = image.getWidth();
			height = image.getHeight();
			
			int[] rawPixels = raw = new int[width * height * 4];
			rawPixels = raw = image.getRGB(0, 0, width, height, null, 0, width);
			pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = rawPixels[x + y * width];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
					pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
					pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
					pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
				}
			}
			pixels.flip();
			
			image.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLoaded(true);
		
		return this;
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
		
		stbi_image_free(pixels);
		setLoaded(true);
		return this;
	}
	
	/**
	 * Sketchy-balls at best
	 */
	public void unload() {
		this.destroy();
		this.raw = null;
		this.pixels.clear();
		this.pixels = null;
		setLoaded(false);
	}
	
	public void destroy() {
		glDeleteTextures(id);
	}
	
	//SETTERS ******************************************
	public void setLoaded(boolean loaded) {
		this.isLoaded = loaded;
	}
	
	//GETTERS ******************************************
	public long getiID() {
		return iID;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getPath() {
		return path;
	}
	
	public int[] getRaw() {
		return raw;
	}
	
	public ByteBuffer getBuffer() {
		return pixels;
	}
	
	//LOCAL METHODS *************************************************
	private void genGLTexture(int pFormat) {
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, pFormat, GL_UNSIGNED_BYTE, pixels);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glGenerateMipmap(GL_TEXTURE_2D);
	}
}
