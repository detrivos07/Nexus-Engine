#version 330

layout (location=0) in vec3 pos;
layout (location=1) in mat4 projMat;
layout (location=5) in vec2 texs0;
layout (location=6) in vec2 texs1;
layout (location=7) in vec2 texs2;
layout (location=8) in vec2 texs3;

out vec2 texCoords;

void main() {
	vec2 newTex = vec2(0, 0);
	if (gl_VertexID == 0) {
		newTex = texs0;
	} else if (gl_VertexID == 1) {
		newTex = texs1;
	} else if (gl_VertexID == 2) {
		newTex = texs2;
	} else if (gl_VertexID == 3) {
		newTex = texs3;
	}
	texCoords = newTex;
	gl_Position = projMat * vec4(pos, 1.0);
}