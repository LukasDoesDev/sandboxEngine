#version 460 core

in vec3 position;
in vec3 color;
in vec2 texureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

void main() {
    gl_Position = vec4(position, 1.0);
    passColor = color;
    passTextureCoord = texureCoord;
}