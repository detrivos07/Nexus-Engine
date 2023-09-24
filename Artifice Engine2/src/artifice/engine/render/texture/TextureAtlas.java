package artifice.engine.render.texture;

import static org.lwjgl.opengl.GL20.*;

import java.util.*;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import artifice.engine.level.tile.TileTypes;

public class TextureAtlas extends Texture {
	
	private List<Texture> textures;
	private List<Vector2i> offsets;
	private List<TileTypes> types;

	private int size, texSize;
	
	public TextureAtlas(List<Texture> textures) {
		this.textures = textures;
		this.offsets = new ArrayList<Vector2i>();
		this.types = new ArrayList<TileTypes>();
		this.texSize = this.textures.get(0).getWidth();
		this.size = (int) Math.ceil(Math.sqrt(this.textures.size()));
		this.width = this.height = this.size * this.texSize;
		this.raw = new int[width * height];
		loadTexturesToAtlas();
		genGLTexture();
	}
	
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}

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
}
