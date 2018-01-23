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

package org.barronpm.sjgf;

import org.barronpm.sjgf.draw.Texture;
import org.barronpm.sjgf.exceptions.SJGFException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * A GameWindow represents an OS window and contains an instance of a {@link Game}.
 *
 * Only one GameWindow can exist in a program. GameWindow objects have
 * mutable parameters, such as position, dimensions, and title.
 *
 * To create a GameWindow instance, use the {@link #create(Game)} method.
 *
 * @author Patrick Barron
 * @see Disposable
 * @see Game
 * @since 1.0
 */
public interface GameWindow {

    /**
     * Logger instance used by implementations of GameWindow
     *
     * @see Logger
     * @since 1.0
     */
    Logger LOG = LoggerFactory.getLogger(GameWindow.class);

    /**
     * Creates a new GameWindow that renders the provided game.
     * For this method to work, there must be an implementation of GameWindow
     * present. If there is none, it will throw a {@link SJGFException}.
     *
     * @param game the game to be rendered.
     * @return the new GameWindow instance
     * @throws NullPointerException if game is <code>null</code>
     * @throws SJGFException if no implementation of GameWindow is found.
     * @since 1.0
     */
    static GameWindow create(Game game) {
        Optional<GameWindow> optionalWindow = ServiceLoader.load(GameWindow.class).findFirst();
        if (!optionalWindow.isPresent())
            throw new SJGFException("No implementation of GameWindow");

        GameWindow gameWindow = optionalWindow.get();
        gameWindow.setGame(game);

        return gameWindow;
    }

    /**
     * Returns the x coordinate of the upper-left corner of this window.
     *
     * @return The x position of this window, in pixels
     * @see #setX(int)
     * @since 1.0
     */
    int getX();

    /**
     * Sets the x coordinate of the upper-left corner of this window.
     *
     * @param x the new x value, in pixels.
     * @see #getX()
     * @since 1.0
     */
    void setX(int x);

    /**
     * Returns the y coordinate of the upper-left corner of this window.
     *
     * @return The y position of this window, in pixels
     * @see #setY(int)
     * @since 1.0
     */
    int getY();

    /**
     * Sets the y coordinate of the upper-left corner of this window.
     *
     * @param y The new y value, in pixels.
     * @see #getY()
     * @since 1.0
     */
    void setY(int y);

    /**
     * Returns the width of the client area.
     *
     * @return The width of this window, in pixels.
     * @see #setWidth(int)
     * @since 1.0
     */
    int getWidth();

    /**
     * Sets the width of the client area of this window.
     *
     * @param width The new height of this window
     * @throws IllegalArgumentException if width &lt; 0
     * @see #getWidth()
     * @since 1.0
     */
    void setWidth(int width);

    /**
     * Returns the height of the client area.
     *
     * @return The height of this window, in pixels.
     * @see #setHeight(int)
     * @since 1.0
     */
    int getHeight();


    /**
     * Sets the height of the client area of this window.
     *
     * @param height the new height of this window
     * @see #getHeight()
     * @since 1.0
     */
    void setHeight(int height);


    /**
     * Returns the title of this window
     *
     * @return The title of this window
     * @see #setTitle(String)
     * @since 1.0
     */
    String getTitle();

    /**
     * Specifies the title of this window.
     *
     * @param title the new title
     * @see #getTitle()
     * @since 1.0
     */
    void setTitle(String title);

    /**
     * Returns whether or not this window is using VSync.
     * VSync synchronizes the refresh rate of this window with that of the monitor,
     * preventing "screen tearing," or when the monitor updates in the middle of a frame
     * refresh, causing some of the screen to be displaying one frame and other parts of
     * the screen displaying a different frame.
     *
     * @return whether this window is currently using VSync
     * @see #setUseVSync(boolean)
     * @since 1.0
     */
    boolean isUsingVSync();

    /**
     * Sets whether or not this window should utilize VSync.
     * VSync synchronizes the refresh rate of this window with that of the monitor,
     * preventing "screen tearing," or when the monitor updates in the middle of a frame
     * refresh, causing some of the screen to be displaying one frame and other parts of
     * the screen displaying a different frame.
     *
     * @param useVSync whether or not this window should utilize VSync
     * @see #isUsingVSync()
     * @since 1.0
     */
    void setUseVSync(boolean useVSync);

    /**
     * Returns the monitor that this window is displayed on. This is only applicable when the
     * {@link WindowState} is set to {@link WindowState#FULLSCREEN}
     * 
     * @return The monitor that this window renders to.
     * @see #setMonitor(Monitor)
     * @see #getState()
     * @see #setState(WindowState)
     * @see WindowState
     * @since 1.0
     */
    Monitor getMonitor();

    /**
     * Returns the monitor that this window is displayed on. This is only applicable when the
     * {@link WindowState} is set to {@link WindowState#FULLSCREEN}
     * 
     * @param monitor the monitor to be displayed on
     * @see #getMonitor()
     * @see #getState()
     * @see #setState(WindowState)
     * @see WindowState
     * @since 1.0
     */
    void setMonitor(Monitor monitor);

    /**
     * Returns the current window state.
     *
     * @return the current state of this window
     * @see #setState(WindowState)
     * @see WindowState
     * @since 1.0
     */
    WindowState getState();

    /**
     * Sets this window's state.
     *
     * @param state the new state of this window
     * @see #getState()
     * @see WindowState
     * @since 1.0
     */
    void setState(WindowState state);

    /**
     * Returns whether the window can be resized by the user
     *
     * @return whether the window can be resized by the user
     * @since 1.0
     */
    boolean isResizable();

    /**
     * Returns whether the window is visible to the user.
     *
     * @return whether the window is currently visible
     * @since 1.0
     */
    boolean isVisible();

    /**
     * Returns the game instance associated with this window.
     *
     * @return the game instance associated with this window
     * @see #setGame(Game)
     * @since 1.0
     */
    Game getGame();

    /**
     * Sets the game object that this window is displaying.
     *
     * @param game non-null game instance
     * @throws NullPointerException if game is null
     * @see #getGame()
     * @since 1.0
     */
    void setGame(Game game);

    /**
     * Returns the texture loader associated with this window.
     *
     * @return this window's texture loader.
     * @see ResourceLoader
     * @since 1.0
     */
    ResourceLoader<Texture> getTextureLoader();

    /**
     * Starts this window's game. This method initializes OpenGL and starts this
     * window's game.
     *
     * @see #close()
     * @since 1.0
     */
    void start();

    /**
     * Closes the window and releases all resources held by it.
     *
     * @see #start()
     * @since 1.0
     */
    void close();
}
