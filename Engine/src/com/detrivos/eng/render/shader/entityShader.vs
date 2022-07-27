#version 140

in vec3 pos;
in vec2 textures;
uniform mat4 projection;

varying vec2 texCoords;

void main() {
	texCoords = textures;
	gl_Position = projection * vec4(pos, 1.0);
}