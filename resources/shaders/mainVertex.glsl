#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 texureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

uniform float scale;

void main() {
    gl_Position = vec4(position, 1.0) * vec4(scale, scale, scale, 1);
    passColor = color;
    passTextureCoord = texureCoord;
}




/*#version 460 core

in vec3 position;
in vec3 color;
in vec2 texureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

uniform float scale;

void main() {
    gl_Position = vec4(position, 1.0) * vec4(scale, scale, scale, 1);
    passColor = color;
    passTextureCoord = texureCoord;
}*/