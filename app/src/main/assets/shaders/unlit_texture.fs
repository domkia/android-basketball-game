#version 300 es

uniform sampler2D tex;

in mediump vec2 uvs;

out highp vec4 fragColor;

void main()
{
    fragColor = texture(tex, uvs);
}