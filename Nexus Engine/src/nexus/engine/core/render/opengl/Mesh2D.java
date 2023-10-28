package nexus.engine.core.render.opengl;

public class Mesh2D extends Mesh {
	static float[] verts = new float[] {
			-1f, 1f, 0, //TL
			1f, 1f, 0, //TR
			1f, -1f, 0, //BR
			-1f, -1f, 0 //BL
	};
	
	static float[] texs = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1,
	};
	
	static int[] inds = new int[] {
			0, 1, 2,
			2, 3, 0
	};

	public Mesh2D() {
		super(verts, texs, new float[0], inds);
	}
}
