#version 330 core

in vec3 passColor;

out vec4 outColor;

void main(void) {
    /*loat r = sin(gl_FragCoord.x / 100.0) + cos(gl_FragCoord.y / 100.0);
    float g = sin(gl_FragCoord.x / 10.0) * sin(gl_FragCoord.y / 500.0);
    float b = sin(gl_FragCoord.x / 10.0) + sin(gl_FragCoord.y / 100.0);
    outColor = vec4(r, g, b, 1.0);*/
    outColor = vec4(passColor, 1.0);

}