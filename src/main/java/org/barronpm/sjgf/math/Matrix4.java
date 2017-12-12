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

package org.barronpm.sjgf.math;

import java.util.Arrays;

public class Matrix4 {

    private final float[][] values = new float[4][4];

    private Matrix4() {}

    public static Matrix4 createOrtho(int left, int right, int bottom, int top) {
        float f = Integer.MAX_VALUE;
        float n = 0;

        Matrix4 matrix = new Matrix4();

        matrix.values[0][0] = 2f / (right - left);
        matrix.values[0][3] = -((float) right + left) / (right - left);
        matrix.values[1][1] = 2f / (top - bottom);
        matrix.values[1][3] = -((float) top + bottom) / (top - bottom);
        matrix.values[2][2] = 2f / (f - n);
        matrix.values[2][3] = -(f + n) / (f - n);
        matrix.values[3][3] = 1;

        return matrix;
    }

    public Vector3 mult(Vector3 in) {
        return mult(in, new Vector3());
    }

    public Vector3 mult(Vector3 in, Vector3 out) {
        float x = values[0][0] * in.getX()
                + values[0][1] * in.getY()
                + values[0][2] * in.getZ()
                + values[0][3];
        float y = values[1][0] * in.getX()
                + values[1][1] * in.getY()
                + values[1][2] * in.getZ()
                + values[1][3];
        float z = values[2][0] * in.getX()
                + values[2][1] * in.getY()
                + values[2][2] * in.getZ()
                + values[2][3];
        float w = values[3][0] * in.getX()
                + values[3][1] * in.getY()
                + values[3][2] * in.getZ()
                + values[3][3];

        if (w != 1) {
            x /= w;
            y /= w;
            z /= w;
        }

        out.set(x, y, z);
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix4 matrix4 = (Matrix4) o;

        return Arrays.deepEquals(values, matrix4.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }
}
