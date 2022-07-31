package nexus.engine;

import nexus.engine.core.io.*;

public interface IProgram {
	void init(DisplayManager display);
	void input(DisplayManager display);
	void update(DisplayManager display);
	void render(DisplayManager display);
	void destroy();
}
