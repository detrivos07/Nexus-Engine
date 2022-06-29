package nexus.engine.core.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class FileUtils {//TODO dvs :: LOGGING LIB
	

	public static final String userHome = System.getProperty("user.home");
	public static final String userWorkPath = System.getProperty("user.dir")+"\\logs";
	
	public static File createFile(String path) {
		File file = null;
		try {
			file = new File(path);
			if (file.createNewFile()) return file;
		} catch (IOException e) {
			System.out.println("File creation failed!");
		}
		return null;
	}
	
	public static boolean writeLinesToFile(List<String> lines, File file) {
		try {
			FileWriter writer = new FileWriter(file, true);
			for (String line : lines) writer.write(line+System.lineSeparator());
			writer.close();
			return true;
		} catch (IOException e) {
			System.out.println("Unable to write lines!");
		}
		return false;
	}
	
	public static List<String> readAllLines(String path) {
		List<String> list = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(FileUtils.class.getName()).getResourceAsStream(path)))) {
			String line;
			while ((line = br.readLine()) != null) list.add(line);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Unable to read lines!");
		}
		System.out.println("Successfully read text file: " + path);
		return list;
	}
	
	public static boolean resourceExists(String path) {
		boolean result;
		File file = new File(path);
		result = (file.exists());
		return result;
	}
	
	public static InputStreamReader newReader(String path) {
		return new InputStreamReader(FileUtils.class.getResourceAsStream(path));
	}
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
		ByteBuffer buffer = null;
		
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1) ;
			} catch (IOException e) {
				System.out.println("Exception occured when trying to load resource: " + resource);
			}
		} else {
			try (
				InputStream source = FileUtils.class.getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)) {
				buffer = BufferUtils.createByteBuffer(bufferSize);
				
				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) break;
					if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 2);
				}
			} catch (IOException e) {
				System.out.println("Exception occured when trying to load resource: " + resource);
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
