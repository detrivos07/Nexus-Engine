package nexus.engine.core.io;

public interface WindowContext {
	void createContext();
	void preRender();
	void updateViewport(int w, int h);
}
