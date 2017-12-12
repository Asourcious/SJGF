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

package org.barronpm.sjgf;

import org.barronpm.sjgf.backend.Engine;
import org.barronpm.sjgf.backend.GlGameWindow;
import org.barronpm.sjgf.exceptions.SJGFException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindowBuilder {

    private final Game game;

    private int x = 0;
    private int y = 0;
    private int width = 800;
    private int height = 600;
    private String title = "SJGF";
    private boolean resizable = true;
    private boolean visible = true;
    private boolean useVsync = true;
    private Monitor monitor = null;
    private WindowState state = WindowState.RESTORED;

    public GameWindowBuilder(Game game) {
        if (game == null)
            throw new NullPointerException("Provided game was null");

        this.game = game;
    }

    public int getX() {
        return x;
    }

    public GameWindowBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public GameWindowBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public GameWindowBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GameWindowBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameWindowBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isResizable() {
        return resizable;
    }

    public GameWindowBuilder setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public GameWindowBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isUsingVsync() {
        return useVsync;
    }

    public GameWindowBuilder setUseVsync(boolean useVsync) {
        this.useVsync = useVsync;
        return this;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public GameWindowBuilder setMonitor(Monitor monitor) {
        this.monitor = monitor;
        return this;
    }

    public WindowState getState() {
        return state;
    }

    public GameWindowBuilder setState(WindowState state) {
        this.state = state;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public GameWindow build() {
        if (Engine.instance != null)
            throw new IllegalStateException("Only one GameWindow can exist");

        if (!glfwInit())
            throw new SJGFException("Failed to initialize GLFW");

        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, visible ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        long window = glfwCreateWindow(width, height, title,
                state.equals(WindowState.FULLSCREEN) ? monitor.getHandle() : NULL,
                NULL);

        if (window == NULL)
            throw new SJGFException("Failed to create window");

        Engine.instance = new GlGameWindow(game, window, title, monitor, useVsync, state);
        return Engine.instance;
    }
}
