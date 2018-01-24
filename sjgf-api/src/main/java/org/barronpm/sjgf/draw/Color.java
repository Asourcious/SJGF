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

package org.barronpm.sjgf.draw;

/**
 * Represents a color in the sRGB color space.
 *
 * @author Patrick Barron
 * @see Graphics
 * @see Texture
 * @since 1.0
 */
public final class Color {

    /**
     * The color black.
     *
     * @since 1.0
     */
    public static final Color BLACK = new Color(0, 0, 0);

    /**
     * The color dark gray.
     *
     * @since 1.0
     */
    public static final Color DARK_GRAY = new Color(.25f, .25f, .25f);

    /**
     * The color gray
     *
     * @since 1.0
     */
    public static final Color GRAY = new Color(.5f, .5f, .5f);

    /**
     * The color light gray
     *
     * @since 1.0
     */
    public static final Color LIGHT_GRAY = new Color(.75f, .75f, .75f);

    /**
     * The color white
     *
     * @since 1.0
     */
    public static final Color WHITE = new Color(1, 1, 1);

    /**
     * The color blue
     *
     * @since 1.0
     */
    public static final Color BLUE = new Color(0, 0, 1);

    /**
     * The color cyan
     *
     * @since 1.0
     */
    public static final Color CYAN = new Color(0, 1, 1);

    /**
     * The color green
     *
     * @since 1.0
     */
    public static final Color GREEN = new Color(0, 1, 0);

    /**
     * The color yellow
     *
     * @since 1.0
     */
    public static final Color YELLOW = new Color(1, 1, 0);

    /**
     * The color orange
     *
     * @since 1.0
     */
    public static final Color ORANGE = new Color(1, .5f, 0);

    /**
     * The color red
     *
     * @since 1.0
     */
    public static final Color RED = new Color(1, 0, 0);

    /**
     * The color magenta
     *
     * @since 1.0
     */
    public static final Color MAGENTA = new Color(1, 0, 1);

    /**
     * The color pink
     *
     * @since 1.0
     */
    public static final Color PINK = new Color(1, .5f, .9f);

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    /**
     * Creates a color with the specified red, green, and blue components in the range (0-1).
     * Alpha defaults to 1.
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @throws IllegalArgumentException if r, g, or b are outside of the range 0 to 1 inclusive
     * @since 1.0
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    /**
     * Creates a color with the specified red, green, blue, and alpha components in the range (0-1).
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     * @throws IllegalArgumentException if r, g, or b are outside of the range 0 to 1 inclusive
     * @since 1.0
     */
    public Color(float r, float g, float b, float a) {
        if(r < 0 || r > 2
                || g < 0 || g > 1
                || b < 0 || b > 1
                || a < 0 || a > 1)
            throw new IllegalArgumentException("Values must be between 0 and 1!");

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Returns the red component, from 0 to 1
     *
     * @return the red component
     * @since 1.0
     */
    public float getRed() {
        return r;
    }

    /**
     * Returns the green component, from 0 to 1
     *
     * @return the green component
     * @since 1.0
     */
    public float getGreen() {
        return g;
    }

    /**
     * Returns the blue component, from 0 to 1
     *
     * @return the blue component
     * @since 1.0
     */
    public float getBlue() {
        return b;
    }

    /**
     * Returns the alpha component, from 0 to 1
     *
     * @return the alpha component
     * @since 1.0
     */
    public float getAlpha() {
        return a;
    }

    /**
     * Returns a string representation of this object. This value is intended for debugging purposes
     * and may change from release to release.
     *
     * @return a string representation of this object
     * @since 1.0
     */
    @Override
    public String toString() {
        return getClass().getName() +"[r=" + r + ",g=" + g + ",b=" + b + ",a=" + a + "]";
    }

    /**
     * Returns whether or not this object is equal to another object.
     * This returns true if and only if o is not null and the red, green, blue, and alpha
     * components are equal
     *
     * @param o the object to be compared to
     * @return true if the other color is equal to this, false otherwise
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return Float.compare(color.r, r) == 0
                && Float.compare(color.g, g) == 0
                && Float.compare(color.b, b) == 0
                && Float.compare(color.a, a) == 0;
    }

    /**
     * Computes the hash code for this Color
     *
     * @return the hash code value of this Color
     * @since 1.0
     */
    @Override
    public int hashCode() {
        int result = (r != +0.0f ? Float.floatToIntBits(r) : 0);
        result = 31 * result + (g != +0.0f ? Float.floatToIntBits(g) : 0);
        result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
        result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
        return result;
    }
}
