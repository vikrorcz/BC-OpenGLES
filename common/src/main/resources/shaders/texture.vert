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

void main()
{
    gl_Position = u_Matrix * a_Position;
    v_FragPos = vec3(u_ModelMatrix * a_Position);
    v_Normal = normalize(mat3(u_ModelMatrix) * a_Normal);

    v_TexCoord = a_TexCoord;
}