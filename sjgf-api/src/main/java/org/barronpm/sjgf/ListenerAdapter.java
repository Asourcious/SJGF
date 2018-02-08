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

    public void onGenericKeyEvent(GenericKeyEvent event) {}

    public void onGenericMonitorEvent(GenericMonitorEvent event) {}

    public void onGenericMouseEvent(GenericMouseEvent event) {}



    public void onKeyHold(KeyHoldEvent event) {}

    public void onKeyPress(KeyPressEvent event) {}

    public void onKeyRelease(KeyReleaseEvent event) {}

    public void onMonitorConnected(MonitorConnectedEvent event) {}

    public void onMonitorDisconnected(MonitorDisconnectedEvent event) {}

    public void onMouseButtonPress(MouseButtonPressEvent event) {}

    public void onMouseButtonRelease(MouseButtonReleaseEvent event) {}

    public void onMouseEnter(MouseEnterEvent event) {}

    public void onMouseMove(MouseMoveEvent event) {}
}
