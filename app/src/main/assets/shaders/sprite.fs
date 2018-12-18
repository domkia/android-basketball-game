#version 300 es

in mediump vec2 uv;
uniform sampler2D tex;
out highp vec4 fragColor;

void main()
{
    highp vec4 color = texture(tex, uv.xy);
    if(color.a < 0.75)
        discard;
    fragColor = color;
}


