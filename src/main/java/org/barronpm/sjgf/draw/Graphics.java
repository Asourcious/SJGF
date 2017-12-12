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

package org.barronpm.sjgf.draw;

public interface Graphics {

    Color getColor();
    void setColor(Color color);

    void drawArc(float x1, float y1, float width, float height, float start, float end);
    void drawArc(float x1, float y1, float width, float height, float start, float end, int segments);
    void drawEllipse(float x, float y, float width, float height);
    void drawEllipse(float x, float y, float width, float height, int segments);
    void drawLine(float x1, float y1, float x2, float y2);
    void drawString(String string, float x, float y);
    void drawRect(float x, float y, float width, float height);
    void drawTexture(Texture texture, float x, float y);
    void fillArc(float x1, float y1, float width, float height, float start, float end);
    void fillArc(float x1, float y1, float width, float height, float start, float end, int segments);
    void fillEllipse(float x, float y, float width, float height);
    void fillEllipse(float x, float y, float width, float height, int segments);
    void fillRect(float x, float y, float width, float height);
}
