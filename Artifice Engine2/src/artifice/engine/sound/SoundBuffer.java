package artifice.engine.sound;

import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.*;

public class SoundBuffer {
	
	private int id;
	
	public SoundBuffer(String path) {
		loadVorbisSound(path);
	}
	
	public void destroy() {
		alDeleteBuffers(id);
	}
	
	public int getID() {
		return id;
	}
	
	void loadVorbisSound(String path) {
		stackPush();
		IntBuffer channelsBuffer = stackMallocInt(1);
		stackPush();
		IntBuffer sampleRateBuffer = stackMallocInt(1);
		
		ShortBuffer raw = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);
		if (raw == null) {stackPop();stackPop();throw new IllegalStateException("Unable to load sound at: " + path);}
		
		int channels = channelsBuffer.get();
		int sampleRate = sampleRateBuffer.get();
		stackPop();
		stackPop();
		
		int format = -1;
		if (channels == 1) format = AL_FORMAT_MONO16;
		else if (channels == 2) format = AL_FORMAT_STEREO16;
		
		id = alGenBuffers();
		alBufferData(id, format, raw, sampleRate);
	}
}
