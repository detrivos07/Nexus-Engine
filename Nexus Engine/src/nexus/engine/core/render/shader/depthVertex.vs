#version 330

const int MAX_WEIGHTS = 4;
const int MAX_JOINTS = 150;

layout (location=0) in vec3 pos;
layout (location=1) in vec2 inTex;
layout (location=2) in vec3 vNorm;
layout (location=3) in vec4 jointWeights;
layout (location=4) in ivec4 jointIndices;
layout (location=5) in mat4 modelViewIMat;
layout (location=9) in mat4 modelLightViewIMat;

uniform int instanced;
uniform mat4 jointsMat[MAX_JOINTS];
uniform mat4 modelLightViewMat;
uniform mat4 orthoProjMat;

void main() {
	vec4 initPos = vec4(0, 0, 0, 0);
	mat4 modelLightViewMatrix;
	if (instanced > 0) {
		modelLightViewMatrix = modelLightViewIMat;
		initPos = vec4(pos, 1.0);
	} else {
		modelLightViewMatrix = modelLightViewMat;
		
		int count = 0;
		for(int i = 0; i < MAX_WEIGHTS; i++) {
			float weight = jointWeights[i];
			if (weight > 0) {
				count++;
				int jointIndex = jointIndices[i];
				vec4 tmpPos = jointsMat[jointIndex] * vec4(pos, 1.0);
				initPos += weight * tmpPos;
			}
		}
		if (count == 0) {
			initPos = vec4(pos, 1.0);
		}
	}
	gl_Position = orthoProjMat * modelLightViewMatrix * initPos;
}