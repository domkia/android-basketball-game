#version 300 es

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 uvs;

uniform mat4 viewMatrix;
uniform mat4 projMatrix;

uniform mat4 modelMatrix[32];
uniform vec2 uvOffset[32];
uniform float spriteWidth;

out vec2 uv;

void main()
{
    uv = uvOffset[gl_InstanceID] + vec2(uvs.x * spriteWidth, uvs.y * spriteWidth);

    gl_Position = projMatrix * viewMatrix
                    * modelMatrix[gl_InstanceID] * vec4(position.x, position.y, 0, 1);
}


