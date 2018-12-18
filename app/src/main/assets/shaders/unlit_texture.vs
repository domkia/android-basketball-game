#version 300 es

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 uv;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projMatrix;

out mediump vec2 uvs;

void main()
{
    uvs = vec2(uv.x, 1.0 - uv.y);
    gl_Position = projMatrix * viewMatrix * modelMatrix * vec4(position.xyz, 1.0);
}