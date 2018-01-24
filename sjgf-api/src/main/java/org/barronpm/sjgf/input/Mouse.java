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

package org.barronpm.sjgf.input;

import org.barronpm.sjgf.util.Args;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

public final class Mouse {

    private static final DoubleBuffer tmp = BufferUtils.createDoubleBuffer(1);

    public float getX() {
        tmp.clear();
        glfwGetCursorPos(glfwGetCurrentContext(), tmp, null);

        return (float) tmp.get();
    }

    public float getY() {
        tmp.clear();
        glfwGetCursorPos(glfwGetCurrentContext(), null, tmp);

        return (float) tmp.get();
    }

    public static boolean isButtonPressed(Buttons button) {
        return glfwGetMouseButton(glfwGetCurrentContext(), button.getId()) == GLFW_PRESS;
    }

    public static Set<Buttons> getPressedButtons() {
        return Arrays.stream(Buttons.values())
                .filter(Mouse::isButtonPressed)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Buttons.class)));
    }

    public enum Buttons {
        LEFT(GLFW_MOUSE_BUTTON_LEFT),
        RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
        MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE);

        private final int id;

        Buttons(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Buttons getButtonById(int id) {
            // Similar problems as Controller.Buttons.getButtonById

            Args.inRange(0, values().length, id, "ID");
            return values()[id];
        }
    }
}
