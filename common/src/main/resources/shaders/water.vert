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
uniform float u_Left;
uniform float u_Top;
uniform float u_Width;
uniform float u_Height;
uniform float u_Time;
uniform float u_WaveHeight;

// Parameters for wave effect
//const float u_WaveHeight = 5.0;  // Height of the waves
const float u_WaveFrequency = 0.002;  // Frequency of the waves
const float u_WaveSpeed = 0.6;  // Speed of the waves

void main()
{
    // Global coordinates for wave calculation
    vec4 globalPosition = u_ModelMatrix * a_Position;
    float globalX = globalPosition.x;
    float globalZ = globalPosition.z;

    // Calculate multiple wave effects with different frequencies and phases
    float wave1 = sin(globalX * u_WaveFrequency + u_Time * u_WaveSpeed);
    float wave2 = cos(globalZ * u_WaveFrequency * 0.7 + u_Time * u_WaveSpeed * 0.8);
    float wave3 = sin((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave4 = sin(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave5 = cos((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);

    // Blend the waves smoothly
    float wave = (wave1 + wave2 + wave3 + wave4 + wave5) / 5.0 * u_WaveHeight;

    // Calculate partial derivatives for normal calculation
    float wave1_dx = u_WaveFrequency * cos(globalX * u_WaveFrequency + u_Time * u_WaveSpeed);
    float wave2_dz = -u_WaveFrequency * 0.7 * sin(globalZ * u_WaveFrequency * 0.7 + u_Time * u_WaveSpeed * 0.8);
    float wave3_dx = u_WaveFrequency * 0.5 * cos((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave3_dz = u_WaveFrequency * 0.5 * cos((globalX + globalZ) * u_WaveFrequency * 0.5 + u_Time * u_WaveSpeed * 1.2);
    float wave4_dx = u_WaveFrequency * 0.3 * cos(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave4_dz = u_WaveFrequency * 0.4 * cos(globalX * u_WaveFrequency * 0.3 + globalZ * u_WaveFrequency * 0.4 + u_Time * u_WaveSpeed * 1.5);
    float wave5_dx = -u_WaveFrequency * 0.6 * sin((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);
    float wave5_dz = u_WaveFrequency * 0.6 * sin((globalX - globalZ) * u_WaveFrequency * 0.6 + u_Time * u_WaveSpeed * 0.9);

    float wave_dx = (wave1_dx + wave3_dx + wave4_dx + wave5_dx) / 5.0 * u_WaveHeight;
    float wave_dz = (wave2_dz + wave3_dz + wave4_dz + wave5_dz) / 5.0 * u_WaveHeight;

    // Normal calculation considering the wave effect
    vec3 normal = normalize(vec3(-wave_dx, 1.0, -wave_dz));

    // Modify the vertex position with the wave effect
    vec4 modifiedPosition = a_Position;
    modifiedPosition.y += wave;

    gl_Position = u_Matrix * modifiedPosition;
    v_FragPos = vec3(u_ModelMatrix * modifiedPosition);
    v_Normal = normalize(mat3(u_ModelMatrix) * normal);

    v_TexCoord = a_TexCoord * vec2(u_Width, u_Height) + vec2(u_Left, u_Top);
}
