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

import org.lwjgl.BufferUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;

public class AudioSource {

    private ByteBuffer data;

    private final int frequency;
    private final int format;

    private AudioSource(byte[] bytes, int frequency, int format) {
        data = BufferUtils.createByteBuffer(bytes.length);
        data.put(bytes);
        data.flip();
        this.frequency = frequency;
        this.format = format;
    }

    public static AudioSource load(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream stream = AudioSystem.getAudioInputStream(
                AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(file));

        byte[] bytes = new byte[(int) (stream.getFrameLength() * stream.getFormat().getFrameSize())];
        int numRead = stream.read(bytes);

        int format;
        switch (stream.getFormat().getChannels()) {
            case 1:
                format = AL_FORMAT_MONO16;
                break;
            case 2:
                format = AL_FORMAT_STEREO16;
                break;
            default:
                throw new RuntimeException("Unsupported number of channels: " + stream.getFormat().getChannels());
        }

        return new AudioSource(bytes, (int) stream.getFormat().getSampleRate(), format);
    }

    ByteBuffer getBuffer() {
        return data;
    }

    int getFrequency() {
        return frequency;
    }

    int getFormat() {
        return format;
    }
}
