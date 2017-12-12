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

package org.barronpm.sjgf.draw;

public final class Color {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color DARK_GRAY = new Color(.25f, .25f, .25f);
    public static final Color GRAY = new Color(.5f, .5f, .5f);
    public static final Color LIGHT_GRAY = new Color(.75f, .75f, .75f);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color CYAN = new Color(0, 1, 1);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color YELLOW = new Color(1, 1, 0);
    public static final Color ORANGE = new Color(1, .5f, 0);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color MAGENTA = new Color(1, 0, 1);
    public static final Color PINK = new Color(1, .5f, .9f);

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }

    public float getAlpha() {
        return a;
    }

    @Override
    public String toString() {
        return getClass().getName() +"[r=" + r + ",g=" + g + ",b=" + b + ",a=" + a + "]";
    }

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

    @Override
    public int hashCode() {
        int result = (r != +0.0f ? Float.floatToIntBits(r) : 0);
        result = 31 * result + (g != +0.0f ? Float.floatToIntBits(g) : 0);
        result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
        result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
        return result;
    }
}
