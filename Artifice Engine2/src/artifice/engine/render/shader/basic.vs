#version 330

layout (location=0) in vec3 pos;
layout (location=1) in vec2 texs;
uniform mat4 projMat;

varying vec2 texCoords;

void main() {
	texCoords = texs;
	gl_Position = projMat * vec4(pos, 1.0);
}