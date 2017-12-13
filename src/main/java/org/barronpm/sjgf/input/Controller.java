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

package org.barronpm.sjgf.input;

import org.barronpm.sjgf.backend.Args;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWGamepadState;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

public final class Controller {

    private static final Controller[] controllers = new Controller[17];

    private final int slot;
    private final GLFWGamepadState state;

    static {
        for (int i = 0; i < controllers.length; i++) {
            controllers[i] = new Controller(i);
        }
    }

    private Controller(int slot) {
        this.slot = slot;
        this.state = new GLFWGamepadState(BufferUtils.createByteBuffer(40));
    }

    public static Controller getController(int slot) {
        return controllers[slot];
    }

    public static Set<Controller> getConnectedControllers() {
        return Arrays.stream(controllers)
                .filter(Controller::isConnected)
                .collect(Collectors.toSet());
    }

    public static Set<Controller> getSupportedControllers() {
        return Arrays.stream(controllers)
                .filter(Controller::isSupported)
                .collect(Collectors.toSet());
    }

    public boolean isConnected() {
        return glfwJoystickPresent(slot);
    }

    public boolean isSupported() {
        return glfwJoystickIsGamepad(slot);
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return glfwGetGamepadName(slot);
    }

    public boolean isButtonDown(Buttons button) {
        glfwGetGamepadState(slot, state);

        return state.buttons(button.getId()) == GLFW_PRESS;
    }

    public Set<Buttons> getPressedButtons() {
        glfwGetGamepadState(slot, state);

        Set<Buttons> set = EnumSet.noneOf(Buttons.class);

        for (Buttons button : Buttons.values()) {
            if (state.buttons(button.getId()) == GLFW_PRESS)
                set.add(button);
        }

        return set;
    }

    public float getLeftStickX() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_LEFT_X);
    }

    public float getLeftStickY() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_LEFT_Y);
    }

    public float getRightStickX() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_X);
    }

    public float getRightStickY() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_Y);
    }

    public float getLeftTrigger() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER);
    }

    public float getRightTrigger() {
        glfwGetGamepadState(slot, state);

        return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);
    }

    public enum Buttons {
        A(GLFW_GAMEPAD_BUTTON_A),
        B(GLFW_GAMEPAD_BUTTON_B),
        X(GLFW_GAMEPAD_BUTTON_X),
        Y(GLFW_GAMEPAD_BUTTON_Y),
        LEFT_BUMPER(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),
        RIGHT_BUMPER(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),
        BACK(GLFW_GAMEPAD_BUTTON_BACK),
        START(GLFW_GAMEPAD_BUTTON_START),
        GUIDE(GLFW_GAMEPAD_BUTTON_GUIDE),
        LEFT_THUMB(GLFW_GAMEPAD_BUTTON_LEFT_THUMB),
        RIGHT_THUMB(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),
        DPAD_UP(GLFW_GAMEPAD_BUTTON_DPAD_UP),
        DPAD_RIGHT(GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),
        DPAD_DOWN(GLFW_GAMEPAD_BUTTON_DPAD_DOWN),
        DPAD_LEFT(GLFW_GAMEPAD_BUTTON_DPAD_LEFT);

        private final int id;

        Buttons(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Buttons getButtonById(int id) {
            // Currently this method relies on the ordinal of the enum being equal to its id. This may be broken
            // in future releases of GLFW. Look into a more robust solution without sacrificing speed.

            Args.inRange(0, values().length, id, "ID");
            return values()[id];
        }
    }
}
