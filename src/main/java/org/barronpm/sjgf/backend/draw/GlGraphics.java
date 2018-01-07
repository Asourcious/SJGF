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

import org.barronpm.sjgf.Disposable;
import org.barronpm.sjgf.backend.Args;
import org.barronpm.sjgf.draw.Camera;
import org.barronpm.sjgf.draw.Color;
import org.barronpm.sjgf.draw.Graphics;
import org.barronpm.sjgf.draw.Texture;
import org.barronpm.sjgf.math.Vector3;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class GlGraphics implements Graphics, Disposable {

    private Color color = Color.BLACK;
    private Camera camera = null;

    private final Camera defaultCamera = null;

    private GlShaderProgram shaderProgram;

    private GlRectangleBatch rectangleBatch;

    private int vao;
    private int vertexVbo;
    private int colorVbo;

    public GlGraphics() {
        shaderProgram = new GlShaderProgram();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vertexVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);
        rectangleBatch = new GlRectangleBatch(vao, vertexVbo, colorVbo);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        Args.notNull(color, "color");
        this.color = color;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void drawArc(float x, float y, float width, float height, float start, float end) {
        drawArc(x, y, width, height, start, end, 16);
    }

    @Override
    public void drawArc(float x, float y, float width, float height, float start, float end, int segments) {

    }

    @Override
    public void drawEllipse(float x, float y, float width, float height) {
        drawEllipse(x, y, width, height, 32);
    }

    @Override
    public void drawEllipse(float x, float y, float width, float height, int segments) {

    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {

    }

    @Override
    public void drawString(String string, float x, float y) {

    }

    @Override
    public void drawRect(float x, float y, float width, float height) {

    }

    @Override
    public void drawTexture(Texture texture, float x, float y) {

    }

    @Override
    public void fillEllipse(float x, float y, float width, float height) {
        fillEllipse(x, y, width, height, 32);
    }

    @Override
    public void fillEllipse(float x, float y, float width, float height, int segments) {

    }

    @Override
    public void fillRect(float x, float y, float width, float height) {
        rectangleBatch.add(color,
                new Vector3(x, y, 0),
                new Vector3(x, y + height, 0),
                new Vector3(x + width, y, 0),
                new Vector3(x + width, y + height, 0),
                new Vector3(x + width, y, 0),
                new Vector3(x, y + height, 0));
    }

    public void draw() {
        rectangleBatch.flush();
    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
        glDeleteBuffers(vertexVbo);
        glDeleteBuffers(colorVbo);
        glDeleteVertexArrays(vao);
    }
}