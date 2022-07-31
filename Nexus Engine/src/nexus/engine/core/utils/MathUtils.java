package nexus.engine.core.utils;

import java.util.List;

public class MathUtils {
	
	public static float[] listFloatToArray(List<Float> list) {
		int s = list != null ? list.size() : 0;
		float[] floatArr = new float[s];
		for (int i = 0; i < s; i++) floatArr[i] = list.get(i);
		return floatArr;
	}
	
	public static int[] listIntToArray(List<Integer> list) {
		int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
		return result;
	}
}
