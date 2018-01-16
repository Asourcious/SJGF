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

import org.barronpm.sjgf.util.Args;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWGamepadState;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Represents a controller.
 *
 * An instance of this class may or may not represent a connected
 * device. Not all controller types are supported. To check if a
 * controller is supported, use the {@link #isSupported()} method.
 *
 * @author Patrick Barron
 * @see Buttons
 * @see Keyboard
 * @see Mouse
 * @since 1.0
 */
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

    /**
     * Returns the controller instance for the provided slot.
     *
     * The returned controller object may or may not be connected
     * and may or may not be supported.
     *
     * @param slot the slot to return, in the range 0 to 16 inclusive
     * @return the controller instance for the provided slot
     * @throws IndexOutOfBoundsException if the slot is not in the range 0 to 16
     * @see #getConnectedControllers()
     * @see #getSupportedControllers()
     * @since 1.0
     */
    public static Controller getController(int slot) {
        Args.inRange(0, controllers.length, slot, "slot");
        return controllers[slot];
    }

    /**
     * Returns all controller objects that represent connected controllers.
     * To get all supported controllers, use the {@link #getSupportedControllers()}
     * method.
     *
     * @return a non-null set of connected controllers.
     * @see #getController(int)
     * @see #getSupportedControllers()
     * @since 1.0
     */
    public static Set<Controller> getConnectedControllers() {
        return Arrays.stream(controllers)
                .filter(Controller::isConnected)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all controller objects that represent supported controllers.
     * To get all connected controllers, use the {@link #getConnectedControllers()}
     * method.
     *
     * @return a non-null set of supported controllers.
     * @see #getConnectedControllers()
     * @since 1.0
     */
    public static Set<Controller> getSupportedControllers() {
        return Arrays.stream(controllers)
                .filter(Controller::isSupported)
                .collect(Collectors.toSet());
    }

    /**
     * Returns whether or not this object represents a connected
     * controller. To see if this controller is supported, use the
     * {@link #isSupported()} method.
     *
     * @return whether this controller is connected.
     * @see #isSupported()
     * @since 1.0
     */
    public boolean isConnected() {
        return glfwJoystickPresent(slot);
    }

    /**
     * Returns whether or not this object represents a connected and supported
     * controller.
     *
     * @return whether this controller is supported.
     * @see #isConnected()
     * @since 1.0
     */
    public boolean isSupported() {
        return glfwJoystickIsGamepad(slot);
    }

    /**
     * Returns the slot that this controller occupies.
     * This will be in the range 0-16 inclusive.
     *
     * @return the slot this controller occupies
     * @since 1.0
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Returns the human-readable name of this controller, or <code>null</code>
     * if this controller isn't connected. This method will return a name even
     * if this controller isn't supported.
     *
     * @return The human-readable name of this controller, or <code>null</code>
     * if the controller isn't connected.
     * @since 1.0
     */
    public String getName() {
        if (glfwJoystickIsGamepad(slot))
            return glfwGetGamepadName(slot);

        return glfwGetJoystickName(slot);
    }

    /**
     * Returns whether or not the given button is pressed.
     *
     * @param button the button to check
     * @return whether or not the provided button is pressed
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public boolean isButtonDown(Buttons button) {
        return glfwGetJoystickButtons(slot).get(button.getId()) == GLFW_PRESS;
        //checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.buttons(button.getId()) == GLFW_PRESS;
    }

    /**
     * Returns a never-null set of the buttons that are currently pressed.
     *
     * @return a non-null set of currently pressed buttons
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public Set<Buttons> getPressedButtons() {
        checkConnected();
        checkSupported();
        glfwGetGamepadState(slot, state);

        Set<Buttons> set = EnumSet.noneOf(Buttons.class);

        for (Buttons button : Buttons.values()) {
            if (state.buttons(button.getId()) == GLFW_PRESS)
                set.add(button);
        }

        return set;
    }

    /**
     * Returns the x value of the left stick.
     *
     * @return the x value of the left stick in the range [-1, 1]
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getLeftStickX() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_LEFT_X);
        return glfwGetJoystickAxes(slot).get(0);
    }

    /**
     * Returns the y value of the left stick.
     *
     * @return the y value of the left stick in the range [-1, 1]
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getLeftStickY() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_LEFT_Y);
        return glfwGetJoystickAxes(slot).get(1);
    }

    /**
     * Returns the x value of the right stick.
     *
     * @return the x value of the right stick in the range [-1, 1]
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getRightStickX() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_X);
        return glfwGetJoystickAxes(slot).get(3);
    }

    /**
     * Returns the y value of the right stick.
     *
     * @return the y value of the right stick in the range [-1, 1]
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getRightStickY() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_Y);
        return glfwGetJoystickAxes(slot).get(4);
    }

    /**
     * Returns how depressed the left trigger is, in the range [0, 1]
     *
     * @return the state of the left trigger
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getLeftTrigger() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER);
        return glfwGetJoystickAxes(slot).get(2);
    }

    /**
     * @return the state of the right trigger
     * @throws IllegalStateException if the controller is not connected or not supported
     * @since 1.0
     */
    public float getRightTrigger() {
        checkConnected();
        //checkSupported();
        //glfwGetGamepadState(slot, state);

        //return state.axes(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);
        return glfwGetJoystickAxes(slot).get(5);
    }

    private void checkConnected() {
        if (!isConnected())
            throw new IllegalStateException("Controller isn't connected!");
    }

    private void checkSupported() {
        if (!isSupported())
            throw new IllegalStateException("Controller isn't supported!");
    }


    /**
     * Represents controller buttons.
     *
     * @author Patrick Barron
     * @since 1.0
     */
    public enum Buttons {

        /**
         * The A button. Equivalent to the cross button on a PlayStation controller.
         *
         * @since 1.0
         */
        A(GLFW_GAMEPAD_BUTTON_A),

        /**
         * The B button. Equivalent to the circle button on a PlayStation controller.
         *
         * @since 1.0
         */
        B(GLFW_GAMEPAD_BUTTON_B),

        /**
         * The X button. Equivalent to the square button on a PlayStation controller.
         *
         * @since 1.0
         */
        X(GLFW_GAMEPAD_BUTTON_X),

        /**
         * The Y button. Equivalent to the triangle button on a PlayStation controller.
         *
         * @since 1.0
         */
        Y(GLFW_GAMEPAD_BUTTON_Y),

        /**
         * The left bumper.
         *
         * @since 1.0
         */
        LEFT_BUMPER(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),

        /**
         * The right bumper.
         *
         * @since 1.0
         */
        RIGHT_BUMPER(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),

        /**
         * The back button.
         *
         * @since 1.0
         */
        BACK(GLFW_GAMEPAD_BUTTON_BACK),

        /**
         * The start button.
         *
         * @since 1.0
         */
        START(GLFW_GAMEPAD_BUTTON_START),

        /**
         * The guide button.
         *
         * @since 1.0
         */
        GUIDE(GLFW_GAMEPAD_BUTTON_GUIDE),

        /**
         * The left stick button.
         *
         * @since 1.0
         */
        LEFT_THUMB(GLFW_GAMEPAD_BUTTON_LEFT_THUMB),

        /**
         * The right stick button.
         *
         * @since 1.0
         */
        RIGHT_THUMB(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),

        /**
         * The up button.
         *
         * @since 1.0
         */
        DPAD_UP(GLFW_GAMEPAD_BUTTON_DPAD_UP),

        /**
         * The right button.
         *
         * @since 1.0
         */
        DPAD_RIGHT(GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),

        /**
         * The down button.
         *
         * @since 1.0
         */
        DPAD_DOWN(GLFW_GAMEPAD_BUTTON_DPAD_DOWN),

        /**
         * The left button.
         *
         * @since 1.0
         */
        DPAD_LEFT(GLFW_GAMEPAD_BUTTON_DPAD_LEFT);

        private final int id;

        Buttons(int id) {
            this.id = id;
        }

        /**
         * Returns the id of this button.
         *
         * @return the id of this button
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the button that has the provided id.
         *
         * @param id the id to
         * @return the button associated with the id.
         */
        public static Buttons getButtonById(int id) {
            // Currently this method relies on the ordinal of the enum being equal to its id. This may be broken
            // in future releases of GLFW. Look into a more robust solution without sacrificing speed.

            Args.inRange(0, values().length, id, "ID");
            return values()[id];
        }
    }
}
