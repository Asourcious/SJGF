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

package org.barronpm.sjgf.backend;

import org.barronpm.sjgf.Game;
import org.barronpm.sjgf.GameWindow;
import org.barronpm.sjgf.Monitor;
import org.barronpm.sjgf.WindowState;
import org.barronpm.sjgf.backend.draw.GlGraphics;
import org.barronpm.sjgf.draw.Color;
import org.barronpm.sjgf.exceptions.SJGFException;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class GlGameWindow implements GameWindow {

    private final Game game;
    private final long window;

    private String title;
    private Monitor monitor;
    private boolean useVsync;
    private WindowState state;

    private final IntBuffer tmp = BufferUtils.createIntBuffer(1);

    public GlGameWindow(Game game, long window, String title, Monitor monitor, boolean useVsync, WindowState state) {
        this.game = game;
        this.window = window;
        this.title = title;
        this.monitor = monitor;
        this.useVsync = useVsync;
        this.state = state;
    }

    @Override
    public void start() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GlGraphics graphics = new GlGraphics(this);

        double previous = glfwGetTime();
        while (!glfwWindowShouldClose(window)) {
            double current = glfwGetTime();
            double elapsed = current - previous;
            previous = current;

            glfwPollEvents();

            game.update(this, elapsed);

            glViewport(0, 0, getWidth(), getHeight());
            Color color = game.getBackgroundColor();
            glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            glClear(GL_COLOR_BUFFER_BIT);

            game.render(this, graphics);

            graphics.draw();
            glfwSwapBuffers(window);
        }

        game.dispose();
        graphics.dispose();
    }

    @Override
    public void close() {
        glfwSetWindowShouldClose(window, true);
    }

    @Override
    public int getX() {
        tmp.clear();
        glfwGetWindowPos(window, tmp, null);
        return tmp.get();
    }

    @Override
    public void setX(int x) {
        glfwSetWindowPos(window, x, getY());
    }

    @Override
    public int getY() {
        tmp.clear();
        glfwGetWindowPos(window, null, tmp);
        return tmp.get();
    }

    @Override
    public void setY(int y) {
        glfwSetWindowPos(window, getX(), y);
    }

    @Override
    public int getWidth() {
        tmp.clear();
        glfwGetWindowSize(window, tmp, null);
        return tmp.get();
    }

    @Override
    public void setWidth(int width) {
        glfwSetWindowSize(window, width, getHeight());
    }

    @Override
    public int getHeight() {
        tmp.clear();
        glfwGetWindowSize(window, null, tmp);
        return tmp.get();
    }

    @Override
    public void setHeight(int height) {
        glfwSetWindowSize(window, getWidth(), height);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isUsingVSync() {
        return useVsync;
    }

    @Override
    public void setUseVSync(boolean useVSync) {
        glfwSwapInterval(useVSync ? 1 : 0);
        this.useVsync = useVSync;
    }

    @Override
    public Monitor getMonitor() {
        return monitor;
    }

    @Override
    public void setMonitor(Monitor monitor) {
        glfwSetWindowMonitor(window, monitor.getHandle(), getX(), getY(), getWidth(), getHeight(), GLFW_DONT_CARE);
        this.monitor = monitor;
    }

    @Override
    public WindowState getState() {
        return state;
    }

    @Override
    public void setState(WindowState state) {
        switch (state) {
            case MINIMIZED:
                glfwIconifyWindow(window);
                break;
            case RESTORED:
                glfwSetWindowMonitor(window, NULL, getX(), getY(), getWidth(), getHeight(), GLFW_DONT_CARE);
                break;
            case MAXIMIZED:
                glfwSetWindowMonitor(window, NULL, getX(), getY(), getWidth(), getHeight(), GLFW_DONT_CARE);
                glfwMaximizeWindow(window);
                break;
            case FULLSCREEN:
                glfwSetWindowMonitor(window, getMonitor().getHandle(), getX(), getY(), getWidth(), getHeight(), GLFW_DONT_CARE);
                break;
            default:
                throw new SJGFException("Unknown state!: " + state);
        }
        this.state = state;
    }

    @Override
    public boolean isVisible() {
        return glfwGetWindowAttrib(window, GLFW_VISIBLE) == GLFW_TRUE;
    }

    @Override
    public boolean isResizable() {
        return glfwGetWindowAttrib(window, GLFW_RESIZABLE) == GLFW_TRUE;
    }

    @Override
    public Game getGame() {
        return game;
    }

    public long getHandle() {
        return window;
    }
}
