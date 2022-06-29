#version 330

const int MAX_POINT_LIGHTS = 8;
const int MAX_SPOT_LIGHTS = 4;

in vec2 exTex;
in vec3 mvVertNorm;
in vec3 mvVertPos;
in vec4 mlvVPos;
in mat4 outModelViewMat;

out vec4 fragColour;

struct Fog {
	int activef;
	vec3 col;
	float density;
};

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct PointLight {
	vec3 col;
	//assumed to be in view coords
	vec3 pos;
	float intensity;
	Attenuation att;
};

struct SpotLight {
	PointLight point;
	vec3 dir;
	float range;
};

struct DirectionalLight {
	vec3 col;
	vec3 dir;
	float intensity;
};

struct Material {
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	int hasTex;
	int hasNormalMap;
	float reflectance;
};

uniform sampler2D sampler;
uniform sampler2D normalMap;
uniform sampler2D shadowMap;
uniform vec3 ambientLight;
uniform float specPower;
uniform Material material;

uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform DirectionalLight sun;

uniform Fog fog;

uniform int renderShadow;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

void setupColours(Material material, vec2 texCoord) {
	if (material.hasTex == 1) {
		ambientC = texture(sampler, texCoord);
		diffuseC = ambientC;
		speculrC = ambientC;
	} else {
		ambientC = material.ambient;
		diffuseC = material.diffuse;
		speculrC = material.specular;
	}
}

vec4 calcLightCol(vec3 colour, float intensity, vec3 pos, vec3 toLSrc, vec3 normal) {
	vec4 diffuseCol = vec4(0,0,0,0);
	vec4 specCol = vec4(0,0,0,0);
	
	//Diffuse light
	float diffuseFactor = max(dot(normal, toLSrc), 0.0);
	diffuseCol = diffuseC * vec4(colour, 1.0) * intensity * diffuseFactor;
	
	//Specular light
	vec3 cameraDir = normalize(-pos);
	vec3 fromLSrc = -toLSrc;
	vec3 reflectedLight = normalize(reflect(fromLSrc, normal));
	float specularFactor = max(dot(cameraDir, reflectedLight), 0.0);
	specularFactor = pow(specularFactor, specPower);
	specCol = speculrC * intensity * specularFactor * material.reflectance * vec4(colour, 1.0);
	
	return (diffuseCol + specCol);
}

vec4 calcPointLight(PointLight light, vec3 pos, vec3 normal) {
	vec3 lightDir = light.pos - pos;
	vec3 toLSrc = normalize(lightDir);
	vec4 lcol = calcLightCol(light.col, light.intensity, pos, toLSrc, normal);
	
	//Attenuation
	float distance = length(lightDir);
	float attInv = light.att.constant + light.att.linear * distance + light.att.exponent * distance * distance;
	return lcol / attInv;
}

vec4 calcSpotLight(SpotLight light, vec3 pos, vec3 norm) {
	vec3 ldir = light.point.pos - pos;
	vec3 toLSrc = normalize(ldir);
	vec3 fromLSrc = -toLSrc;
	float alpha = dot(fromLSrc, normalize(light.dir));
	
	vec4 col = vec4(0, 0, 0, 0);
	
	if (alpha > light.range) {
		col = calcPointLight(light.point, pos, norm);
		col *= (1.0 - (1.0 - alpha) / (1.0 - light.range));
	}
	return col;
}

vec4 calcDirLight(DirectionalLight sun, vec3 pos, vec3 norm) {
	return calcLightCol(sun.col, sun.intensity, pos, normalize(sun.dir), norm);
}

vec4 calcFog(vec3 pos, vec4 col, Fog fog, vec3 ambientLight, DirectionalLight sun) {
	vec3 fcol = fog.col * (ambientLight + sun.col * sun.intensity);
	float d = length(pos);
	float ffactor = 1.0 / exp((d * fog.density) * (d * fog.density));
	ffactor = clamp(ffactor, 0.0, 1.0);
	
	vec3 resultCol = mix(fcol, col.xyz, ffactor);
	return vec4(resultCol.xyz, col.w);
}

vec3 calcNormal(Material material, vec3 normal, vec2 tcoord, mat4 modelViewMat) {
	vec3 newNorm = normal;
	if (material.hasNormalMap == 1) {
		newNorm = texture(normalMap, tcoord).rgb;
		newNorm = normalize(newNorm * 2 - 1);
		newNorm = normalize(modelViewMat * vec4(newNorm, 0.0)).xyz;
	}
	return newNorm;
}

float calcShadow(vec4 pos) {
	if (renderShadow > 0) {
		return 1.0;
	}
	
	vec3 projCoords = pos.xyz;
	projCoords = projCoords * 0.5 + 0.5;
	
	float bias = 0.05;
	float sfactor = 0.0;
	vec2 inc = 1.0 / textureSize(shadowMap, 0);
	for (int row = -1; row <= 1; ++row) {
		for (int col = -1; col <= 1; ++col) {
			float tdepth = texture(shadowMap, projCoords.xy + vec2(row, col) * inc).r;
			sfactor += projCoords.z - bias > tdepth ? 1.0 : 0.0;
		}
	}
	sfactor /= 9.0;
	
	if (projCoords.z > 1.0) {
		sfactor = 1.0;
	}
	return 1 - sfactor;
}

void main() {
	setupColours(material, exTex);
	vec3 currNorm = calcNormal(material, mvVertNorm, exTex, outModelViewMat);
	vec4 diffuseSpecComp = calcDirLight(sun, mvVertPos, currNorm);
	if (pointLights.length() > 0) for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
		if (pointLights[i].intensity > 0) {
			diffuseSpecComp += calcPointLight(pointLights[i], mvVertPos, currNorm);
		}
	}
	
	if (spotLights.length() > 0) for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
		if (spotLights[i].point.intensity > 0) {
			diffuseSpecComp += calcSpotLight(spotLights[i], mvVertPos, currNorm);
		}
	}
	
	float shadow = calcShadow(mlvVPos);
	fragColour = clamp(ambientC * vec4(ambientLight, 1) + diffuseSpecComp * shadow, 0, 1);
	
	if (fog.activef == 1) {
		fragColour = calcFog(mvVertPos, fragColour, fog, ambientLight, sun);
	}
}