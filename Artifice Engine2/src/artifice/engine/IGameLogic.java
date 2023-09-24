package artifice.engine;

import artifice.engine.io.*;

public interface IGameLogic {
	void init(Window window);
	void input(Window window, Camera camera);
	void update(Window window);
	void render(Window window, Camera camera);
	void destroy();
}