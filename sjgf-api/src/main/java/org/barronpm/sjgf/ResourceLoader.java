package org.barronpm.sjgf;

import java.io.File;

public interface ResourceLoader<T> {
    T load(File file);
}
