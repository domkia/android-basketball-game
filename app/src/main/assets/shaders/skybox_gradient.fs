#version 300 es

in mediump vec3 viewDirection;

out highp vec4 fragColor;

void main()
{
    mediump float viewDot = dot(vec3(0.0, 1.0, 0.0), normalize(viewDirection));
    viewDot = viewDot * 0.5 + 0.5;
    fragColor = mix(vec4(1.0, 0.0, 0.0, 1.0), vec4(0.0, 0.0, 1.0, 1.0), viewDot);
}