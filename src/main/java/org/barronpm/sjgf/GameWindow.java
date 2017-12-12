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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface GameWindow extends AutoCloseable {

    Logger LOG = LoggerFactory.getLogger(GameWindow.class);

    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getWidth();
    void setWidth(int width);
    int getHeight();
    void setHeight(int height);
    String getTitle();
    void setTitle(String title);
    boolean isUsingVsync();
    void setUseVsync(boolean useVsync);
    Monitor getMonitor();
    void setMonitor(Monitor monitor);
    WindowState getState();
    void setState(WindowState state);

    boolean isResizable();
    boolean isVisible();

    Game getGame();

    void start();

    @Override
    void close();
}
