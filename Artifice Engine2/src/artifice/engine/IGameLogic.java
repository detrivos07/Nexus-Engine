package artifice.engine;

public interface IGameLogic {
	void init();
	void input();
	void update();
	void render();
	void destroy();
}