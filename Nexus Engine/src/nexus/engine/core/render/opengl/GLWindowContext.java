package nexus.engine.core.render.opengl;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.NativeType;

import nexus.engine.core.io.WindowContext;

public class GLWindowContext implements WindowContext {
	
	private boolean cullfaces = false;
	private boolean depthTest = false;
	
	@Override
	/**
	 * enables BLEND, DEPTH_TEST by default
	 */
	public void createContext(String context) {
		initContext(context);
		GL.createCapabilities();
		restore();
		setClearColour(0.0f, 0.0f, 0.1f, 0.0f);
		if (cullfaces) cullFaces();
	}
	
	void initContext(String context) {
		if (context.contains("cull")) cullfaces = true;
		if (context.contains("depth")) depthTest = true;
	}
	
	public void cullFaces() {
		enable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	public void showTris() {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	
	public void restore() {
		enable(GL_BLEND);
		if (depthTest) enable(GL_DEPTH_TEST);
		enable(GL_STENCIL_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Context specific method of glEnable()
	 * @param target
	 */
	public void enable(@NativeType(value="GLenum") int target) {
		glEnable(target);
	}
	
	/**
	 * Context specific method of glDisable()
	 * @param target
	 */
	public void disable(@NativeType(value="GLenum") int target) {
		glDisable(target);
	}
	
	@Override
	public void updateViewport(int w, int h) {
		glViewport(0, 0, w, h);
	}
	
	@Override
	public void preRender() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	/**
	 * sets the GL Clear colour to the specified values
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setClearColour(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
}
