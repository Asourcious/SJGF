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

package org.barronpm.sjgf.util;

public final class Args {

    private Args() {}

    public static void inRange(int lower, int upper, int arg, String name) {
        if (lower <= arg && upper > arg)
            return;

        throw new IndexOutOfBoundsException("Provided" + name + " must be in range [" + lower + ',' + upper + ']');
    }

    public static void notNull(Object o, String name) {
        if (o != null)
            return;

        throw new NullPointerException("Provided " + name + " was null.");
    }

}
