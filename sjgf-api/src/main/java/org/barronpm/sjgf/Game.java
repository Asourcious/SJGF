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

import org.barronpm.sjgf.draw.Color;
import org.barronpm.sjgf.draw.Graphics;

/**
 * Game objects control all of the logic needed for a game.
 *
 * @author Patrick Barron
 * @see GameWindow
 * @since 1.0
 */
public interface Game extends Disposable {

    /**
     * Returns the background color of this game.
     *
     * @return the background color of this game
     */
    Color getBackgroundColor();

    /**
     * This method is called once during the game's lifecycle before the game loop begins.
     * Common uses of this method include loading resources to be
     *
     * @param window the window associated with this game
     * @see #dispose()
     * @since 1.0
     */
    void init(GameWindow window);

    /**
     * This method is called when the application should perform a "step"
     * in its game logic.
     *
     * @param window the window associated with this game
     * @param delta the amount of time that has passed, in seconds, since the last frame
     * @see #render(GameWindow, Graphics)
     * @since 1.0
     */
    void update(GameWindow window, double delta);

    /**
     * This method is called when the application should render a frame.
     * Although game logic such as physics is allowed here, it is recommended to
     * use {@link #update(GameWindow, double)} for this.
     *
     * @param window the window associated with this game
     * @param graphics a graphics object to be used for rendering
     * @see #update(GameWindow, double)
     * @since 1.0
     */
    void render(GameWindow window, Graphics graphics);
}
