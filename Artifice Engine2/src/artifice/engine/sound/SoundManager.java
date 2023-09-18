package artifice.engine.sound;

import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.*;

import org.lwjgl.openal.*;

public class SoundManager {
	
	private long device, context;
	
	private Listener l;
	
	private List<SoundBuffer> buffers;
	private Map<String, Source> sources;
	
	public SoundManager() {
		buffers = new ArrayList<SoundBuffer>();
		sources = new HashMap<String, Source>();
	}
	
	public void init() {
		device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
		if (device == NULL) throw new IllegalStateException("Unable to create openAL device!");
		int[] attribs = new int[] {0};
		context = alcCreateContext(device, attribs);
		if (context == NULL) throw new IllegalStateException("Unable to create openAL context");
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
		
		l = new Listener();
	}
	
	public void playSource(String name) {
		Source s = sources.get(name);
		if (s != null) s.play();
	}
	
	public void addSource(String name, Source source) {
		sources.put(name, source);
	}
	
	public void removeSource(String name) {
		sources.remove(name);
	}
	
	public void addBuffer(SoundBuffer buffer) {
		buffers.add(buffer);
	}
	
	public void destroy() {
		for (SoundBuffer b : buffers) b.destroy();
		for (Source s : sources.values()) s.destroy();
		if (context != NULL) alcDestroyContext(context);
		if (device != NULL) alcCloseDevice(device);
	}
	
	public Listener getListener() {
		return l;
	}
	
	public Source getSource(String name) {
		return sources.get(name);
	}
}
