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

public final class Vector2 {

    private float x;
    private float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
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

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public float len2() {
        return x * x + y * y;
    }

    public float dist(Vector2 other) {
        return (float) Math.sqrt(dist2(other));
    }

    public float dist2(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public void add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2 = (Vector2) o;

        return Float.compare(vector2.x, x) == 0 && Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);

        return result;
    }
}
