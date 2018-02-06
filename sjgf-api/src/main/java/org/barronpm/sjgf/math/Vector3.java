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

public final class Vector3 {

    private float x;
    private float y;
    private float z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3(Vector3 vector) {
        this(vector.x, vector.y, vector.z);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public float len2() {
        return x * x + y * y + z * z;
    }

    public float dist(Vector3 other) {
        return (float) Math.sqrt(dist2(other));
    }

    public float dist2(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public void add(Vector3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    public void sub(Vector3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    public void mult(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
    }

    public void div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
    }

    public Vector3 crossProduct(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

    public float dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        return Float.compare(vector3.x, x) == 0
                && Float.compare(vector3.y, y) == 0
                && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);

        return result;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}