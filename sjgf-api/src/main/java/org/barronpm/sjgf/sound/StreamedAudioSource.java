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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;

/**
 * An audio source to be played by an {@link AudioPlayer}.
 *
 * StreamedAudioSources, unlike an instance of {@link AudioSource}, are
 * only partially in memory at any given time, and are instead read
 * in pieces as the data is needed. Consider this over a normal AudioSource for
 * longer files that won't be played as frequently.
 *
 * @author Patrick Barron
 * @see AudioPlayer
 * @see AudioSource
 * @since 1.0
 */
public class StreamedAudioSource {

    public static final Logger LOG = LoggerFactory.getLogger(StreamedAudioSource.class);

    private final File file;
    private long size;
    private int read = 0;

    private AudioInputStream stream;

    int frequency;
    int frameSize;
    int format;

    private StreamedAudioSource(File file, AudioInputStream stream) {
        this.file = file;
        this.frequency = (int) stream.getFormat().getFrameRate();
        this.frameSize = stream.getFormat().getFrameSize();
        this.size = stream.getFrameLength() * frameSize;
        this.stream = stream;

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
    }

    /**
     * Creates a StreamedAudioSource from a file.
     *
     * @param file the file to read.
     * @return the created StreamedAudioSource
     * @throws IOException if the file is unable to be read.
     * @throws UnsupportedAudioFileException if the type of the provided file is not supported.
     * @since 1.0
     */
    public static StreamedAudioSource load(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream stream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,
                AudioSystem.getAudioInputStream(file));

        return new StreamedAudioSource(file, stream);
    }

    byte[] read() {
        byte[] ary = new byte[Math.min(5 * frequency * frameSize,(int) (size - read))];

        int read = 0;
        try {
            read = stream.read(ary);
        } catch (IOException e) {
            LOG.warn("Error reading source: ", e);
        }

        this.read += read;

        return ary;
    }

    boolean isDone() {
        return read == stream.getFormat().getFrameSize() * stream.getFrameLength();
    }

    void reset() {
        try {
            stream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,
                    AudioSystem.getAudioInputStream(file));
            frequency = (int) stream.getFormat().getFrameRate();
            frameSize = stream.getFormat().getFrameSize();
            read = 0;
        } catch (UnsupportedAudioFileException | IOException e) {
            LOG.warn("Error resetting stream: ", e);
        }
    }
}
