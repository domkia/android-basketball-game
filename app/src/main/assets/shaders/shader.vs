#version 300 es

layout(location = 0) in vec2 position;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projMatrix;

out vec2 uv;

void main()
{
    uv = vec2(1.0) - position.xy;
    gl_Position = projMatrix * viewMatrix * modelMatrix * vec4(position.x, position.y, 0.0, 1.0);
}