package artifice.engine.sound;

import static org.lwjgl.openal.AL10.*;

import org.joml.Vector3f;

public class Source {
	
	private int id;
	
	private float gain = 1f;
	private float pitch = 1f;
	
	public Source(boolean loop, boolean relative) {
		this.id = alGenSources();
		
		if (loop) alSourcei(id, AL_LOOPING, AL_TRUE);
		if (relative) alSourcei(id, AL_SOURCE_RELATIVE, AL_TRUE);
		setPos(new Vector3f());
		setSpeed(new Vector3f());
		setGain(gain);
		setPitch(pitch);
	}
	
	public void play() {
		alSourcePlay(id);
	}
	
	public void pause() {
		alSourcePause(id);
	}
	
	public void stop() {
		alSourceStop(id);
	}
	
	public void destroy() {
		stop();
		alDeleteSources(id);
	}
	
	public void setBuffer(int bufferId) {
        stop();
        alSourcei(id, AL_BUFFER, bufferId);
	}
	
    public void setPos(Vector3f position) {
        alSource3f(id, AL_POSITION, position.x, position.y, position.z);
    }
    
    public void setSpeed(Vector3f speed) {
        alSource3f(id, AL_VELOCITY, speed.x, speed.y, speed.z);
    }
    
    public void setGain(float gain) {
    	this.gain = gain;
        alSourcef(id, AL_GAIN, gain);
    }
    
    public void setPitch(float pitch) {
    	this.pitch = pitch;
    	alSourcef(id, AL_PITCH, pitch);
    }
    
	public void setProperty(int param, float value) {
    	alSourcef(id, param, value);
	}
    
	public boolean isPlaying() {
		return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	public float getGain() {
		return gain;
	}
	
	public float getPitch() {
		return pitch;
	}
}
