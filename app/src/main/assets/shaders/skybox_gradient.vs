#version 300 es

layout(location = 0) in vec2 position;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

out vec3 viewDirection;

void main()
{
    mediump vec2 temp = position * 2.0;
    vec4 projected = projMatrix * viewMatrix * vec4(position.x, position.y, 0.0, 1.0);
    gl_Position = vec4(temp.x, temp.y, 0.0, 1.0);

    viewDirection = projected.xyz;
}