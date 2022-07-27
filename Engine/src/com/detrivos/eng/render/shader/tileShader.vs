#version 140

in vec3 pos;
in mat4 projection;
in vec2 textures0;
in vec2 textures1;
in vec2 textures2;
in vec2 textures3;

out vec2 texCoords;

void main() {
	vec2 newTex = vec2(0, 0);
	if (gl_VertexID == 0) {
		newTex = textures0;
	} else if (gl_VertexID == 1) {
		newTex = textures1;
	} else if (gl_VertexID == 2) {
		newTex = textures2;
	} else if (gl_VertexID == 3) {
		newTex = textures3;
	}
	texCoords = newTex;
	gl_Position = projection * vec4(pos, 1.0);
}