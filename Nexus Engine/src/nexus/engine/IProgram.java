package nexus.engine;

public interface IProgram extends Inputable {
	void init();
	void update();
	void render();
	void destroy();
}
