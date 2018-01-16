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

import org.barronpm.sjgf.math.Matrix4;
import org.barronpm.sjgf.math.Vector3;

public final class OrthographicCamera implements Camera {

    private Matrix4 projection;
    private Matrix4 inverseProjection;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        setViewport(left, right, bottom, top);
    }

    public void setViewport(float left, float right, float bottom, float top) {
        projection = Matrix4.createOrtho(left, right, bottom, top);
        inverseProjection = projection.invert();
    }

    @Override
    public Vector3 project(Vector3 in) {
        return project(in, new Vector3());
    }

    @Override
    public Vector3 project(Vector3 in, Vector3 out) {
        return projection.mult(in, out);
    }

    @Override
    public Vector3 project(float x, float y, float z) {
        return project(new Vector3(x, y, z));
    }

    @Override
    public Vector3 invertProject(Vector3 in) {
        return invertProject(in, new Vector3());
    }

    @Override
    public Vector3 invertProject(Vector3 in, Vector3 out) {
        return inverseProjection.mult(in, out);
    }

    @Override
    public Vector3 invertProject(float x, float y, float z) {
        return invertProject(new Vector3(x, y, z));
    }
}