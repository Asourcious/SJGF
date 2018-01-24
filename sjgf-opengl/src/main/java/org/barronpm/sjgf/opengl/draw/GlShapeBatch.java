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
import org.barronpm.sjgf.math.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GlShapeBatch {

    private static final int MAX_SHAPES = 30;

    private final int vao;
    private final int vertexVbo;
    private final int colorVbo;

    private final float[] vertexArray;
    private final float[] colorArray;

    private final GlShaderProgram shaderProgram;

    private int numShapes = 0;
    private final int numVertices;

    public GlShapeBatch(GlShaderProgram shaderProgram, int vao, int vertexVbo, int colorVbo, int numVertices) {
        this.shaderProgram = shaderProgram;
        this.vao = vao;
        this.vertexVbo = vertexVbo;
        this.colorVbo = colorVbo;
        this.numVertices = numVertices;
        this.vertexArray = new float[MAX_SHAPES * numVertices * 3];
        this.colorArray = new float[MAX_SHAPES * numVertices * 4];
    }

    public void add(Color color, Vector3... vertices) {
        for (int i = 0; i < numVertices; i++) {
            vertexArray[numShapes * numVertices * 3 + i * 3] = vertices[i].getX();
            vertexArray[numShapes * numVertices * 3 + i * 3 + 1] = vertices[i].getY();
            vertexArray[numShapes * numVertices * 3 + i * 3 + 2] = vertices[i].getZ();

            colorArray[numShapes * numVertices * 4 + i * 4] = color.getRed();
            colorArray[numShapes * numVertices * 4 + i * 4 + 1] = color.getGreen();
            colorArray[numShapes * numVertices * 4 + i * 4 + 2] = color.getBlue();
            colorArray[numShapes * numVertices * 4 + i * 4 + 3] = color.getAlpha();
        }
        numShapes++;
        if (numShapes == MAX_SHAPES)
            flush();
    }

    public void flush() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(numShapes * numVertices * 3);
        FloatBuffer colors = BufferUtils.createFloatBuffer(numShapes * numVertices * 4);

        float[] tmp = new float[numShapes * numVertices * 3];
        System.arraycopy(vertexArray, 0, tmp, 0, numShapes * numVertices * 3);
        vertices.put(tmp);
        tmp = new float[numShapes * numVertices * 4];
        System.arraycopy(colorArray, 0, tmp, 0, numShapes * numVertices * 4);
        colors.put(tmp);

        vertices.flip();
        colors.flip();

        glUseProgram(shaderProgram.getHandle());
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        switch (numVertices) {
            case 1:
                glDrawArrays(GL_POINT, 0, numShapes * numVertices);
                break;
            case 2:
                glDrawArrays(GL_LINES, 0, numShapes * numVertices);
                break;
            case 3:
                glDrawArrays(GL_TRIANGLES, 0, numShapes * numVertices);
                break;
            default:
                break;
        }

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        numShapes = 0;
    }
}