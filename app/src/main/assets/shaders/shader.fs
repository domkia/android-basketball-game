#version 300 es

in mediump vec2 uv;

uniform sampler2D tex;

out vec4 fragColor;

void main()
{
    fragColor = texture2D(tex, uv);
}