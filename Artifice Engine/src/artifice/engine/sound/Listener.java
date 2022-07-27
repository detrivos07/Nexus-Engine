package artifice.engine.sound;

import static org.lwjgl.openal.AL10.*;

import org.joml.Vector3f;

public class Listener {
	
	public Listener() {
		this(new Vector3f());
	}
	
	public Listener(Vector3f pos) {
		alListener3f(AL_POSITION, pos.x, pos.y, pos.z);
		alListener3f(AL_VELOCITY, 0, 0, 0);
	}
	
	public void setOrientation(Vector3f at) {
		alListener3f(AL_ORIENTATION, at.x, at.y, at.z);
	}
}
