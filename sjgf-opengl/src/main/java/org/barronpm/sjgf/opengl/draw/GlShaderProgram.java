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

public class GlShaderProgram implements Disposable {

    private final int program;
    private boolean disposed = false;

    public GlShaderProgram() {
        program = glCreateProgram();
        glAttachShader(program, GlShader.VERTEX.getHandle());
        glAttachShader(program, GlShader.FRAGMENT.getHandle());
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            throw new SJGFException("Failed to link program: "
                    + glGetProgramInfoLog(program));
        }

        glUseProgram(program);
        GlShader.VERTEX.dispose();
        GlShader.FRAGMENT.dispose();
    }

    public int getHandle() {
        return disposed ? -1 : program;
    }

    @Override
    public void dispose() {
        glDeleteProgram(program);
        disposed = true;
    }
}