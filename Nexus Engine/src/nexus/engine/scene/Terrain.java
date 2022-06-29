package nexus.engine.scene;

import org.joml.Vector3f;

import nexus.engine.core.render.opengl.HeightMapMesh;

public class Terrain {

	private final GameObject[] meshes;
	private final int terrainSize, vpCol, vpRow;
	private final HeightMapMesh mesh;
	
	private final Box2d[][] bbs;
	
	public Terrain(int terrainSize, Vector3f scale, float miny, float maxy, String hmap, String texPath, int texInc) {
		this.terrainSize = terrainSize;
		meshes = new GameObject[terrainSize * terrainSize];
		mesh = new HeightMapMesh(miny, maxy, hmap, texPath, texInc);
		
		vpCol = mesh.getWidth() - 1;
		vpRow = mesh.getHeight() - 1;
		
		bbs = new Box2d[terrainSize][terrainSize];
		for (int r = 0; r < terrainSize; r++) {
			for (int c = 0; c < terrainSize; c++) {
				float xd = (c - ((float) terrainSize - 1) / 2.0f) * scale.x * HeightMapMesh.getXLength();
				float zd = (r - ((float) terrainSize - 1) / 2.0f) * scale.z * HeightMapMesh.getZLength();
				
				GameObject tblock = new GameObject(mesh.getMesh());
				tblock.setScale(new Vector3f(scale));
				tblock.setPos(xd, 0, zd);
				meshes[r * terrainSize + c] = tblock;
				
				bbs[r][c] = getBB(tblock);
			}
		}
	}
	
	public float getHeight(Vector3f pos) {
		float result = Float.MIN_VALUE;
		
		Box2d bb = null;
		boolean found = false;
		GameObject tblock = null;
		for (int r = 0; r < terrainSize && !found; r++) {
			for (int c = 0; c < terrainSize && !found; c++) {
				tblock = meshes[r * terrainSize + c];
				bb = bbs[r][c];
				found = bb.contains(pos.x, pos.z);
			}
		}
		
		if (found) {
			Vector3f[] triangle = getTriangle(pos, bb, tblock);
			result = interpolateHeight(triangle[0], triangle[1], triangle[2], pos.x, pos.z);
		}
		
		return result;
	}
	
	public GameObject[] getMeshes() {
		return meshes;
	}
	
	protected Vector3f[] getTriangle(Vector3f pos, Box2d bb, GameObject tblock) {
		float cw = bb.w / vpCol;
		float ch = bb.h / vpRow;
		int c = (int) ((pos.x - bb.x) / cw);
		int r = (int) ((pos.z - bb.y) / ch);
		
		Vector3f[] triangle = new Vector3f[3];
		triangle[1] = new Vector3f(
				bb.x + c * cw,
				getWorldHeight(r + 1, c, tblock),
				bb.y + (r + 1) * ch);
		triangle[2] = new Vector3f(
				bb.x + (c + 1) * cw,
				getWorldHeight(r, c + 1, tblock),
				bb.y + r * ch);
		if (pos.z < getDiagonalZCoord(triangle[1].x, triangle[1].z, triangle[2].x, triangle[2].z, pos.x)) {
			triangle[0] = new Vector3f(
					bb.x + c * cw,
					getWorldHeight(r, c, tblock),
					bb.y + r * ch);
		} else {
			triangle[0] = new Vector3f(
					bb.x + (c + 1) * cw,
					getWorldHeight(r + 2, c + 1, tblock),
					bb.y + (r + 1) * ch);
		}
		return triangle;
	}
	
	protected float getDiagonalZCoord(float x1, float z1, float x2, float z2, float x) {
		return ((z1 - z2) / (x1 - x2)) * (x - x1) + z1;
	}
	
	protected float getWorldHeight(int r, int c, GameObject tblock) {
		float y = mesh.getHeight(r, c);
		return y * tblock.getScale().y + tblock.getPos().y;
	}
	
	protected float interpolateHeight(Vector3f pa, Vector3f pb, Vector3f pc, float x, float z) {
		float a = (pb.y - pa.y) * (pc.z - pa.z) - (pc.y - pa.y) * (pb.z - pa.z);
		float b = (pb.z - pa.z) * (pc.x - pa.x) - (pc.z - pa.z) * (pb.x - pa.x);
		float c = (pb.x - pa.x) * (pc.y - pa.y) - (pc.x - pa.x) * (pb.y - pa.y);
		float d = -(a * pa.x + b * pa.y + c * pa.z);
		float y = (-d - a * x - c * z) / b;
		return y;
	}
	
	Box2d getBB(GameObject o) {
		Vector3f scale = o.getScale();
		Vector3f pos = o.getPos();
		
		float tlx = HeightMapMesh.STARTX * scale.x + pos.x;
		float tlz = HeightMapMesh.STARTZ * scale.z + pos.z;
		float w = Math.abs(HeightMapMesh.STARTX * 2) * scale.x;
		float h = Math.abs(HeightMapMesh.STARTZ * 2) * scale.z;
		return new Box2d(tlx, tlz, w, h);
	}
	
	static class Box2d {
		public float x, y, w, h;
		
		public Box2d(float x, float y, float w, float h) {
			this.x =x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		
		public boolean contains(float x2, float y2) {
			return x2 >= x && y2 >= y && x2 < x + w && y2 < y + h;
		}
	}
}
