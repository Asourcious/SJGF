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

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents a particular key on the keyboard.
 *
 * @author Patrick Barron
 * @see Keyboard
 * @since 1.0
 */
public enum Keys {

    /**
     * The space " " key.
     *
     * @since 1.0
     */
    SPACE(GLFW_KEY_SPACE),

    /**
     * The apostrophe "'" key.
     *
     * @since 1.0
     */
    APOSTROPHE(GLFW_KEY_APOSTROPHE),

    /**
     * The comma "," key.
     *
     * @since 1.0
     */
    COMMA(GLFW_KEY_COMMA),

    /**
     * The minus "-" key.
     *
     * @since 1.0
     */
    MINUS(GLFW_KEY_MINUS),

    /**
     * The period "." key.
     *
     * @since 1.0
     */
    PERIOD(GLFW_KEY_PERIOD),

    /**
     * The slash "/" key.
     *
     * @since 1.0
     */
    SLASH(GLFW_KEY_SLASH),

    /**
     * The zero "0" key.
     *
     * @since 1.0
     */
    ZERO(GLFW_KEY_0),

    /**
     * The one "1" key.
     *
     * @since 1.0
     */
    ONE(GLFW_KEY_1),

    /**
     * The two "2" key.
     *
     * @since 1.0
     */
    TWO(GLFW_KEY_2),

    /**
     * The three "3" key.
     *
     * @since 1.0
     */
    THREE(GLFW_KEY_3),

    /**
     * The four "4" key.
     *
     * @since 1.0
     */
    FOUR(GLFW_KEY_4),

    /**
     * The five "5" key.
     *
     * @since 1.0
     */
    FIVE(GLFW_KEY_5),

    /**
     * The six "6" key.
     *
     * @since 1.0
     */
    SIX(GLFW_KEY_6),

    /**
     * The seven "7" key.
     *
     * @since 1.0
     */
    SEVEN(GLFW_KEY_7),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    EIGHT(GLFW_KEY_8),

    /**
     * The nine "9" key.
     *
     * @since 1.0
     */
    NINE(GLFW_KEY_9),

    /**
     * The semicolon ";" key.
     *
     * @since 1.0
     */
    SEMICOLON(GLFW_KEY_SEMICOLON),

    /**
     * The equal "=" key.
     *
     * @since 1.0
     */
    EQUAL(GLFW_KEY_EQUAL),

    /**
     * The A "a" key.
     *
     * @since 1.0
     */
    A(GLFW_KEY_A),

    /**
     * The B "b" key.
     *
     * @since 1.0
     */
    B(GLFW_KEY_B),

    /**
     * The C "c" key.
     *
     * @since 1.0
     */
    C(GLFW_KEY_C),

    /**
     * The D "d" key.
     *
     * @since 1.0
     */
    D(GLFW_KEY_D),

    /**
     * The E "e" key.
     *
     * @since 1.0
     */
    E(GLFW_KEY_E),

    /**
     * The F "f" key.
     *
     * @since 1.0
     */
    F(GLFW_KEY_F),

    /**
     * The G "g" key.
     *
     * @since 1.0
     */
    G(GLFW_KEY_G),

    /**
     * The H "h" key.
     *
     * @since 1.0
     */
    H(GLFW_KEY_H),

    /**
     * The I "i" key.
     *
     * @since 1.0
     */
    I(GLFW_KEY_I),

    /**
     * The J "j" key.
     *
     * @since 1.0
     */
    J(GLFW_KEY_J),

    /**
     * The K "k" key.
     *
     * @since 1.0
     */
    K(GLFW_KEY_K),

    /**
     * The L "l" key.
     *
     * @since 1.0
     */
    L(GLFW_KEY_L),

    /**
     * The M "m" key.
     *
     * @since 1.0
     */
    M(GLFW_KEY_M),

    /**
     * The N "n" key.
     *
     * @since 1.0
     */
    N(GLFW_KEY_N),

    /**
     * The O "o" key.
     *
     * @since 1.0
     */
    O(GLFW_KEY_O),

    /**
     * The P "p" key.
     *
     * @since 1.0
     */
    P(GLFW_KEY_P),

    /**
     * The Q "q" key.
     *
     * @since 1.0
     */
    Q(GLFW_KEY_Q),

    /**
     * The R "r" key.
     *
     * @since 1.0
     */
    R(GLFW_KEY_R),

    /**
     * The S "s" key.
     *
     * @since 1.0
     */
    S(GLFW_KEY_S),

    /**
     * The T "t" key.
     *
     * @since 1.0
     */
    T(GLFW_KEY_T),

    /**
     * The U "u" key.
     *
     * @since 1.0
     */
    U(GLFW_KEY_U),

    /**
     * The V "v" key.
     *
     * @since 1.0
     */
    V(GLFW_KEY_V),

    /**
     * The W "w" key.
     *
     * @since 1.0
     */
    W(GLFW_KEY_W),

    /**
     * The X "x" key.
     *
     * @since 1.0
     */
    X(GLFW_KEY_X),

    /**
     * The Y "y" key.
     *
     * @since 1.0
     */
    Y(GLFW_KEY_Y),

    /**
     * The Z "z" key.
     *
     * @since 1.0
     */
    Z(GLFW_KEY_Z),

    /**
     * The left bracket "[" key.
     *
     * @since 1.0
     */
    LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),

    /**
     * The backslash "\" key.
     *
     * @since 1.0
     */
    BACKSLASH(GLFW_KEY_BACKSLASH),

    /**
     * The right bracket "]" key.
     *
     * @since 1.0
     */
    RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),

    /**
     * The grave accent "`" key.
     *
     * @since 1.0
     */
    GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),

    /**
     * The escape key.
     *
     * @since 1.0
     */
    ESCAPE(GLFW_KEY_ESCAPE),

    /**
     * The enter key.
     *
     * @since 1.0
     */
    ENTER(GLFW_KEY_ENTER),

    /**
     * The tab "  " key.
     *
     * @since 1.0
     */
    TAB(GLFW_KEY_TAB),

    /**
     * The backspace key.
     *
     * @since 1.0
     */
    BACKSPACE(GLFW_KEY_BACKSPACE),

    /**
     * The insert key.
     *
     * @since 1.0
     */
    INSERT(GLFW_KEY_INSERT),

    /**
     * The delete key.
     *
     * @since 1.0
     */
    DELETE(GLFW_KEY_DELETE),

    /**
     * The right arrow key.
     *
     * @since 1.0
     */
    RIGHT(GLFW_KEY_RIGHT),

    /**
     * The left arrow key.
     *
     * @since 1.0
     */
    LEFT(GLFW_KEY_LEFT),

    /**
     * The down arrow key.
     *
     * @since 1.0
     */
    DOWN(GLFW_KEY_DOWN),

    /**
     * The up arrow key.
     *
     * @since 1.0
     */
    UP(GLFW_KEY_UP),

    /**
     * The page up key.
     *
     * @since 1.0
     */
    PAGE_UP(GLFW_KEY_PAGE_UP),

    /**
     * The page down key.
     *
     * @since 1.0
     */
    PAGE_DOWN(GLFW_KEY_PAGE_DOWN),

    /**
     * The home key.
     *
     * @since 1.0
     */
    HOME(GLFW_KEY_HOME),

    /**
     * The end key.
     *
     * @since 1.0
     */
    END(GLFW_KEY_END),

    /**
     * The caps lock key.
     *
     * @since 1.0
     */
    CAPS_LOCK(GLFW_KEY_CAPS_LOCK),

    /**
     * The scroll lock key.
     *
     * @since 1.0
     */
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),

    /**
     * The num lock key.
     *
     * @since 1.0
     */
    NUM_LOCK(GLFW_KEY_NUM_LOCK),

    /**
     * The print screen key.
     *
     * @since 1.0
     */
    PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),

    /**
     * The pause key.
     *
     * @since 1.0
     */
    PAUSE(GLFW_KEY_PAUSE),

    /**
     * The F1 key.
     *
     * @since 1.0
     */
    F1(GLFW_KEY_F1),

    /**
     * The F2 key.
     *
     * @since 1.0
     */
    F2(GLFW_KEY_F2),

    /**
     * The F3 key.
     *
     * @since 1.0
     */
    F3(GLFW_KEY_F3),

    /**
     * The F4 key.
     *
     * @since 1.0
     */
    F4(GLFW_KEY_F4),

    /**
     * The F5 key.
     *
     * @since 1.0
     */
    F5(GLFW_KEY_F5),

    /**
     * The F6 key.
     *
     * @since 1.0
     */
    F6(GLFW_KEY_F6),

    /**
     * The F7 key.
     *
     * @since 1.0
     */
    F7(GLFW_KEY_F7),

    /**
     * The F8 key.
     *
     * @since 1.0
     */
    F8(GLFW_KEY_F8),

    /**
     * The F9 key.
     *
     * @since 1.0
     */
    F9(GLFW_KEY_F9),

    /**
     * The F10 key.
     *
     * @since 1.0
     */
    F10(GLFW_KEY_F10),

    /**
     * The F11 key.
     *
     * @since 1.0
     */
    F11(GLFW_KEY_F11),

    /**
     * The F12 key.
     *
     * @since 1.0
     */
    F12(GLFW_KEY_F12),

    /**
     * The F13 key.
     *
     * @since 1.0
     */
    F13(GLFW_KEY_F13),

    /**
     * The F14 key.
     *
     * @since 1.0
     */
    F14(GLFW_KEY_F14),

    /**
     * The F15 key.
     *
     * @since 1.0
     */
    F15(GLFW_KEY_F15),

    /**
     * The F16 key.
     *
     * @since 1.0
     */
    F16(GLFW_KEY_F16),

    /**
     * The F17 key.
     *
     * @since 1.0
     */
    F17(GLFW_KEY_F17),

    /**
     * The F18 key.
     *
     * @since 1.0
     */
    F18(GLFW_KEY_F18),

    /**
     * The F19 key.
     *
     * @since 1.0
     */
    F19(GLFW_KEY_F19),

    /**
     * The F20 key.
     *
     * @since 1.0
     */
    F20(GLFW_KEY_F20),

    /**
     * The F21 key.
     *
     * @since 1.0
     */
    F21(GLFW_KEY_F21),

    /**
     * The F22 key.
     *
     * @since 1.0
     */
    F22(GLFW_KEY_F22),

    /**
     * The F23 key.
     *
     * @since 1.0
     */
    F23(GLFW_KEY_F23),

    /**
     * The F24 key.
     *
     * @since 1.0
     */
    F24(GLFW_KEY_F24),

    /**
     * The F25 key.
     *
     * @since 1.0
     */
    F25(GLFW_KEY_F25),

    /**
     * The "0" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_0(GLFW_KEY_KP_0),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_1(GLFW_KEY_KP_1),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_2(GLFW_KEY_KP_2),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_3(GLFW_KEY_KP_3),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_4(GLFW_KEY_KP_4),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_5(GLFW_KEY_KP_5),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    NUMPAD_6(GLFW_KEY_KP_6),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    NUMPAD_7(GLFW_KEY_KP_7),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    NUMPAD_8(GLFW_KEY_KP_8),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    NUMPAD_9(GLFW_KEY_KP_9),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_DECIMAL(GLFW_KEY_KP_DECIMAL),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_DIVIDE(GLFW_KEY_KP_DIVIDE),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_MULTIPLY(GLFW_KEY_KP_MULTIPLY),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_SUBTRACT(GLFW_KEY_KP_SUBTRACT),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_ADD(GLFW_KEY_KP_ADD),

    /**
     * The "1" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_ENTER(GLFW_KEY_KP_ENTER),

    /**
     * The "=" key on the number pad.
     *
     * @since 1.0
     */
    NUMPAD_EQUAL(GLFW_KEY_KP_EQUAL),

    /**
     * The left shift key.
     *
     * @since 1.0
     */
    LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),

    /**
     * The left control key.
     *
     * @since 1.0
     */
    LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),

    /**
     * The left alt key.
     *
     * @since 1.0
     */
    LEFT_ALT(GLFW_KEY_LEFT_ALT),

    /**
     * The left super (command) key.
     *
     * @since 1.0
     */
    LEFT_SUPER(GLFW_KEY_LEFT_SUPER),

    /**
     * The right shift key.
     *
     * @since 1.0
     */
    RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),

    /**
     * The right control key.
     *
     * @since 1.0
     */
    RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),

    /**
     * The eight "8" key.
     *
     * @since 1.0
     */
    RIGHT_ALT(GLFW_KEY_RIGHT_ALT),

    /**
     * The right super (command) key.
     *
     * @since 1.0
     */
    RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),

    /**
     * The menu key.
     *
     * @since 1.0
     */
    MENU(GLFW_KEY_MENU);

    private static final Map<Integer, Keys> codes = new HashMap<>();

    private final int code;

    Keys(int code) {
        this.code = code;
    }

    /**
     * Returns the code represented by this key. Useful for storing. You can find the key instance'
     * associated with this code by calling {@link #getKeyByCode(int)}.
     *
     * @return the key associated with this code.
     * @see #getKeyByCode(int)
     * @since 1.0
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the key associated with the provided code, or <code>null</code> if no such key exists.
     *
     * @param code the code for the key.
     * @return the key associated with the provided code.
     * @see #getCode()
     * @since 1.0
     */
    public static Keys getKeyByCode(int code) {
        // This is a more robust solution than the one in Controller.Buttons, but it is very verbose
        // and should likewise be considered for a replacement by a better solution

        if (codes.isEmpty()) {
            for (Keys key : values()) {
                codes.put(key.code, key);
            }
        }

        return codes.get(code);
    }
}