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

import org.barronpm.sjgf.Disposable;
import org.barronpm.sjgf.exceptions.SJGFException;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

class GlShader implements Disposable {

    private final int handle;
    private boolean disposed = false;

    public GlShader(int type, String source) {
        handle = glCreateShader(type);
        glShaderSource(handle, source);
        glCompileShader(handle);

        if (glGetShaderi(handle, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new SJGFException("Failed to compile shader: "
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