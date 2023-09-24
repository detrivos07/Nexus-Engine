package artifice.engine.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;

import org.lwjgl.BufferUtils;

public class LoaderUtils {

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
		ByteBuffer buffer = null;
		
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try (
				InputStream source = LoaderUtils.class.getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)) {
				buffer = BufferUtils.createByteBuffer(bufferSize);
				
				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) break;
					if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 2);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.flip();
        return buffer;
	}
    
	public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}
}
