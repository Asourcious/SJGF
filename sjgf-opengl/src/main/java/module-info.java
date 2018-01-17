/*
 *     Copyright 2017 Patrick Barron
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

import org.barronpm.sjgf.GameWindow;
import org.barronpm.sjgf.opengl.GlGameWindow;

module org.barronpm.sjgf.opengl {
    requires org.barronpm.sjgf;

    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.openal;
    requires org.lwjgl.opengl;

    provides GameWindow with GlGameWindow;
}