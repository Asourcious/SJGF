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

package org.barronpm.sjgf.math;

import java.util.Arrays;

public final class Matrix3 {

    private final float[][] values;

    public Matrix3(float[][] values) {
        // Ensure that values is a nonnull 3x3 array
        if (values == null || values.length != 3
                || values[0] == null || values[0].length != 3
                || values[1] == null || values[1].length != 3
                || values[2] == null || values[2].length != 3) {
            throw new IllegalArgumentException("Must provide a 3x3 array");
        }

        this.values = values;
    }

    private Matrix3() {
        this.values = new float[3][3];
    }

    public static Matrix3 createOrtho(int left, int right, int bottom, int top) {

        Matrix3 matrix = new Matrix3();

        matrix.values[0][0] = 2f / ( right - left);
        matrix.values[0][1] = -((float) right + left) / (right - left);
        matrix.values[1][1] = 2f / ( top - bottom);
        matrix.values[1][2] = -((float) top + bottom) / (top - bottom);
        matrix.values[2][2] = 1;

        return matrix;
    }

    public Vector2 mult(Vector2 in) {
        return mult(in, new Vector2());
    }

    public Vector2 mult(Vector2 in, Vector2 out) {
        float x = values[0][0] * in.getX()
                + values[0][1] * in.getY()
                + values[0][2];
        float y = values[1][0] * in.getX()
                + values[1][1] * in.getY()
                + values[1][2];
        float w = values[2][0] * in.getX()
                + values[2][1] * in.getY()
                + values[2][2];

        if (w != 1) {
            x /= w;
            y /= w;
        }

        out.set(x, y);
        return out;
    }

    public float determinant() {
        return (values[0][0] * (values[1][1] * values[2][2] - values[1][2] * values[3][0])
                - values[0][1] * (values[1][0] * values[2][2] - values[1][2] * values[2][0])
                + values[0][2] * (values[1][0] * values[2][1] - values[1][1] * values[2][0]));
    }

    public Matrix3 invert() {
        float d = determinant();

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix3 matrix3 = (Matrix3) o;

        return Arrays.deepEquals(values, matrix3.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
