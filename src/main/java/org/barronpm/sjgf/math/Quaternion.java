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

import java.util.Objects;

public final class Quaternion {

    private float a;
    private float b;
    private float c;
    private float d;

    public Quaternion() {
        this(0, 0, 0, 0);
    }

    public Quaternion(float a, float b, float c, float d) {
        set(a, b, c, d);
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public void set(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Matrix4 toMatrix4() {
        float[][] values = new float[4][4];

        values[0][0] = a;
        values[0][1] = -b;
        values[0][2] = -c;
        values[0][3] = -d;
        values[1][0] = b;
        values[1][1] = a;
        values[1][2] = -d;
        values[1][3] = c;
        values[2][0] = c;
        values[2][1] = d;
        values[2][2] = a;
        values[2][3] = -b;
        values[3][0] = d;
        values[3][1] = -c;
        values[3][2] = b;
        values[3][3] = a;

        return new Matrix4(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaternion that = (Quaternion) o;
        return Float.compare(that.a, a) == 0 &&
                Float.compare(that.b, b) == 0 &&
                Float.compare(that.c, c) == 0 &&
                Float.compare(that.d, d) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }

    @Override
    public String toString() {
        return a + " + " + b + "i + " + c + "j + " + d;
    }
}