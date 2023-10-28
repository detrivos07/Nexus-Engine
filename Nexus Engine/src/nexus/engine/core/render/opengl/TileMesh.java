package nexus.engine.core.render.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import nexus.engine.core.render.utils.VBO;

public class TileMesh extends Mesh {
	public static final int MAX = 10000;// MAX instances to be rendered
	public static final int f_COUNT = 24;
	
	private VBO inst;
	private FloatBuffer buffer;

	public TileMesh(float[] pos, float[] texCoords, float[] normals, int[] inds) {
		super(pos, texCoords, normals, inds);
		buffer = BufferUtils.createFloatBuffer(MAX * f_COUNT);
	}

}
