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

import org.barronpm.sjgf.draw.Color;
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

class GlTextureBatch {

    private static final int MAX_TEXTURES = 30;

    private final GlShaderProgram program;

    private final int vao;
    private final int vertexVbo;
    private final int uvVbo;
    private final int colorVbo;

    private final float[] vertexArray;
    private final float[] coordinateArray;
    private final float[] colorArray;
    private final ArrayList<Texture> textures;

    private int numTextures = 0;

    GlTextureBatch(GlShaderProgram program) {
        this.program = program;
        this.vertexArray = new float[MAX_TEXTURES * 6 * 3];
        this.coordinateArray = new float[MAX_TEXTURES * 6 * 2];
        this.colorArray = new float[MAX_TEXTURES * 6 * 4];
        this.textures = new ArrayList<>(MAX_TEXTURES);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vertexVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        uvVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, uvVbo);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0);
    }

    void add(Texture texture, Color color, Vector3... vertices) {
        addRegion(texture, color, 0, 0, 1, 1, vertices);
    }

    void addRegion(Texture texture, Color color, float x1, float y1, float x2, float y2, Vector3... vertices) {
        textures.add(texture);

        for (int i = 0; i < 6; i++) {
            vertexArray[numTextures * 6 * 3 + i * 3] = vertices[i].getX();
            vertexArray[numTextures * 6 * 3 + i * 3 + 1] = vertices[i].getY();
            vertexArray[numTextures * 6 * 3 + i * 3 + 2] = vertices[i].getZ();

            colorArray[numTextures * 6 * 4 + i * 4] = color.getRed();
            colorArray[numTextures * 6 * 4 + i * 4 + 1] = color.getGreen();
            colorArray[numTextures * 6 * 4 + i * 4 + 2] = color.getBlue();
            colorArray[numTextures * 6 * 4 + i * 4 + 3] = color.getAlpha();
        }

        System.arraycopy(new float[]{ x1, y1, x1, y2, x2, y1, x2, y1, x2, y2, x1, y2 },
                0, coordinateArray, numTextures * 6 * 2, 12);

        numTextures++;
        if (numTextures == MAX_TEXTURES)
            flush();
    }

    void flush() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(numTextures * 6 * 3);
        FloatBuffer coords = BufferUtils.createFloatBuffer(numTextures * 6 * 2);
        FloatBuffer colors = BufferUtils.createFloatBuffer(numTextures * 6 * 4);

        float[] tmp = new float[numTextures * 6 * 3];
        System.arraycopy(vertexArray, 0, tmp, 0, numTextures * 6 * 3);
        vertices.put(tmp);
        tmp = new float[numTextures * 6 * 2];
        System.arraycopy(coordinateArray, 0, tmp, 0, numTextures * 6 * 2);
        coords.put(tmp);
        tmp = new float[numTextures * 6 * 4];
        System.arraycopy(colorArray, 0, tmp, 0, numTextures * 6 * 4);
        colors.put(tmp);

        vertices.flip();
        coords.flip();
        colors.flip();

        glUseProgram(program.getHandle());
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, uvVbo);
        glBufferData(GL_ARRAY_BUFFER, coords, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        for (int i = 0; i < textures.size(); i++) {
            ((GlTexture) textures.get(i)).bind();
            glUniform1i(glGetUniformLocation(program.getHandle(), "tex"), 0);
            glDrawArrays(GL_TRIANGLES, 6 * i, 6);
        }

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);

        numTextures = 0;
        textures.clear();
    }
}
