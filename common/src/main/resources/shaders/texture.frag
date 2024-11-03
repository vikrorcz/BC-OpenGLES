#version 100

precision highp float;

varying vec2 v_TexCoord;

uniform sampler2D u_TexCoord;
uniform vec4 u_Color;

void main() {
    vec4 texColor = texture2D(u_TexCoord, v_TexCoord) + vec4(u_Color.rgb, 0.0);

    if (texColor.a < 0.45) {
        discard;
    }

    gl_FragColor = vec4(texColor.rgb, texColor.a);
}
