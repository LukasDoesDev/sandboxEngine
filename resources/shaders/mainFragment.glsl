#version 330 core

in vec3 passColor;
in vec2 passTextureCoord;

out vec4 outColor;

uniform sampler2D tex;

void main(void) {
    outColor = texture(tex, passTextureCoord);
}