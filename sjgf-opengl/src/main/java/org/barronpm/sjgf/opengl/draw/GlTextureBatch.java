/*
 *     Copyright 2017-2018 Patrick Barron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.barronpm.sjgf.opengl.draw;

import org.barronpm.sjgf.draw.Texture;
import org.barronpm.sjgf.math.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GlTextureBatch {

    private static final int MAX_TEXTURES = 30;

    private final GlShaderProgram program;

    private final int vao;
    private final int vertexVbo;
    private final int uvVbo;

    private final float[] vertexArray;
    private final ArrayList<Texture> textures;

    private int numTextures = 0;

    public GlTextureBatch(GlShaderProgram program) {
        this.program = program;
        this.vertexArray = new float[MAX_TEXTURES * 6 * 3];
        this.textures = new ArrayList<>(MAX_TEXTURES);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vertexVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        uvVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, uvVbo);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
    }

    public void add(Texture texture, Vector3... vertices) {
        textures.add(texture);
        for (int i = 0; i < 6; i++) {
            vertexArray[numTextures * 6 * 3 + i * 3] = vertices[i].getX();
            vertexArray[numTextures * 6 * 3 + i * 3 + 1] = vertices[i].getY();
            vertexArray[numTextures * 6 * 3 + i * 3 + 2] = vertices[i].getZ();
        }
        numTextures++;
        if (numTextures == MAX_TEXTURES)
            flush();
    }

    public void flush() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(numTextures * 6 * 3);
        FloatBuffer coords = BufferUtils.createFloatBuffer(numTextures * 6 * 2);

        float[] tmp = new float[numTextures * 6 * 3];
        System.arraycopy(vertexArray, 0, tmp, 0, numTextures * 6 * 3);
        vertices.put(tmp);

        for (int i = 0; i < numTextures; i++) {
            coords.put(0);
            coords.put(0);
            coords.put(0);
            coords.put(1);
            coords.put(1);
            coords.put(0);
            coords.put(1);
            coords.put(0);
            coords.put(1);
            coords.put(1);
            coords.put(0);
            coords.put(1);
        }

        vertices.flip();
        coords.flip();

        glUseProgram(program.getHandle());
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, uvVbo);
        glBufferData(GL_ARRAY_BUFFER, coords, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        for (int i = 0; i < textures.size(); i++) {
            ((GlTexture) textures.get(i)).bind();
            glUniform1i(glGetUniformLocation(program.getHandle(), "tex"), 0);
            glDrawArrays(GL_TRIANGLES, 6 * i, 6);
        }

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        numTextures = 0;
        textures.clear();
    }
}
