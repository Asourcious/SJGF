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
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents a mouse.
 *
 * @author Patrick Barron
 * @see Keyboard
 * @since 1.0
 */
public final class Mouse {

    /**
     * Returns the x position of the cursor, in screen coordinates,
     * relative to the upper-left corner of the client area of the specified window
     *
     * @return the x position of the cursor
     * @since 1.0
     */
    public static float getX() {
        MemoryStack stack = MemoryStack.stackPush();
        DoubleBuffer buffer = stack.mallocDouble(1);
        glfwGetCursorPos(glfwGetCurrentContext(), buffer, null);
        float x = (float) buffer.get();
        stack.pop();

        return x;
    }

    /**
     * Returns the y position of the cursor, in screen coordinates,
     * relative to the upper-left corner of the client area of the specified window
     *
     * @return the y position of the cursor
     * @since 1.0
     */
    public static float getY() {
        MemoryStack stack = MemoryStack.stackPush();
        DoubleBuffer buffer = stack.mallocDouble(1);
        IntBuffer height = stack.mallocInt(1);
        glfwGetCursorPos(glfwGetCurrentContext(), null, buffer);
        glfwGetWindowSize(glfwGetCurrentContext(), null, height);
        float y = (float) buffer.get() - height.get();
        stack.pop();

        return y;
    }

    /**
     * Returns whether a provided button is currently pressed.
     *
     * @param button the button to check
     * @return whether the button is pressed.
     * @since 1.0
     */
    public static boolean isButtonPressed(Buttons button) {
        return glfwGetMouseButton(glfwGetCurrentContext(), button.getId()) == GLFW_PRESS;
    }

    /**
     * Returns a set containing the pressed buttons.
     *
     * @return a set containing the pressed buttons
     * @since 1.0
     */
    public static Set<Buttons> getPressedButtons() {
        return Arrays.stream(Buttons.values())
                .filter(Mouse::isButtonPressed)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Buttons.class)));
    }

    /**
     * Represents the buttons on a mouse.
     *
     * @author Patrick Barron
     * @since 1.0
     */
    public enum Buttons {

        /**
         * The left mouse button
         *
         * @since 1.0
         */
        LEFT(GLFW_MOUSE_BUTTON_LEFT),

        /**
         * The right mouse button
         *
         * @since 1.0
         */
        RIGHT(GLFW_MOUSE_BUTTON_RIGHT),

        /**
         * The middle mouse button
         *
         * @since 1.0
         */
        MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE);

        private final int id;

        Buttons(int id) {
            this.id = id;
        }

        /**
         * Returns the id associated with this button.
         * This is useful for serialization.
         *
         * @return the id associated with this button.
         * @since 1.0
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the button associated with the following id.
         *
         * @param id the id
         * @return the associated button.
         * @since 1.0
         */
        public static Buttons getButtonById(int id) {
            // Similar problems as Controller.Buttons.getButtonById

            Args.inRange(0, values().length, id, "ID");
            return values()[id];
        }
    }
}
