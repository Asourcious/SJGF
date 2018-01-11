package org.barronpm.sjgf.backend.draw;

import org.barronpm.sjgf.draw.Camera;
import org.barronpm.sjgf.draw.OrthographicCamera;
import org.barronpm.sjgf.math.Vector3;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class PixelBasedCamera extends GLFWWindowSizeCallback implements Camera {

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
