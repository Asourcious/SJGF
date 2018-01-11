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
import org.barronpm.sjgf.backend.Engine;
import org.barronpm.sjgf.draw.Camera;
import org.barronpm.sjgf.draw.Color;
import org.barronpm.sjgf.draw.Graphics;
import org.barronpm.sjgf.draw.Texture;

import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class GlGraphics implements Graphics, Disposable {

    private Color color = Color.BLACK;
    private Camera camera;

    private final PixelBasedCamera defaultCamera;

    private GlShaderProgram shaderProgram;

    private GlShapeBatch lineBatch;
    private GlShapeBatch triangleBatch;

    private int vao;
    private int vertexVbo;
    private int colorVbo;

    public GlGraphics() {
        defaultCamera = new PixelBasedCamera(Engine.instance.getWidth(), Engine.instance.getHeight());
        glfwSetWindowSizeCallback(Engine.window, defaultCamera);
        camera = defaultCamera;

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
        triangleBatch = new GlShapeBatch(vao, vertexVbo, colorVbo, 3);
        lineBatch = new GlShapeBatch(vao, vertexVbo, colorVbo, 2);
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
        return camera == defaultCamera ? null :camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera == null ? defaultCamera : camera;
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
        double inc = (Math.PI * 2) / segments;
        for (double theta = 0; theta < Math.PI * 2; theta += inc) {
            float x1 = (float) (width * Math.cos(theta));
            float x2 = (float) (width * Math.cos(theta + inc));
            float y1 = (float) (height * Math.sin(theta));
            float y2 = (float) (height * Math.sin(theta + inc));

            lineBatch.add(color,
                    camera.project(x + x1, y + y1, 0),
                    camera.project(x + x2, y + y2, 0));
        }
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        lineBatch.add(color,
                camera.project(x1, y1, 0),
                camera.project(x2, y2, 0));
    }

    @Override
    public void drawString(String string, float x, float y) {

    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        lineBatch.add(color,
                camera.project(x, y, 0),
                camera.project(x, y + height, 0));
        lineBatch.add(color,
                camera.project(x, y + height, 0),
                camera.project(x + width, y + height, 0));
        lineBatch.add(color,
                camera.project(x + width, y + height, 0),
                camera.project(x + width, y, 0));
        lineBatch.add(color,
                camera.project(x + width, y, 0),
                camera.project(x, y, 0));

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
        double inc = (Math.PI * 2) / segments;
        for (double theta = 0; theta < Math.PI * 2; theta += inc) {
            float x1 = (float) (width * Math.cos(theta));
            float x2 = (float) (width * Math.cos(theta + inc));
            float y1 = (float) (height * Math.sin(theta));
            float y2 = (float) (height * Math.sin(theta + inc));

            triangleBatch.add(color,
                    camera.project(x, y, 0),
                    camera.project(x + x1, y + y1, 0),
                    camera.project(x + x2, y + y2, 0));
        }
    }

    @Override
    public void fillRect(float x, float y, float width, float height) {
        triangleBatch.add(color,
                camera.project(x, y, 0),
                camera.project(x, y + height, 0),
                camera.project(x + width, y, 0)
        );
        triangleBatch.add(color,
                camera.project(x + width, y + height, 0),
                camera.project(x + width, y, 0),
                camera.project(x, y + height, 0)
        );
    }

    public void draw() {
        triangleBatch.flush();
        lineBatch.flush();
    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
        glDeleteBuffers(vertexVbo);
        glDeleteBuffers(colorVbo);
        glDeleteVertexArrays(vao);
    }
}