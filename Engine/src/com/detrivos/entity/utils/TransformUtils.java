package com.detrivos.entity.utils;

import org.joml.*;

public class TransformUtils {

	public static Matrix4f getProjection(Matrix4f target, Vector3f scale, Vector3f pos) {
		return target.translate(pos).scale(scale);
	}
}
