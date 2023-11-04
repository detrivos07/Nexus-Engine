package nexus.engine.core.render.opengl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import nexus.engine.d2.TileTypes;

public class TextureAtlas extends Texture {
	
	private List<Texture> textures;
	private List<Vector2i> offsets;
	private List<TileTypes> types;
	
	private int size, texSize;

	public TextureAtlas(String path) {
		super(path);
	}
	
	public TextureAtlas(List<Texture> textures) {
		this.textures = textures;
		this.offsets = new ArrayList<>();
		this.types = new ArrayList<>();
		this.texSize = this.textures.get(0).getWidth();
		this.size = (int) Math.ceil(Math.sqrt(this.textures.size()));
		this.width = this.height = this.size * this.texSize;
		this.raw = new int[width * height];
		loadTexturesToAtlas();
		genGLTexture();
	}
	
	private void loadTexturesToAtlas() {
		Texture[][] texs = new Texture[size][size];
		int a = 0;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (a < textures.size()) {
					texs[x][y] = textures.get(a++);
					offsets.add(new Vector2i(x, y));
				}
				else texs[x][y] = new Texture(textures.get(0).getWidth());
			}
		}
		
		int texSize = textures.get(0).getWidth();
		for (int texY = 0; texY < size; texY++) {
			for (int pixY = 0; pixY < texSize; pixY++) {
				for (int texX = 0; texX < size; texX++) {
					for (int pixX = 0; pixX < texSize; pixX++) {
						raw[((texX * texSize) + pixX) + ((pixY * size) * texSize) + ((texY * texSize) * getWidth())] = texs[texX][texY].getRaw()[pixX + pixY * texSize];
					}
				}
			}
		}
		
		pixels = BufferUtils.createByteBuffer(getWidth() * getWidth() * 4);
		
		for (int y = 0; y < getWidth(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int pixel = raw[x + y * getWidth()];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
				pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
				pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
				pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
			}
		}
		
		pixels.flip();
	}
	
	public static TextureAtlas loadAtlas(String path) {
		List<Texture> textures = new ArrayList<>();
		String tempPath = path + "/";
		File root = new File(tempPath);
		for (int j = 0; j < root.list().length; j++) {
			textures.add(new Texture(tempPath + root.list()[j]).load());
		}
		return new TextureAtlas(textures);
	}
	
	//GETTERS
	public Vector2i getOffset(Texture t) {
		return offsets.get(textures.indexOf(t));
	}
	
	public List<Texture> getTextureList() {
		return textures;
	}
	
	public List<TileTypes> getTileTypes() {
		return types;
	}
	
	public int getWidth() {
		return size * texSize;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getTexSize() {
		return texSize;
	}
}
