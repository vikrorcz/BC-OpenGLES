#version 100

precision highp float;

uniform vec4 u_Color;

void main()
{
    gl_FragColor = vec4(u_Color.rgb, u_Color.a);
}

