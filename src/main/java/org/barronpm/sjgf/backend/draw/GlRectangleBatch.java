/*
 *     Copyright 2017 Patrick Barron
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

package org.barronpm.sjgf.backend.draw;

import org.barronpm.sjgf.draw.Color;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GlRectangleBatch {

    private static final int BATCH_SIZE = 10;

    private final int vao;
    private final int vertexVbo;
    private final int colorVbo;

    private final FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(BATCH_SIZE * 2 * 3 * 3);
    private final FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(BATCH_SIZE * 2 * 3 * 4);

    private int numberOfShapes = 0;

    public GlRectangleBatch(int vao, int vertexVbo, int colorVbo) {
        this.vao = vao;
        this.vertexVbo = vertexVbo;
        this.colorVbo = colorVbo;
    }

    public void add(Color color, float... vertices) {
        vertexBuffer.put(vertices);

        // Add a color for each vertex, 3 per triangle, 2 triangles per rectangle
        // TODO: Look into making this more efficient in regards to memory usage
        for (int i = 0; i < 6; i++) {
            colorBuffer.put(color.getRed());
            colorBuffer.put(color.getGreen());
            colorBuffer.put(color.getBlue());
            colorBuffer.put(color.getAlpha());
        }

        numberOfShapes++;
        if (numberOfShapes == BATCH_SIZE)
            flush();
    }

    public void flush() {
        vertexBuffer.flip();
        colorBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(GL_TRIANGLES, 0, numberOfShapes * 2 * 3);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        vertexBuffer.flip();
        colorBuffer.flip();
        vertexBuffer.clear();
        colorBuffer.clear();
    }
}
