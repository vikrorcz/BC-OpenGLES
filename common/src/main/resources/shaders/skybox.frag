#version 100

precision highp float;

varying vec2 v_TexCoord;
varying vec3 v_Normal;
varying vec3 v_FragPos;

uniform float u_ScreenWidth;
uniform float u_ScreenHeight;
uniform vec3 u_LightPosition;
uniform sampler2D u_TexCoord;
uniform vec4 u_Color;
uniform vec3 u_CameraPosition;
uniform mat4 u_Matrix;

const vec3 sunColor = vec3(255.0 / 255.0, 228.0 / 255.0, 132.0 / 255.0);
const vec3 lightColor = vec3(1.0, 1.0, 1.0); // White light
const float ambientStrength = 0.3; // night: 0.1 | day: 0.3
const float ambientSkyStrength = 0.9; // night: 0.3 | day: 1.0
const float sunSize = 0.3;

void main() {
    vec4 texColor = texture2D(u_TexCoord, v_TexCoord) + vec4(u_Color.rgb, 0.0);

    vec3 finalColor = texColor.rgb;

    // We still apply at least ambient lighting, so we can change skybox color
    vec3 ambient = ambientSkyStrength * lightColor;
    finalColor = ambient * texColor.rgb;
    vec4 lightPosScreen = u_Matrix * vec4(u_LightPosition, 1.0);
    lightPosScreen /= lightPosScreen.w;
    vec2 lightScreenPos = lightPosScreen.xy * 0.5 + 0.5;
    vec2 sunPosition = lightScreenPos;
    vec2 fragCoord = gl_FragCoord.xy / vec2(u_ScreenWidth, u_ScreenHeight);
    float sunDistance = length(fragCoord - sunPosition) / sunSize;
    float sunFalloff = 0.2 / (sunDistance * sunDistance + 0.01) * 0.2;

    vec3 lightDirection = normalize(u_LightPosition - u_CameraPosition);
    vec3 viewDirection = normalize(u_CameraPosition - v_FragPos);
    float dotProduct = dot(viewDirection, lightDirection);

    float visibility = smoothstep(0.0, 1.0, -dotProduct);
    finalColor += sunColor * sunFalloff * visibility;

    gl_FragColor = vec4(finalColor, texColor.a);
}