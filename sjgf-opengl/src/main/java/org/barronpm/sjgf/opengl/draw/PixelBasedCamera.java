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

package org.barronpm.sjgf.opengl.draw;

import org.barronpm.sjgf.draw.Camera;
import org.barronpm.sjgf.draw.OrthographicCamera;
import org.barronpm.sjgf.math.Vector3;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public final class PixelBasedCamera extends GLFWWindowSizeCallback implements Camera {

    private OrthographicCamera camera;

    public PixelBasedCamera(int width, int height) {
        camera = new OrthographicCamera(0, width, 0, height);
    }

    @Override
    public Vector3 project(Vector3 in) {
        return camera.project(in);
    }

    @Override
    public Vector3 project(Vector3 in, Vector3 out) {
        return camera.project(in, out);
    }

    @Override
    public Vector3 project(float x, float y, float z) {
        return camera.project(x, y, z);
    }

    @Override
    public Vector3 invertProject(Vector3 in) {
        return camera.invertProject(in);
    }

    @Override
    public Vector3 invertProject(Vector3 in, Vector3 out) {
        return camera.invertProject(in, out);
    }

    @Override
    public Vector3 invertProject(float x, float y, float z) {
        return camera.invertProject(x, y, z);
    }

    @Override
    public void invoke(long window, int width, int height) {
        camera.setViewport(0, width, 0, height);
    }
}
