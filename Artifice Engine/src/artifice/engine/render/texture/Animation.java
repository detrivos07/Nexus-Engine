package artifice.engine.render.texture;

public class Animation {
	
	Texture[] frames;
	int pointer;
	
	double timeElapsed, timeCurrent, timeLast, fps;
	
	public Animation(String path) {
		fps = 0;
		timeElapsed = 0;
		timeCurrent = 0;
		pointer = 0;
		timeLast = 0;
		frames = new Texture[] {
				new Texture(path)
		};
	}
	
	public Animation(int amt, int fps, String path) {
		pointer = 0;
		timeElapsed = 0;
		timeCurrent = 0;
		timeLast = System.nanoTime() / 1000000000.0;
		this.fps = 1.0 / fps;
		
		frames = new Texture[amt];
		for (int i = 0; i < amt; i++) frames[i] = new Texture(path + "/" + i);
	}
	
	public void bind(int sampler) {
		timeCurrent = System.nanoTime() / 1000000000.0;
		timeElapsed += timeCurrent - timeLast;
		
		if (fps != 0)  if (timeElapsed >= fps) {
			timeElapsed = 0;
			pointer++;
		}
		
		if (pointer >= frames.length) pointer = 0;
		timeLast = timeCurrent;
		
		frames[pointer].bind(sampler);
	}
}
