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

package org.barronpm.sjgf.draw;

import org.barronpm.sjgf.math.Vector3;

/**
 * A Camera projects coordinates from world space to screen space.
 *
 * @author Patrick Barron
 * @see OrthographicCamera
 * @since 1.0
 */
public interface Camera {

    /**
     * Projects a coordinate onto screen space.
     *
     * @param in the coordinate to project.
     * @return the projected coordinate.
     */
    Vector3 project(Vector3 in);

    /**
     * Projects a coordinate onto screen space.
     * Stores the output in a provided {@link Vector3}.
     *
     * @param in the coordinate to project
     * @param out the vector to store the result in.
     * @return the projected coordinate.
     */
    Vector3 project(Vector3 in, Vector3 out);

    /**
     * Projects a coordinate onto screen space.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the projected coordinate.
     */
    Vector3 project(float x, float y, float z);

    /**
     * Projects a coordinate from screen space to world space.
     * This is the inverse of projecting from world space to screen space.
     *
     * @param in the coordinate to inverse project
     * @return the projected coordinate.
     */
    Vector3 invertProject(Vector3 in);

    /**
     * Projects a coordinate from screen space to world space.
     * This is the inverse of projecting from world space to screen space.
     *
     * @param in the coordinate to project
     * @param out the coordinate to project to
     * @return the projected coordinate.
     */
    Vector3 invertProject(Vector3 in, Vector3 out);

    /**
     * Projects a coordinate from screen space to world space.
     * This is the inverse of projecting from world space to screen space.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @return the projected coordinate.
     */
    Vector3 invertProject(float x, float y, float z);
}