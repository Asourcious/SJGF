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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents a keyboard.
 *
 * @author Patrick Barron
 * @see Mouse
 * @see Keys
 * @since 1.0
 */
public final class Keyboard {

    private Keyboard() {}

    /**
     * Returns whether or not the provided key is currently being pressed.
     *
     * @param key the key to check.
     * @return whether the key is pressed
     * @since 1.0
     */
    public static boolean isKeyPressed(Keys key) {
        return glfwGetKey(glfwGetCurrentContext(), key.getCode()) == GLFW_PRESS;
    }

    /**
     * Returns a set containing all of the currently pressed keys.
     *
     * @return a set containing the pressed keys.
     * @since 1.0
     */
    public static Set<Keys> getPressedKeys() {
        return Arrays.stream(Keys.values())
                .filter(Keyboard::isKeyPressed)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Keys.class)));
    }
}
