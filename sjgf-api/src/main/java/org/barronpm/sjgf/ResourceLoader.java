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

package org.barronpm.sjgf;

import java.io.File;

/**
 * A ResourceLoader loads resources from files
 *
 * @author Patrick Barron
 * @param <T> the type of resource that is loaded.
 * @since 1.0
 */
public interface ResourceLoader<T> {

    /**
     * Loads a resource from the provided file.
     *
     * @param file the file to load from
     * @return the loaded resource.
     * @since 1.0
     */
    T load(File file);
}
