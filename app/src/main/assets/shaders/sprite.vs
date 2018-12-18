#version 300 es

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 uvs;

uniform mat4 viewMatrix;
uniform mat4 projMatrix;

uniform mat4 modelMatrix[32];
uniform vec4 uvOffset[32];

out vec2 uv;

void main()
{
    uv = uvOffset[gl_InstanceID].xy + vec2(uvs.x * uvOffset[gl_InstanceID].z, uvs.y * uvOffset[gl_InstanceID].w);

    gl_Position = projMatrix * viewMatrix
                    * modelMatrix[gl_InstanceID] * vec4(position.x, position.y, 0, 1);
}


