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

package org.barronpm.sjgf.opengl;

import org.barronpm.sjgf.ResourceLoader;
import org.barronpm.sjgf.draw.Texture;
import org.barronpm.sjgf.opengl.draw.GlTexture;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class GlTextureLoader implements ResourceLoader<Texture> {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final IntBuffer x;
    private final IntBuffer y;
    private final IntBuffer channels;

    public GlTextureLoader() {
        stbi_set_flip_vertically_on_load(true);
        x = BufferUtils.createIntBuffer(1);
        y = BufferUtils.createIntBuffer(1);
        channels = BufferUtils.createIntBuffer(1);
    }

    @Override
    public Texture load(File file) {
        ByteBuffer buffer = stbi_load(file.getPath(),
                x, y, channels, 0);
        GlTexture texture = new GlTexture(x.get(), y.get(), channels.get() == 4, buffer);
        x.clear();
        y.clear();
        channels.clear();

        return texture;
    }

}
