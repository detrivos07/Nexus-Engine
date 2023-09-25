package artifice.engine;

import artifice.engine.io.*;

public interface IGameLogic {
	void init();
	void input(Camera camera);
	void update();
	void render(Camera camera);
	void destroy();
}