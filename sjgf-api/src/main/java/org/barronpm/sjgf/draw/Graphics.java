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

import java.awt.Font;

/**
 * The Graphics interface provides basic functionality for rendering
 * geometric primitives, text, and images.
 *
 * @author Patrick Barron
 * @since 1.0
 */
public interface Graphics {

    /**
     * Returns the color of this graphics object.
     * The color is used by default for all drawing except that of textures,
     * with uses {@link Color#WHITE} by default.
     *
     * @return the current color of this object
     * @see #setColor(Color)
     * @since 1.0
     */
    Color getColor();

    /**
     * Sets the default color of this object.
     * The color is used to draw shapes and text by default
     *
     * @param color the color to use.
     * @throws NullPointerException if color is <code>null</code>
     * @since 1.0
     */
    void setColor(Color color);

    /**
     * Returns the font used by this object to render text.
     *
     * @return the font used to render text
     * @see #setFont(Font)
     * @since 1.0
     */
    Font getFont();

    /**
     * Sets the font used by this object to render text.
     *
     * @param font the font to use when rendering text
     * @throws NullPointerException if font is <code>null</code>
     * @see #getFont()
     * @since 1.0
     */
    void setFont(Font font);

    /**
     * Returns the never-null camera used by this object.
     * If no camera is set, it will return the default camera,
     * which has a viewport the size of the window in pixels with the origin at
     * the bottom-left.
     *
     * @return the camera used by this object.
     * @see #setCamera(Camera)
     * @since 1.0
     */
    Camera getCamera();

    /**
     * Sets the camera used by this object. If <code>null</code> is provided,
     * the default camera will be used.
     *
     * @param camera the camera to use, or <code>null</code> to use the default
     * @see #getCamera()
     * @since 1.0
     */
    void setCamera(Camera camera);

    /**
     * Draws an arc with the provided size centered around the provided coordinates
     * with the given start and end angles.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the arc
     * @param height the height of the arc
     * @param start the start angle, in radians
     * @param end the end angle, in radians
     * @see #drawArc(float, float, float, float, float, float, int)
     * @since 1.0
     */
    void drawArc(float x, float y, float width, float height, float start, float end);

    /**
     * Draws an arc with the provided size centered around the provided coordinates
     * with the given start and end angles using the provided number of segments.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the arc
     * @param height the height of the arc
     * @param start the start angle, in radians
     * @param end the end angle, in radians
     * @param segments the number of segments to use
     * @see #drawArc(float, float, float, float, float, float)
     * @since 1.0
     */
    void drawArc(float x, float y, float width, float height, float start, float end, int segments);

    /**
     * Draws an ellipse centered at the given x and y with the provided width and height.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @see #drawEllipse(float, float, float, float, int)
     * @see #fillEllipse(float, float, float, float)
     * @see #fillEllipse(float, float, float, float, int)
     * @since 1.0
     */
    void drawEllipse(float x, float y, float width, float height);

    /**
     * Draws an ellipse centered at the given x and y with the provided width and height
     * using the provided number of segments.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @param segments the number of segments to use
     * @see #drawEllipse(float, float, float, float, int)
     * @see #fillEllipse(float, float, float, float)
     * @see #fillEllipse(float, float, float, float, int)
     * @since 1.0
     */
    void drawEllipse(float x, float y, float width, float height, int segments);

    /**
     * Draws a line from one point to another.
     *
     * @param x1 the first x value
     * @param y1 the first y value
     * @param x2 the second x value
     * @param y2 the second y value
     * @since 1.0
     */
    void drawLine(float x1, float y1, float x2, float y2);

    /**
     * Draws the given text with the bottom-left of the first line at the
     * given point.
     *
     * @param string the text to draw
     * @param x the x position
     * @param y the y position
     * @since 1.0
     */
    void drawString(String string, float x, float y);

    /**
     * Draws a hollow rectangle with the bottom left corner at the provided
     * position with the given width and height.
     *
     * @param x the x position
     * @param y the y position
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @see #fillRect(float, float, float, float)
     */
    void drawRect(float x, float y, float width, float height);

    /**
     * Draws the provided texture with the bottom left corner at the provided position.
     *
     * @param texture the texture to draw
     * @param x the x position
     * @param y the y position
     * @see #drawTexture(Texture, Color, float, float)
     * @see #drawTexture(Texture, float, float, float, float)
     * @see #drawTexture(Texture, Color, float, float, float, float)
     * @since 1.0
     */
    void drawTexture(Texture texture, float x, float y);

    /**
     * Draws the provided texture with the bottom left corner at the provided position
     * using the given color.
     *
     * @param texture the texture to draw
     * @param color the color to draw with
     * @param x the x position
     * @param y the y position
     * @see #drawTexture(Texture, Color, float, float)
     * @see #drawTexture(Texture, float, float, float, float)
     * @see #drawTexture(Texture, Color, float, float, float, float)
     * @since 1.0
     */
    void drawTexture(Texture texture, Color color, float x, float y);

    /**
     * Draws the provided texture with the bottom left corner at the provided position
     * with the provided dimensions.
     *
     * @param texture the texture to draw
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @see #drawTexture(Texture, float, float)
     * @see #drawTexture(Texture, Color, float, float)
     * @see #drawTexture(Texture, Color, float, float, float, float)
     * @since 1.0
     */
    void drawTexture(Texture texture, float x, float y, float width, float height);

    /**
     * Draws the provided texture with the bottom left corner at the provided position
     * with the provided dimensions using the given color.
     *
     * @param texture the texture to draw
     * @param color the color to draw with
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @see #drawTexture(Texture, float, float)
     * @see #drawTexture(Texture, Color, float, float)
     * @see #drawTexture(Texture, float, float, float, float)
     * @since 1.0
     */
    void drawTexture(Texture texture, Color color, float x, float y, float width, float height);

    /**
     * Fills an ellipse at the provided coordinates with the provided width and height
     * centered at the provided coordinates.
     *
     * @param x the x position of the center of the ellipse
     * @param y the y position of the center of the ellipse
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @see #drawEllipse(float, float, float, float)
     * @see #drawEllipse(float, float, float, float, int)
     * @see #fillEllipse(float, float, float, float, int)
     * @since 1.0
     */
    void fillEllipse(float x, float y, float width, float height);

    /**
     * Fills an ellipse at the provided coordinates with the provided width and height
     * centered at the provided coordinates using the provided number of segments.
     *
     * @param x the x position of the center of the ellipse
     * @param y the y position of the center of the ellipse
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @param segments the number of segments to break the ellipse into
     * @see #drawEllipse(float, float, float, float)
     * @see #drawEllipse(float, float, float, float, int)
     * @see #fillEllipse(float, float, float, float)
     * @since 1.0
     */
    void fillEllipse(float x, float y, float width, float height, int segments);

    /**
     * Fills a rectangle with the bottom-left corner on the given coordinates
     * with the given size.
     *
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @see #drawRect(float, float, float, float)
     * @since 1.0
     */
    void fillRect(float x, float y, float width, float height);
}
