#version 100

precision highp float;

varying vec2 v_TexCoord;
varying vec3 v_Normal;
varying vec3 v_FragPos; // Fragment position in world space

uniform vec3 u_LightPosition;
uniform sampler2D u_TexCoord;
uniform vec4 u_Color;
uniform vec3 u_CameraPosition;
uniform float u_Time;

// Fog constants
const vec3 fogColor = vec3(130.9 / 255.0, 126.0 / 255.0, 115.2 / 255.0); // Gray fog color
const float fogStart = 25000.0;  // Fog starts at distance 20 units from the camera
const float fogEnd = 35000.0;   // Fog fully covers the scene at distance 100 units

const vec3 lightColor = vec3(1.0, 1.0, 1.0); // White light
const float ambientStrength = 0.7;
const float specularStrength = 0.7;
const float specularShininess = 2.0;

// Periodic (tileable) random noise
float rand(vec2 co) {
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453);
}

// Periodic (tileable) smooth noise
float smoothNoise(vec2 uv) {
    vec2 i = floor(uv);
    vec2 f = fract(uv);
    f = f * f * (3.0 - 2.0 * f);

    float a = rand(i);
    float b = rand(i + vec2(1.0, 0.0));
    float c = rand(i + vec2(0.0, 1.0));
    float d = rand(i + vec2(1.0, 1.0));

    return mix(a, b, f.x) + (c - a) * f.y * (1.0 - f.x) + (d - b) * f.x * f.y;
}

// Periodic (tileable) Fractal Brownian Motion function
float fbm(vec2 uv) {
    float f = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 5; i++) {
        f += amp * smoothNoise(uv);
        uv = mod(uv * 2.0, 10.0); // Ensure the coordinates tile
        amp *= 0.5;
    }
    return f;
}

void main() {
    // Use the fragment's global position for seamless texturing
    vec2 uv = v_FragPos.xz * 0.001; // Scale the world space coordinates for texturing

    // Animate the texture coordinates to simulate water waves
    uv.x -= u_Time * 0.1; // Slow vertical movement for waves
    float n = fbm(uv * 5.0); // Generate fractal noise for wave effect

    // Modify the texture coordinates with noise
    vec2 animatedTexCoords = uv + vec2(n * 0.05, n * 0.01) * 0.5;
    animatedTexCoords.x += u_Time * 0.012;

    // Sample the texture using the animated coordinates
    vec4 texColor = texture2D(u_TexCoord, animatedTexCoords);


    // Ambient lighting
    vec3 ambient = ambientStrength * lightColor;

    // Diffuse lighting
    vec3 norm = normalize(v_Normal);
    vec3 lightDir = normalize(u_LightPosition - v_FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    // Phong
    vec3 viewDir = normalize(u_CameraPosition - v_FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), specularShininess);
    vec3 specular = specularStrength * spec * lightColor;

    // Combine lighting components
    vec3 result = (ambient + diffuse + specular) * texColor.rgb;

    // Fog calculation using constants
    float distanceToCamera = length(u_CameraPosition - v_FragPos);
    float fogFactor = clamp((fogEnd - distanceToCamera) / (fogEnd - fogStart), 0.0, 1.0);

    // Final color with fog applied (blend between the scene color and fog color)
    vec3 finalColor = mix(fogColor, result, fogFactor);

    // Apply transparency
    float alpha = texColor.a * 0.5;
    float finalAlpha = mix(texColor.a, 0.0, fogFactor);
    // Set the fragment color
    gl_FragColor = vec4(finalColor, finalAlpha);
}