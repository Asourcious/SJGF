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

import org.barronpm.sjgf.events.Event;
import org.barronpm.sjgf.events.GenericMonitorEvent;
import org.barronpm.sjgf.events.MonitorConnectedEvent;
import org.barronpm.sjgf.events.MonitorDisconnectedEvent;
import org.barronpm.sjgf.events.input.*;

/**
 * An abstract implementation of {@link EventListener} which divides {@link Event Events}
 * into subclasses.
 *
 * @author Patrick Barron
 * @see EventListener
 * @since 1.0
 */
public abstract class ListenerAdapter implements EventListener {

    @Override
    public final void onEvent(Event event) {
        if (event instanceof GenericKeyEvent)
            onGenericKeyEvent((GenericKeyEvent) event);
        else if (event instanceof GenericMonitorEvent)
            onGenericMonitorEvent((GenericMonitorEvent) event);
        else if (event instanceof GenericMouseEvent)
            onGenericMouseEvent((GenericMouseEvent) event);

        if (event instanceof KeyHoldEvent)
            onKeyHold((KeyHoldEvent) event);
        else if (event instanceof KeyPressEvent)
            onKeyPress((KeyPressEvent) event);
        else if (event instanceof KeyReleaseEvent)
            onKeyRelease((KeyReleaseEvent) event);
        else if (event instanceof MonitorConnectedEvent)
            onMonitorConnected((MonitorConnectedEvent) event);
        else if (event instanceof MonitorDisconnectedEvent)
            onMonitorDisconnected((MonitorDisconnectedEvent) event);
        else if (event instanceof MouseButtonPressEvent)
            onMouseButtonPress((MouseButtonPressEvent) event);
        else if (event instanceof MouseButtonReleaseEvent)
            onMouseButtonRelease((MouseButtonReleaseEvent) event);
        else if (event instanceof MouseEnterEvent)
            onMouseEnter((MouseEnterEvent) event);
        else if (event instanceof MouseMoveEvent)
            onMouseMove((MouseMoveEvent) event);
    }

    /**
     * Fires whenever there is a key-related event.
     *
     * @param event the event
     * @since 1.0
     */
    public void onGenericKeyEvent(GenericKeyEvent event) {}

    /**
     * Fires whenever there is a monitor-related event.
     *
     * @param event the event
     * @since 1.0
     */
    public void onGenericMonitorEvent(GenericMonitorEvent event) {}

    /**
     * Fires whenever there is a mouse-related event.
     *
     * @param event the event
     * @since 1.0
     */
    public void onGenericMouseEvent(GenericMouseEvent event) {}


    /**
     * Fires whenever a key is held.
     *
     * @param event the event
     * @since 1.0
     */
    public void onKeyHold(KeyHoldEvent event) {}

    /**
     * Fires whenever a key is pressed
     *
     * @param event the event
     * @since 1.0
     */
    public void onKeyPress(KeyPressEvent event) {}

    /**
     * Fires whenever a key is released.
     *
     * @param event the event
     * @since 1.0
     */
    public void onKeyRelease(KeyReleaseEvent event) {}

    /**
     * Fires whenever a monitor is connected to the system.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMonitorConnected(MonitorConnectedEvent event) {}

    /**
     * Fires whenever a monitor is disconnected from the system.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMonitorDisconnected(MonitorDisconnectedEvent event) {}

    /**
     * Fires whenever a mouse button is pressed.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMouseButtonPress(MouseButtonPressEvent event) {}

    /**
     * Fires whenever a mouse button is released.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMouseButtonRelease(MouseButtonReleaseEvent event) {}

    /**
     * Fires whenever the mouse enters the window.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMouseEnter(MouseEnterEvent event) {}

    /**
     * Fires whenever the mouse moves.
     *
     * @param event the event
     * @since 1.0
     */
    public void onMouseMove(MouseMoveEvent event) {}
}
