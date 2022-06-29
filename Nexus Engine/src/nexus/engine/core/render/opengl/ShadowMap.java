package nexus.engine.core.render.opengl;

import static org.lwjgl.opengl.GL30.*;

public class ShadowMap {
	public static final int MAPSIZE = 1024;
	
	private final int fbo;
	private final Texture dm;
	
	public ShadowMap() {
		fbo = glGenFramebuffers();
		dm = new Texture(MAPSIZE, MAPSIZE, GL_DEPTH_COMPONENT);
		
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, dm.getID(), 0);
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) throw new IllegalStateException("Unable to create framebuffer");
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void destroy() {
		dm.destroy();
		glDeleteFramebuffers(fbo);
	}
	
	//GETTERS
	public Texture getDepthMap() {
		return dm;
	}
	
	public int getFBO() {
		return fbo;
	}
}
