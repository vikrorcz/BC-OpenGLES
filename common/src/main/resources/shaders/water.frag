#version 100

precision highp float;

varying vec2 v_TexCoord;
varying vec3 v_Normal;
varying vec3 v_FragPos;

uniform vec3 u_LightPosition;
uniform sampler2D u_TexCoord;
uniform vec4 u_Color;
uniform vec3 u_CameraPosition;
uniform float u_Time;

// Fog constants
const vec3 fogColor = vec3(130.9 / 255.0, 126.0 / 255.0, 115.2 / 255.0); // Gray fog color
const float fogStart = 25000.0;
const float fogEnd = 35000.0;

const vec3 lightColor = vec3(1.0, 1.0, 1.0); // White light
const float ambientStrength = 0.7;
const float specularStrength = 0.7;
const float specularShininess = 2.0;

void main() {
    vec2 scale = v_FragPos.xz * 0.001; // Scaling for the texture
    vec2 animatedTexture = scale;
    animatedTexture.x += u_Time * 0.012;

    vec4 texColor = texture2D(u_TexCoord, animatedTexture);

    // Ambient lighting
    vec3 ambient = ambientStrength * lightColor;

    // Diffuse lighting
    vec3 norm = normalize(v_Normal);
    vec3 lightDir = normalize(u_LightPosition - v_FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    // Specular lighting
    vec3 viewDir = normalize(u_CameraPosition - v_FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), specularShininess);
    vec3 specular = specularStrength * spec * lightColor;

    // Combining ambient + diffuse + specular lighting
    vec3 result = (ambient + diffuse + specular) * texColor.rgb;

    // Apply fog
    float distanceToCamera = length(u_CameraPosition - v_FragPos);
    float fogFactor = clamp((fogEnd - distanceToCamera) / (fogEnd - fogStart), 0.0, 1.0);

    vec3 finalColor = mix(fogColor, result, fogFactor);

    // Apply transparency
    float alpha = texColor.a * 0.5;
    float finalAlpha = mix(texColor.a, 0.0, fogFactor);

    gl_FragColor = vec4(finalColor, finalAlpha);
}