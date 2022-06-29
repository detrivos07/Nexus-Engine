package nexus.engine.utils.loaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import nexus.engine.core.render.opengl.InstancedMesh;
import nexus.engine.core.render.opengl.Mesh;

public class OBJLoader {

	public static Mesh loadMesh(String path) {
		return loadMesh(path, 1);
	}
	
	public static Mesh loadMesh(String path, int instances) {
		if (path == "") return null;
		List<String> lines = readAllLines(path);
		
		List<Vector3f> verts = new ArrayList<Vector3f>();
		List<Vector2f> texs = new ArrayList<Vector2f>();
		List<Vector3f> norms = new ArrayList<Vector3f>();
		List<Face> faces = new ArrayList<Face>();
		
		for (String line : lines) {
			String[] tokens = line.split("\\s+");
			switch (tokens[0]) {
				case "v":
					//Geometric Vertex
					Vector3f vec = new Vector3f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					verts.add(vec);
					break;
				case "vt":
					//Geometric Vertex
					Vector2f tex = new Vector2f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2]));
					texs.add(tex);
					break;
				case "vn":
					//Geometric Vertex
					Vector3f norm = new Vector3f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					norms.add(norm);
					break;
				case "f":
					//Geometric Vertex
					Face face = new Face(tokens[1], tokens[2], tokens[3]);
					faces.add(face);
					break;
				default:
					break;
			}
		}
		
		return reorderLists(verts, texs, norms, faces, instances);
	}
	
	private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> texList, List<Vector3f> normList, List<Face> faces, int instances) {
		List<Integer> inds = new ArrayList<Integer>();
		float[] posArr = new float[posList.size() * 3];
		int i = 0;
		for (Vector3f pos : posList) {
			posArr[i * 3] = pos.x;
			posArr[i * 3 + 1] = pos.y;
			posArr[i * 3 + 2] = pos.z;
			i++;
		}
		float[] texArr = new float[posList.size() * 2];
		float[] normArr = new float[posList.size() * 3];
		
		for (Face face : faces) {
			IdxGroup[] groups = face.getFaceVertexIndices();
			for (IdxGroup group : groups) processFaceVertex(group, texList, normList, inds, texArr, normArr);
		}
		
		int[] indsArr = new int[inds.size()];
		indsArr = inds.stream().mapToInt((Integer v) -> v).toArray();
		Mesh m = null;
		if (instances < 2) m = new Mesh(posArr, texArr, normArr, indsArr);
		else m = new InstancedMesh(posArr, texArr, normArr, indsArr, instances);
		return m;
	}
	
	private static void processFaceVertex(IdxGroup inds, List<Vector2f> texList, List<Vector3f> normList, List<Integer> indList, float[] texArr, float[] normArr) {
		//index for verts
		int posIdx = inds.idxPos;
		indList.add(posIdx);
		
		//reorder Texture Coords
		if (inds.idxTex >= 0) {
			Vector2f texCoord = texList.get(inds.idxTex);
			texArr[posIdx * 2] = texCoord.x;
			texArr[posIdx * 2 + 1] = 1 - texCoord.y;
		}
		//reorder Normals
		if (inds.idxNorm >= 0) {
			Vector3f norm = normList.get(inds.idxNorm);
			normArr[posIdx * 3] = norm.x;
			normArr[posIdx * 3 + 1] = norm.y;
			normArr[posIdx * 3 + 2] = norm.z;
		}
	}
	
	public static List<String> readAllLines(String path) {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(OBJLoader.class.getResourceAsStream(path)));
			String line;
			while ((line = br.readLine()) != null) list.add(line);
		} catch (Exception e) {
			System.out.println("Failed to load file at path: " + path);
			e.printStackTrace();
		}
		return list;
	}
	
	public static class Face {
		
		private IdxGroup[] groups = new IdxGroup[3];
		
		public Face(String v1, String v2, String v3) {
			groups = new IdxGroup[3];
			//parse lines
			groups[0] = parseLine(v1);
			groups[1] = parseLine(v2);
			groups[2] = parseLine(v3);
		}
		
		private IdxGroup parseLine(String line) {
			IdxGroup group = new IdxGroup();
			
			String[] lineTokens = line.split("/");
			int length = lineTokens.length;
			group.idxPos = Integer.parseInt(lineTokens[0]) - 1;
			if (length > 1) {
				String tex = lineTokens[1];
				group.idxTex = tex.length() > 0 ? Integer.parseInt(tex) - 1 : IdxGroup.NO_VALUE;
				if (length > 2) group.idxNorm = Integer.parseInt(lineTokens[2]) - 1;
			}
			return group;
		}
		
		public IdxGroup[] getFaceVertexIndices() {
			return groups;
		}
	}
	
	public static class IdxGroup {
		public static final int NO_VALUE = -1;
		
		public int idxPos, idxTex, idxNorm;
		
		public IdxGroup() {
			idxPos = NO_VALUE;
			idxTex = NO_VALUE;
			idxNorm = NO_VALUE;
		}
	}
}
