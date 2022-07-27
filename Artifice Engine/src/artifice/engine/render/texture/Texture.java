package artifice.engine.render.texture;

import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	
	protected int id;
	protected int[] raw;
	protected ByteBuffer pixels;
	protected int width, height;
	protected String path, name;
	
	public Texture() {
		width = height = 0;
		raw = new int[width * height * 4];
		for (int i = 0 ; i < raw.length; i++) raw[i] = 0xFF000000;
	}
	
	/**
	 * Generates a square, black texture
	 * @param size Size of blank texture to be generated
	 */
	public Texture(int size) {
		width = height = size;
		raw = new int[width * height * 4];
		for (int i = 0 ; i < raw.length; i++) raw[i] = 0xFF000000;
	}
	
	/**
	 * Generates a square, black texture
	 * @param size Size of blank texture to be generated
	 * @param col Color in hexidecimal
	 */
	public Texture(int size, int col) {
		width = height = size;
		raw = new int[width * height * 4];
		pixels = BufferUtils.createByteBuffer(width * height * 4);
		for (int i = 0 ; i < raw.length; i++) raw[i] = col;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = raw[x * width + y];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
				pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
				pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
				pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
			}
		}
		pixels.flip();
		genGLTexture();
	}
	
	public Texture(int[] raw, int deg) {
		width = (int) Math.sqrt(raw.length);
		height = width;
		pixels = BufferUtils.createByteBuffer(width * height * 4);
		
		raw = rotate(raw, width, deg);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = raw[x * width + y];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
				pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
				pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
				pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
			}
		}
		
		pixels.flip();
		
		genGLTexture();
	}
	
	public Texture(String path) {
		this.path = path;
		//try {this.name = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));} catch (Exception e) {System.out.println("fudge you");};
		load();
		genGLTexture();
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
	
	/**
	 * Destroys this object at id in memory
	 */
	public void destroy() {
		glDeleteTextures(id);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	//GETTERS
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
	
	public String getPath() {
		return path;
	}
	
	public ByteBuffer getBuffer() {
		return pixels;
	}
	
	protected void genGLTexture() {
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	protected void load() {
		File file;
		if (path.contains(".")) file = new File(path);
		else file = new File(path + ".png");
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			this.width = image.getWidth();
			this.height = image.getHeight();
			
			raw = new int[width * height * 4];
			raw = image.getRGB(0, 0, width, height, null, 0, width);
			pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = raw[x * width + y];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
					pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
					pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
					pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
				}
			}
			
			pixels.flip();
		} catch (IOException e) {
			System.out.println("Unable to load texture at: " + path);
			e.printStackTrace();
		}
	}
	
	private int[] rotate(int[] pixels, int w, int deg) {
		int[] result = new int[pixels.length];
		
		switch (deg) { 
		case 270:
			int[] ninety = new int[pixels.length];
			int[] temp = new int[pixels.length];
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					ninety[x + (y * w)] = pixels[(x * w) + y];
				}
			}
			
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					temp[x + (y * w)] = ninety[((w * (y + 1)) - 1) - x];
				}
			}
			
			for (int i = 0; i < pixels.length; i++) {
				result[i] = temp[(pixels.length - 1) - i];
			}
			break;
			
		case 180:
			for (int i = 0; i < pixels.length; i++) {
				result[i] = pixels[(pixels.length - 1) - i];
			}
			break;
			
		case 90:
			int[] ninety1 = new int[pixels.length];
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					ninety1[x + (y * w)] = pixels[(x * w) + y];
				}
			}
			
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					result[x + (y * w)] = ninety1[((w * (y + 1)) - 1) - x];
				}
			}
			break;
		default:
			throw new IllegalStateException("Attempting to rotate by " + deg + " degrees.");
		}
		return result;
	}
}
