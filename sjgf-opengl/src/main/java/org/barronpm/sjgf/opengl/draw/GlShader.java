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

package org.barronpm.sjgf.opengl.draw;

import org.barronpm.sjgf.Disposable;
import org.barronpm.sjgf.exceptions.SJGFException;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

public enum GlShader implements Disposable {
    VERTEX(GL_VERTEX_SHADER,
            "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 pos;\n" +
            "layout (location = 1) in vec4 col;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = vec4(pos.x, pos.y, pos.z, 1.0);\n" +
            "    color = col;\n" +
            "}"),
    FRAGMENT(GL_FRAGMENT_SHADER,
            "#version 330 core\n" +
            "\n" +
            "in vec4 color;\n" +
            "\n" +
            "out vec4 fragColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fragColor = color;\n" +
            "}");

    private final int handle;
    private boolean disposed = false;

    GlShader(int type, String source) {
        handle = glCreateShader(type);
        glShaderSource(handle, source);
        glCompileShader(handle);

        if (glGetShaderi(handle, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new SJGFException("Failed to compile handle: "
                    + glGetShaderInfoLog(handle));
        }
    }

    public int getHandle() {
        return disposed ? -1 : handle;
    }

    @Override
    public void dispose() {
        glDeleteShader(handle);
        disposed = true;
    }
}