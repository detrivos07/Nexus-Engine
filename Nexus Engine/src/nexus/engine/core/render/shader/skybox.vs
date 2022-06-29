#version 330

layout (location=0) in vec3 pos;
layout (location=1) in vec2 inTex;
layout (location=2) in vec3 vNorm;

out vec2 exTex;

uniform mat4 modelViewMat;
uniform mat4 projMat;

void main() {
	gl_Position = projMat * modelViewMat * vec4(pos, 1.0);
	exTex = inTex;
}