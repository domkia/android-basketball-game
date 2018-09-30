#version 300 es

layout(location = 0) in vec3 position;

uniform mat4 viewMatrix;
uniform mat4 projMatrix;

void main()
{
    gl_Position = projMatrix * viewMatrix * vec4(position.x, position.y, position.z, 1.0);
}