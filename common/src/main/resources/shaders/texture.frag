#version 100

precision highp float;

varying vec2 v_TexCoord;
varying vec3 v_Normal;
varying vec3 v_FragPos;

uniform vec3 u_LightPosition;
uniform sampler2D u_TexCoord;
uniform vec4 u_Color;
uniform vec3 u_CameraPosition;

const vec3 lightColor = vec3(1.0, 1.0, 1.0); // White light
const float ambientStrength = 0.3;
const float ambientSkyStrength = 0.9;
const float specularStrength = 0.5;
const float specularShininess = 4.0;

void main() {
    vec4 texColor = texture2D(u_TexCoord, v_TexCoord) + vec4(u_Color.rgb, 0.0);

    if (texColor.a < 0.45) {
        discard;
    }

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

    gl_FragColor = vec4(result, texColor.a);
}