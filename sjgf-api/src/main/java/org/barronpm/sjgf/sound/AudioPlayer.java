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

package org.barronpm.sjgf.sound;

import org.barronpm.sjgf.Disposable;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

public class AudioPlayer implements Disposable {

    public static final Logger LOG = LoggerFactory.getLogger(AudioPlayer.class);

    private static final ExecutorService threads = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);

        return thread;
    });

    private final long device;
    private final long context;

    private int source;
    private int buffer;

    public AudioPlayer() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        context = alcCreateContext(device, (int[]) null);
        alcMakeContextCurrent(context);
        ALCCapabilities capabilities = ALC.createCapabilities(device);
        AL.createCapabilities(capabilities);

        buffer = alGenBuffers();
        source = alGenSources();
    }

    public void play(AudioSource source) {
        alBufferData(buffer, source.getFormat(), source.getBuffer(), source.getFrequency());
        alSourcei(this.source, AL_BUFFER, buffer);
        alSourcePlay(this.source);
    }

    public void play(StreamedAudioSource audioSource) {
        threads.submit(new BackgroundSourceLoader(audioSource));
    }

    @Override
    public void dispose() {
        threads.shutdown();
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    private class BackgroundSourceLoader implements Runnable {

        private StreamedAudioSource audioSource;

        private BackgroundSourceLoader(StreamedAudioSource audioSource) {
            this.audioSource = audioSource;
        }

        @Override
        public void run() {
            int[] bufferPool = new int[2];
            alGenBuffers(bufferPool);

            int source = alGenSources();

            ByteBuffer buffer;
            for (int buf : bufferPool) {
                buffer = readToBuffer(audioSource, source);
                bindBuffer(audioSource, buffer, source, buf);
                alSourcePlay(source);
            }

            while (!audioSource.isDone()) {
                buffer = readToBuffer(audioSource, source);

                int buf;
                for (buf = 0; buf == 0; buf = alSourceUnqueueBuffers(source)) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) { }
                }

                bindBuffer(audioSource, buffer, source, buf);
            }
        }

        private ByteBuffer readToBuffer(StreamedAudioSource audioSource, int source) {
            byte[] ary = audioSource.read();
            ByteBuffer buf = MemoryUtil.memAlloc(ary.length);
            buf.put(ary);
            buf.flip();

            return buf;
        }

        private void bindBuffer(StreamedAudioSource audioSource, ByteBuffer buffer, int source, int bufferHandle) {
            alBufferData(bufferHandle, audioSource.format, buffer, audioSource.frequency);
            alSourceQueueBuffers(source, bufferHandle);
            MemoryUtil.memFree(buffer);
        }
    }
}
