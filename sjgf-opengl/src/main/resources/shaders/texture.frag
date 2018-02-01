#version 330 core

uniform sampler2D tex;

in vec2 texCoord;
in vec4 texColor;

out vec4 fragColor;

void main()
{
    fragColor = texture(tex, texCoord) * texColor;
}