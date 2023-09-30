package nexus.engine.core.io;

public interface WindowContext {
	void createContext(String context);
	void preRender();
	void updateViewport(int w, int h);
}
