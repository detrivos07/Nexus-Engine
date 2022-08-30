package nexus.engine.core.render.opengl;

import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import nexus.engine.core.utils.MathUtils;

public class HeightMapMesh {
	private static final int MAX_COL = 255 * 255 * 255;
	public static final float STARTX = -0.5f;
	public static final float STARTZ = -0.5f;
	
	private final float minY, maxY;
	private final Mesh mesh;
	
	private int width, height;
	
	private final float[][] hArr;
	
	@SuppressWarnings("deprecation")
	public HeightMapMesh(float miny, float maxy, String heightMapPath, String texturePath, int texInc) {
		this.minY = miny;
		this.maxY = maxy;
		
		ByteBuffer buf = null;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);
			
			buf = stbi_load(heightMapPath, w, h, channels, 4);
			if (buf == null) throw new IllegalStateException("Unable to load heightMap: " + heightMapPath);
			
			width = w.get();
			height = h.get();
		}
		
		hArr = new float[height][width];
		
		Texture tex = new Texture(texturePath);
		tex.loadSTB();//TODO:: CRITICAL
		
		float incx = getXLength() / (width - 1);
		float incz = getZLength() / (height - 1);
		
		List<Float> pos = new ArrayList<Float>();
		List<Float> texCoords = new ArrayList<Float>();
		List<Integer> inds = new ArrayList<Integer>();
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				//Sets the vertex for the current position
				pos.add(STARTX + col * incx);
				float currh = getHeight(col, row, width, buf);
				hArr[row][col] = currh;
				pos.add(currh);
				pos.add(STARTZ + row * incz);
				
				//Sets the texCoords
				texCoords.add((float) texInc * (float) col / width);
				texCoords.add((float) texInc * (float) row / height);
				
				//Create inds
				if (col < width - 1 && row < height - 1) {
					int lt = row * width + col;
					int lb = (row + 1) * width + col;
					int rb = (row + 1) * width + col + 1;
					int rt = row * width + col + 1;
					
					inds.add(rt);
					inds.add(lb);
					inds.add(rb);
					
					inds.add(lt);
					inds.add(lb);
					inds.add(rt);
				}
			}
		}
		
		float[] posArr = MathUtils.listFloatToArray(pos);
		int[] indsArr = inds.stream().mapToInt(i -> i).toArray();
		float[] texArr = MathUtils.listFloatToArray(texCoords);
		float[] normsArr = calcNormals(posArr, width, height);
		this.mesh = new Mesh(posArr, texArr, normsArr, indsArr);
		Material material = new Material(tex, 0.0f);
		mesh.setMaterial(material);
		
		stbi_image_free(buf);
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getHeight(int r, int c) {
		float result = 0;
		if (r >= 0 && r < hArr.length && c >= 0 && c < hArr[r].length) result = hArr[r][c];
		return result;
	}
	
	public static float getXLength() {
		return Math.abs(-STARTX * 2);
	}
	
	public static float getZLength() {
		return Math.abs(-STARTZ * 2);
	}
	
	float getHeight(int x, int z, int w, ByteBuffer buf) {
		byte r = buf.get(x * 4 + 0 + z * 4 * w);
		byte g = buf.get(x * 4 + 1 + z * 4 * w);
		byte b = buf.get(x * 4 + 2 + z * 4 * w);
		byte a = buf.get(x * 4 + 3 + z * 4 * w);
		int argb = ((0xFF & a) << 24) | ((0xFF & r) << 16) | ((0xFF & g) << 8) | (0xFF & b);
		return this.minY + Math.abs(this.maxY - this.minY) * ((float) argb / (float) MAX_COL);
	}
	
	float[] calcNormals(float[] posArr, int w, int h) {
		Vector3f v0 = new Vector3f();
		Vector3f v1 = new Vector3f();
		Vector3f v2 = new Vector3f();
		Vector3f v3 = new Vector3f();
		Vector3f v4 = new Vector3f();
		Vector3f v12 = new Vector3f();
		Vector3f v23 = new Vector3f();
		Vector3f v34 = new Vector3f();
		Vector3f v41 = new Vector3f();
		List<Float> norms = new ArrayList<Float>();
		Vector3f normal = new Vector3f();
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				if (row > 0 && row < h - 1 && col > 0 && col < w - 1) {
					int i0 = row * w * 3 + col * 3;
					v0.x = posArr[i0];
					v0.y = posArr[i0 + 1];
					v0.z = posArr[i0 + 2];
					
					int i1 = row * w * 3 + (col - 1) * 3;
					v1.x = posArr[i1];
					v1.y = posArr[i1 + 1];
					v1.z = posArr[i1 + 2];
					v1 = v1.sub(v0);
					
					int i2 = (row + 1) * w * 3 + col * 3;
					v2.x = posArr[i2];
					v2.y = posArr[i2 + 1];
					v2.z = posArr[i2 + 2];
					v2 = v2.sub(v0);
					
					int i3 = row * w * 3 + (col + 1) * 3;
					v3.x = posArr[i3];
					v3.y = posArr[i3 + 1];
					v3.z = posArr[i3 + 2];
					v3 = v3.sub(v0);
					
					int i4 = (row - 1) * w * 3 + col * 3;
					v4.x = posArr[i4];
					v4.y = posArr[i4 + 1];
					v4.z = posArr[i4 + 2];
					v4 = v4.sub(v0);
					
					v1.cross(v2, v12);
					v12.normalize();
					
					v2.cross(v3, v23);
					v23.normalize();
					
					v3.cross(v4, v34);
					v34.normalize();
					
					v4.cross(v1, v41);
					v41.normalize();
					
					normal = v12.add(v23).add(v34).add(v41);
					normal.normalize();
				} else {
					normal.x = 0;
					normal.y = 1;
					normal.z = 0;
				}
				
				normal.normalize();
				norms.add(normal.x);
				norms.add(normal.y);
				norms.add(normal.z);
			}
		}
		return MathUtils.listFloatToArray(norms);
	}
}
