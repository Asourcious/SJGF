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
import org.barronpm.sjgf.math.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private final List<Vector3> vertexList = new ArrayList<>();
    private final List<Color> colorList = new ArrayList<>();

    private int numberOfShapes = 0;

    public GlRectangleBatch(int vao, int vertexVbo, int colorVbo) {
        this.vao = vao;
        this.vertexVbo = vertexVbo;
        this.colorVbo = colorVbo;
    }

    public void add(Color color, Vector3... vertices) {
        Collections.addAll(vertexList, vertices);
        colorList.add(color);
    }

    public void flush() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(vertexList.size() * 3);
        vertexList.forEach(vec -> {
            vertices.put(vec.getX());
            vertices.put(vec.getY());
            vertices.put(vec.getZ());
        });

        FloatBuffer colors = BufferUtils.createFloatBuffer(colorList.size() * 6 * 4);
        colorList.forEach(col -> {
            for (int i = 0; i < 6; i++) {
                colors.put(col.getRed());
                colors.put(col.getGreen());
                colors.put(col.getBlue());
                colors.put(col.getAlpha());
            }
        });

        vertices.flip();
        colors.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(GL_TRIANGLES, 0, vertexList.size());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        vertexList.clear();
        colorList.clear();
    }
}
