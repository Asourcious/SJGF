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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

public class AudioPlayer implements Disposable {

    public static final Logger LOG = LoggerFactory.getLogger(AudioPlayer.class);

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

    @Override
    public void dispose() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }
}
