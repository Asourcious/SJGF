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

import org.barronpm.sjgf.Disposable;
import org.barronpm.sjgf.draw.Camera;
import org.barronpm.sjgf.draw.Color;
import org.barronpm.sjgf.draw.Graphics;
import org.barronpm.sjgf.draw.Texture;
import org.barronpm.sjgf.math.Vector3;
import org.barronpm.sjgf.opengl.GlGameWindow;
import org.barronpm.sjgf.opengl.util.FileUtils;
import org.barronpm.sjgf.util.Args;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public final class GlGraphics implements Graphics, Disposable {

    private Color color = Color.BLACK;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    private Camera camera;

    private final PixelBasedCamera defaultCamera;

    private GlShaderProgram shapeProgram;
    private GlShaderProgram textureProgram;

    private GlShapeBatch lineBatch;
    private GlShapeBatch triangleBatch;
    private GlTextureBatch textureBatch;

    private int vao;
    private int vertexVbo;
    private int colorVbo;

    private final Map<Font, GlFont> fontMap = new HashMap<>();

    public GlGraphics(GlGameWindow window) {

        defaultCamera = new PixelBasedCamera(window.getWidth(), window.getHeight());
        glfwSetWindowSizeCallback(window.getHandle(), defaultCamera);
        camera = defaultCamera;

        GlShader vertex = new GlShader(GL_VERTEX_SHADER,
                FileUtils.getResourceContents("/shaders/default.vert"));
        GlShader fragment = new GlShader(GL_FRAGMENT_SHADER,
                FileUtils.getResourceContents("/shaders/default.frag"));

        shapeProgram = new GlShaderProgram(vertex, fragment);

        vertex = new GlShader(GL_VERTEX_SHADER,
                FileUtils.getResourceContents("/shaders/texture.vert"));
        fragment = new GlShader(GL_FRAGMENT_SHADER,
                FileUtils.getResourceContents("/shaders/texture.frag"));

        textureProgram = new GlShaderProgram(vertex, fragment);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vertexVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        colorVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);
        triangleBatch = new GlShapeBatch(shapeProgram, vao, vertexVbo, colorVbo, 3);
        lineBatch = new GlShapeBatch(shapeProgram, vao, vertexVbo, colorVbo, 2);
        textureBatch = new GlTextureBatch(textureProgram);
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

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
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
        double inc = (Math.PI * 2) / segments;
        float cx = x + (width / 2);
        float cy = y + (height / 2);

        for (double theta = start; theta < end; theta += inc) {
            float x1 = (float) (width * Math.cos(theta));
            float x2 = (float) (width * Math.cos(theta + inc));
            float y1 = (float) (height * Math.sin(theta));
            float y2 = (float) (height * Math.sin(theta + inc));

            lineBatch.add(color,
                    camera.project(cx + x1, cy + y1, 0),
                    camera.project(cx + x2, cy + y2, 0));
        }
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
        if (!fontMap.containsKey(font))
            fontMap.put(font, new GlFont(font));

        GlFont font = fontMap.get(this.font);

        int textHeight = font.getHeight(string);

        float drawX = x;
        float drawY = y;
        if (textHeight > font.height) {
            drawY += textHeight - font.height;
        }

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (ch == '\n') {
                drawY -= font.height;
                drawX = x;
                continue;
            }

            if (ch == '\r') {
                continue;
            }

            Glyph g = font.glyphs.get(ch);

            Vector3 v0 = camera.project(drawX, drawY, 0);
            Vector3 v1 = camera.project(drawX, drawY + g.height, 0);
            Vector3 v2 = camera.project(drawX + g.width, drawY, 0);
            Vector3 v3 = camera.project(drawX + g.width, drawY + g.height, 0);

            float s1 = (float) g.x / font.atlas.getWidth();
            float t1 = (float) g.y / font.atlas.getHeight();
            float s2 = (float) (g.x + g.width) / font.atlas.getWidth();
            float t2 = (float) (g.y + g.height) / font.atlas.getHeight();

            textureBatch.addRegion(font.atlas, color, s1, t1, s2, t2, v0, v1, v2, v2, v3, v1);
            drawX += g.width;
        }
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
        Vector3 v0 = camera.project(x, y, 0);
        Vector3 v1 = camera.project(x, y + texture.getHeight(), 0);
        Vector3 v2 = camera.project(x + texture.getWidth(), y, 0);
        Vector3 v3 = camera.project(x+ texture.getWidth(), y + texture.getHeight(), 0);

        textureBatch.add(texture, Color.WHITE,
                v0, v1, v2,
                v2, v3, v1);
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
        textureBatch.flush();
    }

    @Override
    public void dispose() {
        shapeProgram.dispose();
        textureProgram.dispose();
        glDeleteBuffers(vertexVbo);
        glDeleteBuffers(colorVbo);
        glDeleteVertexArrays(vao);
    }
}