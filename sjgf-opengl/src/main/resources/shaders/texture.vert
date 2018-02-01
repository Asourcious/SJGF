#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec4 color;

out vec2 texCoord;
out vec4 texColor;

void main()
{
    gl_Position = vec4(pos.x, pos.y, pos.z, 1.0);
    texCoord = texPos;
    texColor = color;
}