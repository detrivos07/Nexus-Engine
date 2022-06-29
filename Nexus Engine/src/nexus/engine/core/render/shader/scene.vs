#version 330

const int MAX_WEIGHTS = 4;
const int MAX_JOINTS = 150;

layout (location=0) in vec3 pos;
layout (location=1) in vec2 inTex;
layout (location=2) in vec3 norm;
layout (location=3) in vec4 weights;
layout (location=4) in ivec4 jinds;
layout (location=5) in mat4 modelViewIMat;
layout (location=9) in mat4 modelLightViewIMat;

out vec2 exTex;
out vec3 mvVertNorm;
out vec3 mvVertPos;
out vec4 mlvVPos;
out mat4 outModelViewMat;

uniform int instanced;
uniform mat4 jointsMat[MAX_JOINTS];
uniform mat4 modelViewMat;
uniform mat4 projMat;
uniform mat4 modelLightViewMat;
uniform mat4 orthoProjMat;

void main() {
	vec4 initPos = vec4(0, 0, 0, 0);
	vec4 initNormal = vec4(0, 0, 0, 0);
	mat4 modelViewMatrix;
	mat4 lightViewMatrix;
	
	if (instanced > 0) {
		modelViewMatrix = modelViewIMat;
		lightViewMatrix = modelLightViewIMat;
		
		initPos = vec4(pos, 1.0);
		initNormal = vec4(norm, 0.0);
	} else {
		modelViewMatrix = modelViewMat;
		lightViewMatrix = modelLightViewMat;
		
		int count = 0;
		for(int i = 0; i < MAX_WEIGHTS; i++) {
			float weight = weights[i];
			if (weight > 0) {
				count++;
				int jindex = jinds[i];
				vec4 tmpPos = jointsMat[jindex] * vec4(pos, 1.0);
				initPos += weight * tmpPos;
				
				vec4 tmpNorm = jointsMat[jindex] * vec4(norm, 0.0);
				initNormal += weight * tmpNorm;
			}
		}
		if (count == 0) {
			initPos = vec4(pos, 1.0);
			initNormal = vec4(norm, 0.0);
		}
	}
	
	
	vec4 mvPos = modelViewMatrix * initPos;
	gl_Position = projMat * mvPos;
	exTex = inTex;
	mvVertNorm = normalize(modelViewMatrix * initNormal).xyz;
	mvVertPos = mvPos.xyz;
	mlvVPos = orthoProjMat * lightViewMatrix * initPos;
	outModelViewMat = modelViewMatrix;
}