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

package org.barronpm.sjgf.events;

import org.barronpm.sjgf.GameWindow;
import org.barronpm.sjgf.Monitor;

/**
 * This event is fired when a monitor is disconnected from the computer.
 *
 * @author Patrick Barron
 * @see Event
 * @see GenericMonitorEvent
 * @see MonitorConnectedEvent
 * @since 1.0
 */
public class MonitorDisconnectedEvent extends GenericMonitorEvent {

    public MonitorDisconnectedEvent(GameWindow window, Monitor monitor) {
        super(window, monitor);
    }

}
