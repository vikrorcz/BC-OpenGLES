#version 100

precision highp float;

attribute vec4 a_Position;
attribute vec2 a_TexCoord;
attribute vec3 a_Normal;

varying vec2 v_TexCoord;
varying vec3 v_Normal;
varying vec3 v_FragPos;

uniform mat4 u_Matrix;
uniform mat4 u_ModelMatrix;
uniform float u_Time;
uniform float u_WaveHeight;

// Parameters for wave effect
const float u_WaveFrequency = 0.002;
const float u_WaveSpeed = 0.6;

void main()
{
    // Global coordinates for wave calculation
    vec4 globalPosition = u_ModelMatrix * a_Position;
    float globalX = globalPosition.x;
    float globalZ = globalPosition.z;

    float wave1 = sin(globalX * u_WaveFrequency + u_Time * u_WaveSpeed);
    float wave2 = cos(globalZ * u_WaveFrequency * 0.7 + u_Time * u_WaveSpeed * 0.8);
    float wave3 = sin((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave4 = sin(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave5 = cos((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);

    float wave = (wave1 + wave2 + wave3 + wave4 + wave5) / 5.0 * u_WaveHeight;

    // Rebuild the normal when the vertex changes using derivation of previous wave
    float wave1DeltaX = u_WaveFrequency * cos(globalX * u_WaveFrequency + u_Time * u_WaveSpeed);
    float wave2DeltaZ = -u_WaveFrequency * 0.7 * sin(globalZ * u_WaveFrequency * 0.7 + u_Time * u_WaveSpeed * 0.8);
    float wave3DeltaX = u_WaveFrequency * 0.5 * cos((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave3DeltaZ = u_WaveFrequency * 0.5 * cos((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave4DeltaX = u_WaveFrequency * 0.3 * cos(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave4DeltaZ = u_WaveFrequency * 0.4 * cos(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave5DeltaX = -u_WaveFrequency * 0.6 * sin((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);
    float wave5DeltaZ = u_WaveFrequency * 0.6 * sin((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);

    float combinedWaveDeltaX = (wave1DeltaX + wave3DeltaX + wave4DeltaX + wave5DeltaX) / 5.0 * u_WaveHeight;
    float combinedWaveDeltaZ = (wave2DeltaZ + wave3DeltaZ + wave4DeltaZ + wave5DeltaZ) / 5.0 * u_WaveHeight;

    vec3 normal = normalize(vec3(-combinedWaveDeltaX, 1.0, -combinedWaveDeltaZ));

    vec4 modifiedPosition = a_Position;
    modifiedPosition.y += wave;

    gl_Position = u_Matrix * modifiedPosition;
    v_FragPos = vec3(u_ModelMatrix * modifiedPosition);
    v_Normal = normalize(mat3(u_ModelMatrix) * normal);

    v_TexCoord = a_TexCoord;
}
