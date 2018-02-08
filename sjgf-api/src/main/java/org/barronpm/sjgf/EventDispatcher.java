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

package org.barronpm.sjgf;

import org.barronpm.sjgf.events.Event;
import org.barronpm.sjgf.events.MonitorConnectedEvent;
import org.barronpm.sjgf.events.MonitorDisconnectedEvent;
import org.barronpm.sjgf.events.input.*;
import org.barronpm.sjgf.input.Keys;
import org.barronpm.sjgf.math.Vector2;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class EventDispatcher {

    private final Set<EventListener> eventListeners = new HashSet<>();

    public EventDispatcher(GameWindow window, long handle) {
        glfwSetMonitorCallback(new GLFWMonitorCallback() {
            @Override
            public void invoke(long handle, int eventCode) {
                Monitor monitor;
                if (eventCode == GLFW_CONNECTED) {
                    Monitor.monitors.put(handle, new Monitor(handle));
                    monitor = Monitor.getByHandle(handle);
                } else {
                    monitor = Monitor.getByHandle(handle);
                    Monitor.monitors.remove(handle);
                }

                dispatchEvent(eventCode == GLFW_CONNECTED ?
                        new MonitorConnectedEvent(window, monitor) :
                        new MonitorDisconnectedEvent(window, monitor));
            }
        });

        glfwSetKeyCallback(handle, new GLFWKeyCallback() {
            @Override
            public void invoke(long windowHandle, int key, int scancode, int action, int mods) {
                Event event = null;
                switch (action) {
                    case GLFW_RELEASE:
                        event = new KeyReleaseEvent(window, Keys.getKeyByCode(key));
                        break;
                    case GLFW_PRESS:
                        event = new KeyPressEvent(window, Keys.getKeyByCode(key));
                        break;
                    case GLFW_REPEAT:
                        event = new KeyHoldEvent(window, Keys.getKeyByCode(key));
                        break;
                }
                dispatchEvent(event);
            }
        });

        glfwSetCursorEnterCallback(handle, new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long windowHandle, boolean entered) {
                dispatchEvent(entered ? new MouseEnterEvent(window) : new MouseLeaveEvent(window));
            }
        });

        glfwSetCursorPosCallback(handle, new GLFWCursorPosCallback() {
            private float oldX;
            private float oldY;

            @Override
            public void invoke(long windowHandle, double x, double y) {
                dispatchEvent(new MouseMoveEvent(window,
                        new Vector2(oldX, oldY),
                        new Vector2((float) x, (float) y)));

                oldX = (float) x;
                oldY = (float) y;
            }
        });
    }

    private void dispatchEvent(Event event) {
        eventListeners.forEach(listener -> listener.onEvent(event));
    }

    public void addListener(EventListener listener) {
        eventListeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        eventListeners.remove(listener);
    }

    public Set<EventListener> getListeners() {
        return eventListeners;
    }
}
