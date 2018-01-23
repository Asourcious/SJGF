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
