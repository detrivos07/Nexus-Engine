#version 330

in vec2 exTex;
in vec3 mvPos;

out vec4 fragColour;

uniform sampler2D sampler;
uniform vec3 ambientLight;

void main() {
	fragColour = vec4(ambientLight, 1) * texture(sampler, exTex);
}